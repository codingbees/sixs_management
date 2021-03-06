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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.ray.util.Commen.getDaysOfMonth;
import static java.lang.Integer.compareUnsigned;
import static java.lang.Integer.parseInt;

//import com.dingtalk.api.request.OapiUserGetByMobileRequest;

public class SixSController extends Controller {

	public void getSixSTotalListByMonth() throws ParseException {
		Record res = new Record();
		String[] selectDates = get("checkDate").split("-");
		int checkYear = parseInt(selectDates[0]);
		DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");
		Date date = fmt.parse(get("checkDate")+"-01");
		int day_qty = getDaysOfMonth(date);

		String sql1 = "SELECT t1.*,q.`staff_qty` staff_of_select_month FROM\n" +
				"(SELECT sd.*,IFNULL(tab.total,0) AS tabtotal,IFNULL(tab.closedq,0) AS closed FROM s6s_distriction sd\n" +
				"LEFT JOIN (\n" +
				"SELECT s6.`district_id`,s6.district,COUNT(s6.check_status) AS `total`, SUM(s6.handle_status) AS `closedq`\n" +
				"FROM s6s_total s6 \n" +
				"WHERE s6.first_req_date>='"+get("checkDate")+"-01' AND s6.first_req_date<='"+get("checkDate")+"-"+day_qty+"' AND s6.check_status = 1 " +
				" GROUP BY s6.district_id) tab\n" +
				"ON sd.id=tab.`district_id`) t1\n" +
				"LEFT JOIN s6s_staff_qty q\n" +
				"ON t1.`id` = q.`dis_id`\n" +
				"WHERE q.moth_date>='"+get("checkDate")+"-01' AND q.moth_date<='"+get("checkDate")+"-"+day_qty+"' ";
//		String sql3 = "SELECT sd.*,IFNULL(tab.total,0) AS tabtotal,IFNULL(tab.closedq,0) AS closed FROM s6s_distriction sd\n" +
//				"LEFT JOIN (\n" +
//				"SELECT s6.`district_id`,s6.district,COUNT(s6.check_status) AS `total`, SUM(s6.handle_status) AS `closedq`\n" +
//				"FROM s6s_total s6 \n" +
//				"WHERE s6.first_req_date>='"+get("checkDate")+"-01' AND s6.first_req_date<='"+get("checkDate")+"-"+day_qty+"' AND s6.check_status = 1 " +
//				"GROUP BY s6.district_id) tab\n" +
//				"ON sd.id=tab.`district_id`";
		List<Record> recordList = Db.find(sql1);
		String  sqlx = "select * from s6s_kpi_target where year = '"+checkYear+"'";
		List<Record> kpi_target = Db.find(sqlx);
		res.set("kpi_target",kpi_target);
		res.set("totalList",recordList);
		res.set("code",200);
		renderJson(res);
	}
	//
	public void getAwardList() throws ParseException {
		Record res = new Record();
		String[] selectDates = get("checkDate").split("-");
		DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");
		Date date = fmt.parse(get("checkDate")+"-01");
		int day_qty = getDaysOfMonth(date);


		String sql = "select * from s6s_award_record s "+
				"WHERE s.date_time>='"+get("checkDate")+"-01' AND s.date_time<='"+get("checkDate")+"-"+day_qty+"' ";

		List<Record> recordList = Db.find(sql);
		res.set("totalListAward",recordList);
		res.set("code",200);
		renderJson(res);
	}
	//???????????????  onLoad????????????????????????
	public void getAllList(){

		Record res = new Record();
		String[] selectDates = get("selectDate").split("-");
		int curMonth = parseInt(selectDates[1]);
		int year = parseInt(selectDates[0]);
		if (curMonth==12){
			year++;
			curMonth=0;
		}
		String sql1 = "select * from s6s_distriction";
		List<Record> districtList = Db.find(sql1);
		//?????????id???????????????
		String sql2 = "select id,dis_name from s6s_distriction";
		List<Record> districtListWithId = Db.find(sql2);
		//??????????????????
		String dis_id = districtList.get(0).get("id").toString();

		String sql = "select * from s6s_total s where district_id = '"+dis_id+"' and  handle_status = '0' AND s.first_req_date<'"+year+"-"+(curMonth+1)+"-01' and s.check_status = 1;";
		List<Record> totalList = Db.find(sql);
		String sql3 = " and s.first_req_date>='"+get("selectDate")+"-01' AND s.first_req_date<'"+year+"-"+(curMonth+1)+"-01' and s.check_status = 1 ";
		//??????????????????
		String sql5 = "select count(*) notClosedQty from s6s_total s where district_id ='"+dis_id+"' and s.handle_status = 0";
		List<Record> notClosedQty = Db.find(sql5+sql3);
		//????????????
		String sql6 = "select count(*) totalQty from s6s_total s where district_id = '"+dis_id+"' ";
		List<Record> totalQty = Db.find(sql6+sql3);


		//???????????????????????????
		String sql_handler = "select *  from s6s_handlers where dis_id = '"+dis_id+"' ";
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
	//??????????????? ????????????????????????
	public void getLatestData(){
		Record res = new Record();
		String[] selectDates = get("selectDate").split("-");
		int curMonth = parseInt(selectDates[1]);
		int checkYear = parseInt(selectDates[0]);
		int year = parseInt(selectDates[0]);
		if (curMonth==12){
			year++;
			curMonth=0;
		}

		String sql1 = "select * from s6s_total s where s.district_id  = '"+get("selectDistrictId")+"' ";
		String sql2="";
		//?????????2??????????????????
		if(!"2".equals(get("selectStatus"))){
			sql2  = " and s.handle_status = '"+get("selectStatus")+"'";
		}
		String sql3 = " and s.first_req_date>='"+get("selectDate")+"-01' AND s.first_req_date<'"+year+"-"+(curMonth+1)+"-01' and s.check_status = 1";
		List<Record> totalList = Db.find(sql1+sql2+sql3);

		//????????????
		String sql4 = "select count(*) totalQty from s6s_total s where s.district_id  = '"+get("selectDistrictId")+"'";
		List<Record> totalQty = Db.find(sql4+sql3);
		//??????????????????
		String sql5 = "select count(*) notClosedQty from s6s_total s where s.district_id  = '"+get("selectDistrictId")+
				"'  and s.handle_status = 0 ";
		List<Record> notClosedQty = Db.find(sql5+sql3);

		String sql_handler = "select *  from s6s_handlers where dis_id = '"+get("selectDistrictId")+"'";
		List<Record> handlerList = Db.find(sql_handler);

		String  sqlx = "select * from s6s_kpi_target where year = '"+checkYear+"'";
		List<Record> kpi_target = Db.find(sqlx);
		res.set("kpi_target",kpi_target);
		res.set("handlerList",handlerList);
		res.set("totalList",totalList);
		res.set("totalQty",totalQty);
		res.set("notClosedQty",notClosedQty);
		res.set("code",200);
		renderJson(res);
	}
	//????????????
	public void uploadFile(){

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
	//?????????????????????????????????????????????id??????
	public  void savePicName(){
		S6sTotal s6sTotal = getModel(S6sTotal.class,"");

		s6sTotal.update();
		Record rsp = new Record();

		rsp.set("status", 200);
		renderJson(rsp);
	}
	//??????????????????????????????????????????id???????????????
	public void saveLeanCheckData(){
		S6sTotal s6sTotal = getModel(S6sTotal.class,"");
		s6sTotal.set("check_status",1);
		s6sTotal.save();
		Record rsp = new Record();
		rsp.set("status", 200);
		renderJson(rsp);
	}
	//???????????????????????????????????????????????????
	public void updateDoubleAuditStatus(){
		S6sTotal s6sTotal = getModel(S6sTotal.class,"");

		s6sTotal.update();
		Record rsp = new Record();

		rsp.set("status", 200);
		renderJson(rsp);
	}
	//????????????????????????
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
		List<Record> list = Db.find("select * from s6s_distriction ");
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
				"SELECT * FROM `user` WHERE id IN (" +
						"SELECT user_id FROM user_role WHERE role_id = 2)");
		record.set("managerList",managerList);


		List<Record> district = Db.find("select * from s6s_distriction ");
		record.set("district",district);

		record.set("status", 200);
		renderJson(record);
	}

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

		//??????????????????
		String userid = getPara("userid");
		List<S6sTotal> total_score = S6sTotal.dao
				.find("select ifnull(SUM(score),0) total from s6s_total WHERE check_status = 0 and p_job_number = '" + userid+ "'");
		Object totalScore = total_score.get(0).get("total");
		int total = Integer.parseInt(String.valueOf(totalScore));

		//????????????????????????
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
						"ON s.`district_id`=d.`id` \n" +
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
//		System.out.println(s6sGloves.get("dis_name").toString());
		s6sGloves.save();
		Record record = new Record();
		record.set("status",200);
		renderJson(record);
	}
	public void getdata(){
		Record record = new Record();
		List<S6sDistriction> total = S6sDistriction.dao.find("select principal,dis_name,id as dis_id,staff_qty from s6s_distriction ");
		record.set("total",total);
		renderJson(record);
	}

	public void saveStaffQty(){
		S6sStaffQty s6sStaffQty = getModel(S6sStaffQty.class,"");

		s6sStaffQty.save();
		Record record = new Record();
		record.set("status",200);
		renderJson(record);
	}
	// ?????????????????????????????????????????????????????????
	public void checkIsLeader() {
		String user_jobnumber = get("jobnumber");
		Boolean isHandler = false;
		Boolean isLeanManager = false;
		List<S6sHandlers> auditorForLeader = S6sHandlers.dao.find("select * from s6s_handlers");

		for (int i = 0; i < auditorForLeader.size(); i++) {
			if (auditorForLeader.get(i).getPJobNumber().equals(user_jobnumber)) {
				isHandler = true;
			}
		}
		Record resp = new Record();
		resp.set("code", 0);
		resp.set("isHandler", isHandler);

		List<User> isManager = User.dao.find("select * from ( SELECT * FROM `user` WHERE id IN (" +
				"SELECT user_id FROM user_role WHERE role_id = 2)) t where t.job_number = '"+user_jobnumber+"' ");
		if(isManager.size()==1) {
			isLeanManager = true;
		}

		List<User> userManager = User.dao.find("SELECT * FROM `user` WHERE id IN (" +
				"SELECT user_id FROM user_role WHERE role_id = 2)");
		String[] leanManagerName = new String[userManager.size()];
		String[] leanManagerJobNumber = new String[userManager.size()];

		for (int i = 0; i < userManager.size(); i++) {
			leanManagerName[i] = userManager.get(i).getNickname();
			leanManagerJobNumber[i] = userManager.get(i).getJobNumber();
		};

		resp.set("leanManagerName", leanManagerName);
		resp.set("leanManagerJobNumber", leanManagerJobNumber);
		resp.set("isLeanManager", isLeanManager);
		renderJson(resp);

	}

}
