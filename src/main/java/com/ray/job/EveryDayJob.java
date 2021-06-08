package com.ray.job;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.request.OapiV2UserGetbymobileRequest;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.dingtalk.api.response.OapiV2UserGetbymobileResponse;
import com.jfinal.core.NotAction;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.ray.common.ding.AccessTokenUtil;
import com.taobao.api.ApiException;
import org.quartz.JobExecutionContext;

import com.ray.common.model.SerialNumber;
import com.ray.common.quartz.AbsJob;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.SimpleFormatter;

/**
 * 每天执行
 *
 * @author Ray
 * @date 2010-08-03
 */
public class EveryDayJob extends AbsJob {

	@Override
	protected void process(JobExecutionContext context) {
//		System.out.println("每日任务,自增编号归零");
//		SerialNumber sn = SerialNumber.dao.findById(1);
//		sn.delete();
//		sn = new SerialNumber();
//		sn.setId(1);
//		sn.save();
		
		DateFormat df3 = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.CHINA);
		DateFormat df7 = DateFormat.getTimeInstance(DateFormat.MEDIUM, Locale.CHINA);
		String date3 = df3.format(new Date());
		String time3 = df7.format(new Date());
		System.out.println("每日任务已启动，启动时间：" + date3 + " " + time3);
		try {
			findUsers();
		} catch (ApiException e) {
			e.printStackTrace();
		}
	}
	public void findUsers() throws ApiException {

		//查询每个有待关闭问题的车间在三天内将到期的问题数量，包含：今天，明天，后天到期的,以及日期差异大于2天的
		String sql = "SELECT district_id,DATE_FORMAT(NOW(),'%Y-%m-%d') `today` ,COUNT(*) qty\n" +
				",s.district dis_name,s.first_req_date\n" +
				",COUNT( CASE WHEN DATEDIFF(s.first_req_date,DATE_FORMAT(NOW(),'%Y-%m-%d')) =0 THEN 1 ELSE NULL END )AS diff0\n" +
				",COUNT( CASE WHEN DATEDIFF(s.first_req_date,DATE_FORMAT(NOW(),'%Y-%m-%d')) =1 THEN 1 ELSE NULL END )AS diff1\n" +
				",COUNT( CASE WHEN DATEDIFF(s.first_req_date,DATE_FORMAT(NOW(),'%Y-%m-%d')) =2 THEN 1 ELSE NULL END )AS diff2\n" +
				",COUNT( CASE WHEN DATEDIFF(s.first_req_date,DATE_FORMAT(NOW(),'%Y-%m-%d')) >2 THEN 1 ELSE NULL END )AS rest\n" +
				"FROM s6s_total s\n" +
				"WHERE DATE_FORMAT(NOW(),'%Y-%m-%d')<=s.first_req_date \n" +
				"AND check_status =1 AND handle_status = 0\n" +
				"GROUP BY s.district";
		List<Record> list = Db.find(sql);

		for (Record record : list) {
			String district_id = record.getStr("district_id");
			String dis_name = record.getStr("dis_name");
			String msg = "6S管理系统提示：你在"+dis_name+"区域有即将到期的6S问题，今天到期"+record.get("diff0").toString()+
					"条，明天到期"+record.get("diff1").toString()+
					"条，后天到期"+record.get("diff2").toString()+
					"条，之后到期"+record.get("rest").toString()+
					"条，请及时处理。";

			String sql2 = "select dis_name, phone from s6s_handlers where dis_id = '"+district_id+"'";
			List<Record> listPhone = Db.find(sql2);

			for (Record r : listPhone) {
				String userid = getUserIdByPhone(r.getStr("phone"));
				JSONObject jsonObj = JSON.parseObject(userid);
				
				if((Integer) jsonObj.get("errcode")==0){
					JSONObject jsonObj2 = JSON.parseObject(jsonObj.get("result").toString());
					String id = jsonObj2.get("userid").toString();
					sendMessage(id,msg);

				}

			}
		}
	}
	@NotAction
	public void sendMessage(String id,String message) throws ApiException {

		DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");
		OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
		request.setAgentId(Long.valueOf(PropKit.get("AGENT_ID")));
		request.setUseridList(id);
		request.setToAllUser(false);

		OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
		msg.setMsgtype("text");
		msg.setText(new OapiMessageCorpconversationAsyncsendV2Request.Text());
		msg.getText().setContent(message);
		request.setMsg(msg);

		String accessToken = AccessTokenUtil.getToken();
		OapiMessageCorpconversationAsyncsendV2Response rsp = client.execute(request, accessToken);
//		System.out.println("rspBody in Ding send MSG API:"+rsp.getBody());


	}

	//根据手机号查询userid
	@NotAction
	public static String getUserIdByPhone(String mobile){
		Record record = new Record();
		String resp = "";
		try {
			String accessToken = AccessTokenUtil.getToken();
			DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/user/getbymobile");
			OapiV2UserGetbymobileRequest req = new OapiV2UserGetbymobileRequest();
			req.setMobile(mobile);
			OapiV2UserGetbymobileResponse rsp = client.execute(req, accessToken);
			resp = rsp.getBody();
		} catch (ApiException e) {
			e.printStackTrace();
			record.set("resp",e);
			resp = "no data from getUserIdByPhone.";
		}
		return resp;
	}

}
