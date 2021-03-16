package com.ray.controller.app;


import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.request.OapiUserGetByMobileRequest;
import com.dingtalk.api.request.OapiV2UserGetbymobileRequest;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.dingtalk.api.response.OapiUserGetByMobileResponse;
import com.dingtalk.api.response.OapiV2UserGetbymobileResponse;
import com.jfinal.core.Controller;
import com.jfinal.core.NotAction;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;
import com.ray.common.ding.AccessTokenUtil;
import com.ray.common.model.*;
import com.sun.org.apache.bcel.internal.generic.ARETURN;
import com.taobao.api.ApiException;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static java.lang.Integer.parseInt;

//import com.dingtalk.api.request.OapiUserGetByMobileRequest;

public class SixSController extends Controller {

	public void getSixSTotalListByMonth() {
		Record res = new Record();

		String[] selectDates = get("selectDate").split("-");
		int curMonth = parseInt(selectDates[1]);
		int year = parseInt(selectDates[0]);
		if (curMonth==12){
			year++;
			curMonth=0;
		}
		//此sql语句把所有区域的数据都查出来
		String sql2 = " SELECT sd.*,tab.*,IFNULL(tab.total,0) AS tabtotal,IFNULL(tab.close_qty,0) AS closed FROM s6s_distriction sd\n" +
				" LEFT JOIN \n" +
				"(SELECT s.*,COUNT(s.id) AS total ,t.closed close_qty \n" +
				"FROM s6s_total s \n" +
				"LEFT JOIN \n" +
				"(SELECT s6.district,COUNT(s6.id) AS `closed` \n" +
				"FROM s6s_total s6 \n" +
				"WHERE s6.handle_status = '1' \n" +
				"AND s6.date_time>='"+get("selectDate")+"-01' AND s6.date_time<'"+year+"-"+(curMonth+1)+"-01' and s6.check_status = 1 "+"GROUP BY s6.district )  t\n" +
				"  ON s.`district` = t.`district`\n" +
				"WHERE s.date_time>='"+get("selectDate")+"-01' AND s.date_time<'"+year+"-"+(curMonth+1)+"-01' and s.check_status = 1 "+"GROUP BY s.district ) tab\n" +
				"ON tab.`district`=sd.dis_name";
		List<Record> recordList = Db.find(sql2);
		res.set("totalList",recordList);
		res.set("code",200);
		renderJson(res);
	}
	//
	public void getAwardList(){
		Record res = new Record();
		String[] selectDates = get("selectDate").split("-");
		int curMonth = parseInt(selectDates[1]);
		int year = parseInt(selectDates[0]);
		if (curMonth==12){
			year++;
			curMonth=0;
		}
		String sql = "select * from s6s_award_record s "+
				"WHERE s.date_time>='"+get("selectDate")+"-01' AND s.date_time<'"+year+"-"+(curMonth+1)+"-01' ";
		List<Record> recordList = Db.find(sql);
		res.set("totalListAward",recordList);
		res.set("code",200);
		renderJson(res);
	}
	//onLoad获取所有问题数据
	public void getAllList(){
		System.out.println("table_schema is:");
		System.out.println(PropKit.get("table_schema"));
		System.out.println(PropKit.get("jdbcUrl"));
		System.out.println(PropKit.get("domin_path"));
		System.out.println(PropKit.get("nuber"));
		Record res = new Record();
		String[] selectDates = get("selectDate").split("-");
		int curMonth = parseInt(selectDates[1]);
		int year = parseInt(selectDates[0]);
		if (curMonth==12){
			year++;
			curMonth=0;
		}
		String sql1 = "select dis_name from s6s_distriction";
		List<Record> districtList = Db.find(sql1);
		//获取待id的区域名称
		String sql2 = "select id,dis_name from s6s_distriction";
		List<Record> districtListWithId = Db.find(sql2);
		//获取所有数据
		String dis_name = districtList.get(0).get("dis_name");

		String sql = "select * from s6s_total s where district = '"+dis_name+"' and  handle_status = '0' AND s.first_req_date<'"+year+"-"+(curMonth+1)+"-01' and s.check_status = 1;";
		List<Record> totalList = Db.find(sql);
		//获取总数
		String sql3 = " and s.first_req_date>='"+get("selectDate")+"-01' AND s.first_req_date<'"+year+"-"+(curMonth+1)+"-01' and s.check_status = 1 ";
		String sql5 = "select count(*) notClosedQty from s6s_total s where district = '"+dis_name+"' and  handle_status = '0' ";
		List<Record> notClosedQty = Db.find(sql5+sql3);
		//获取未关闭数
		String sql6 = "select count(*) totalQty from s6s_total s where district = '"+dis_name+"' and  handle_status = '0' ";
		List<Record> totalQty = Db.find(sql6+sql3);

		//获取该车间的处理人
		String sql_handler = "select *  from s6s_handlers where dis_name = '"+dis_name+"' ";
		List<Record> handlerList = Db.find(sql_handler);

		res.set("handlerList",handlerList);
		res.set("totalQty",totalQty);
		res.set("notClosedQty",notClosedQty);
		res.set("totalList",totalList);
		res.set("districtList",districtList);
		res.set("districtListWithId",districtListWithId);
		res.set("code",200);
		renderJson(res);
	}
	//及时获取最新数据
	public void getLatestData(){
		Record res = new Record();
		String[] selectDates = get("selectDate").split("-");
		int curMonth = parseInt(selectDates[1]);
		int year = parseInt(selectDates[0]);
		if (curMonth==12){
			year++;
			curMonth=0;
		}

		String sql1 = "select * from s6s_total s where s.district in ( select dis_name from s6s_distriction where id = '"+get("selectDistrictId")+"' )";
		String sql2="";
		//状态为2代表选择全部
		if(!"2".equals(get("selectStatus"))){
			sql2  = " and s.handle_status = '"+get("selectStatus")+"'";
		}
		String sql3 = " and s.first_req_date>='"+get("selectDate")+"-01' AND s.first_req_date<'"+year+"-"+(curMonth+1)+"-01' and s.check_status = 1";
		List<Record> totalList = Db.find(sql1+sql2+sql3);

		//获取总数
		String sql4 = "select count(*) totalQty from s6s_total s where s.district in ( select dis_name from s6s_distriction where id = '"+get("selectDistrictId")+"')";
		List<Record> totalQty = Db.find(sql4+sql3);
		//获取未关闭数
		String sql5 = "select count(*) notClosedQty from s6s_total s where s.district in ( select dis_name from s6s_distriction where id = '"+get("selectDistrictId")+
				"' ) and s.handle_status = '0' ";
		List<Record> notClosedQty = Db.find(sql5+sql3);

		String sql_handler = "select *  from s6s_handlers where dis_id = '"+get("selectDistrictId")+"'";
		List<Record> handlerList = Db.find(sql_handler);

		res.set("handlerList",handlerList);
		res.set("totalList",totalList);
		res.set("totalQty",totalQty);
		res.set("notClosedQty",notClosedQty);
		res.set("code",200);
		renderJson(res);
	}
	//上传图片
	public void uploadFile(){
		System.out.println(getFile("file"));
		String picName = UUID.randomUUID().toString();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String d = sdf.format(new Date());

		UploadFile file = getFile("file","sixs");
		Record rsp = new Record();
		file.getFile().renameTo(new File("D:\\apache-tomcat-8.0.26\\webapps\\sixs\\"+picName+".jpg"));
		String fileName = ""+d+"_"+picName+".jpg";
		rsp.set("fileName", fileName);
		rsp.set("picName", picName+".jpg");
		renderJson(rsp);
	}
	//保存处理问题时提交的照片，根据id更新
	public  void savePicName(){
		S6sTotal s6sTotal = getModel(S6sTotal.class,"");

		s6sTotal.set("handle_status",1);
		s6sTotal.update();
		Record rsp = new Record();

		rsp.set("status", 200);
		renderJson(rsp);
	}
	//保存精益提交的问题照片，没有id，新增数据
	public void saveLeanCheckData(){
		S6sTotal s6sTotal = getModel(S6sTotal.class,"");
		s6sTotal.set("check_status",1);
		s6sTotal.save();
		Record rsp = new Record();

		rsp.set("status", 200);
		renderJson(rsp);
	}
	//保存精益审核已处理完成的图片的数据
	public void updateDoubleAuditStatus(){
		S6sTotal s6sTotal = getModel(S6sTotal.class,"");

		s6sTotal.update();
		Record rsp = new Record();

		rsp.set("status", 200);
		renderJson(rsp);
	}
	//匿名检举提交数据
	public void submitProposal(){
		S6sTotal s6sTotal = getModel(S6sTotal.class,"");
		String context = "success";
		int status = 200;
		Record rsp = new Record();
		try{
		s6sTotal.save();
		}catch (Exception e){
			context = e.toString();
			status = 0;
		}
		rsp.set("status", status);
		rsp.set("context",context);
		renderJson(rsp);
	}
	public void  getDistrict(){
		Record record = new Record();
		List<Record> list = Db.find("select dis_name from s6s_distriction ");
		record.set("district",list);
		record.set("status", 200);
		renderJson(record);
	}
	public  void getDoubleAuditList(){
		Record record = new Record();
		List<Record> doubleAuditListAfterHandle = Db.find("select * from s6s_total where handle_status = 1 and dbl_check_status <>1 ");
		record.set("doubleAuditListAfterHandle",doubleAuditListAfterHandle);

		List<Record> doubleAuditList = Db.find("select * from s6s_total where check_status = 0 ");
		record.set("doubleAuditList",doubleAuditList);

		List<User> managerList = User.dao.find(
				"select ding_user_id from user where username ='admin' or username = 'manager'");
		record.set("managerList",managerList);


		List<Record> district = Db.find("select * from s6s_distriction ");
		record.set("district",district);

		record.set("status", 200);
		renderJson(record);
	}
//	public  void getDoubleAuditListAfterHandle(){
//		Record record = new Record();
//		List<Record> doubleAuditListAfterHandle = Db.find("select * from s6s_total where handle_status = 1 and dbl_handle_status <>1 ");
//		record.set("doubleAuditListAfterHandle",doubleAuditListAfterHandle);
//
//		List<User> managerList = User.dao.find(
//				"select ding_user_id from user where username ='admin' or username = 'manager'");
//
//		record.set("managerList",managerList);
//
//
//		List<Record> district = Db.find("select * from s6s_distriction ");
//		record.set("district",district);
//
//		record.set("status", 200);
//		renderJson(record);
//	}

	public void getHandleItem(){

		Record record = new Record();
		List<Record> handleItem =  Db.find("select * from s6s_total where id = '"+get("id")+"';");
		record.set("handleItem",handleItem);
		record.set("status",200);
		renderJson(record);
	}
	public void handleImpeach(){

		Db.update("update `s6s_total` set `first_req_date` = '"+get("first_req_date")+
				"' , `check_status` = "+get("check_status")+" , `score` = '"+get("score") +"' where `id` = '"+get("id")+"'");
		Record record = new Record();
		record.set("status",200);
		renderJson(record);
	}
	public void getPrizeList() {
		List<S6sPrizeList> PrizeList = S6sPrizeList.dao.find("select * from s6s_prize_list order by id ");
		Record resp = new Record();

		//获取个人总分
		String userid = getPara("userid");
		List<S6sTotal> total_score = S6sTotal.dao
				.find("select ifnull(SUM(score),0) total from s6s_total WHERE check_status = 0 and p_job_number = '" + userid+ "'");
		Object totalScore = total_score.get(0).get("total");
		int total = Integer.parseInt(String.valueOf(totalScore));

		//获取已使用的分数
		List<S6sPrizeExchangeList> used_score = S6sPrizeExchangeList.dao
				.find("select ifnull(SUM(score),0) total from s6s_prize_exchange_list WHERE apply_userid = '" + userid + "'");

		Object usedScore = used_score.get(0).get("total");
		int used = Integer.parseInt(String.valueOf(usedScore)) | 0;

		resp.set("PrizeList", PrizeList);
		resp.set("totalScore", total);
		resp.set("usedScore", used);
		resp.set("code", 200);
		renderJson(resp);

	}
	public  void  getGlovesList(){
		Record record =  new Record();
		String[] selectDates = get("selectDate").split("-");
		int curMonth = parseInt(selectDates[1]);
		int year = parseInt(selectDates[0]);
		if (curMonth==12){
			year++;
			curMonth=0;
		}
		List<S6sGloves> gloveList = S6sGloves.dao.find(
				"SELECT s.*,d.`glove_sequence` number " +
						"FROM s6s_gloves s \n" +
						"LEFT JOIN s6s_distriction d \n" +
						"ON s.`dis_name`=d.`dis_name` \n" +
						"WHERE s.release_date>='"+get("selectDate")+"-01' AND s.release_date<'"+year+"-"+(curMonth+1)+"-01' \n" +
						"ORDER BY release_date ASC,number ASC ");
		record.set("gloveList",gloveList);
		renderJson(record);

	}

	public void getMyMessage() {
		Record resp = new Record();
		String userid = getPara("userid");

		String sql = "SELECT SUM(score) total_scores,COUNT(*) total_items FROM s6s_total WHERE check_status = 1 and p_job_number = '"
				+ userid + "' ";
		List<S6sTotal> scores = S6sTotal.dao.find(sql);

		String sql2 = "select SUM(score) total from s6s_prize_exchange_list WHERE apply_userid = '" + userid + "'";
		List<S6sPrizeExchangeList> used_score = S6sPrizeExchangeList.dao.find(sql2);

		String sql3 = "select * from s6s_prize_exchange_list WHERE apply_userid = '" + userid + "'";
		List<S6sPrizeExchangeList> myExchangeList = S6sPrizeExchangeList.dao.find(sql3);

		String sql4 = "select * from s6s_total where p_job_number = '" + userid + "' ";
		List<S6sTotal> myImpeachList = S6sTotal.dao.find(sql4);

		resp.set("scores", scores);
		resp.set("used_score", used_score);
		resp.set("myExchangeList", myExchangeList);
		resp.set("myImpeachList", myImpeachList);

		resp.set("code", 200);
		renderJson(resp);

	}



	public void saveGloves(){
		S6sGloves s6sGloves = getModel(S6sGloves.class,"");
		System.out.println(s6sGloves.get("dis_name").toString());
		s6sGloves.save();
		Record record = new Record();
		record.set("status",200);
		renderJson(record);
	}

}
