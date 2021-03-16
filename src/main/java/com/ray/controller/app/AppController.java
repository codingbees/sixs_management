package com.ray.controller.app;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import com.ray.common.model.RationalProposal;
import com.ray.common.model.RpImproveType;
import com.ray.common.model.RpLineStructure;
import com.ray.common.model.RpShiftLeader;
import com.ray.common.model.User;
import com.ray.common.model.UserRole;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiDepartmentGetRequest;
import com.dingtalk.api.request.OapiUserGetRequest;
import com.dingtalk.api.request.OapiUserGetuserinfoRequest;
import com.dingtalk.api.response.OapiDepartmentGetResponse;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.dingtalk.api.response.OapiUserGetuserinfoResponse;
import com.jfinal.core.Controller;
import com.jfinal.core.NotAction;
import com.jfinal.kit.HttpKit;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;
import com.taobao.api.ApiException;
import com.ray.common.ding.*;
import com.ray.util.Commen;
import com.ray.util.SerialNumberUtil;
import com.ray.util.ServiceResult;

public class AppController extends Controller {
	
    public void login() throws ApiException {
        //获取accessToken,正式代码要有异常流处理
        String accessToken = AccessTokenUtil.getToken();

        //获取用户信息
        DingTalkClient client = new DefaultDingTalkClient(Env.URL_GET_USER_INFO);
        OapiUserGetuserinfoRequest request = new OapiUserGetuserinfoRequest();
        request.setCode(getPara("authCode"));
        request.setHttpMethod("GET");

        OapiUserGetuserinfoResponse response = new OapiUserGetuserinfoResponse();
        try {
            response = client.execute(request, accessToken);
        } catch (ApiException e) {
            e.printStackTrace();
            renderNull();
        }
        //3.查询得到当前用户的userId
        // 获得到userId之后应用应该处理应用自身的登录会话管理（session）,避免后续的业务交互（前端到应用服务端）每次都要重新获取用户身份，提升用户体验
        String userId = response.getUserid();   
        OapiUserGetResponse userinfo = getUserInfo(accessToken, userId);
        //获取用户部门名称
        String departs = "";
        for (int i = 0; i < userinfo.getDepartment().size(); i++) {
        	DingTalkClient client1 = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/get");
            OapiDepartmentGetRequest request1 = new OapiDepartmentGetRequest();
            request1.setId(userinfo.getDepartment().get(i).toString());
            request1.setHttpMethod("GET");
            OapiDepartmentGetResponse response1 = client1.execute(request1, accessToken);
            departs += response1.getName()+",";
		}
        departs = departs.substring(0, departs.length()-1);

        //返回结果
        Map<String, Object> resultMap = new HashMap<>();
        JSONObject jb = JSONObject.parseObject(userinfo.getBody());
        jb.put("departments", departs);
        resultMap.put("userinfo", jb.toString());
        ServiceResult serviceResult = ServiceResult.success(resultMap);

        renderJson(serviceResult);
    }
    
    /**
     * 获取用户详情
     *
     * @param accessToken
     * @param userId
     * @return
     */
    @NotAction
    private OapiUserGetResponse getUserInfo(String accessToken, String userId) {
        try {
            DingTalkClient client = new DefaultDingTalkClient(Env.URL_USER_GET);
            OapiUserGetRequest request = new OapiUserGetRequest();
            request.setUserid(userId);
            request.setHttpMethod("GET");
            OapiUserGetResponse response = client.execute(request, accessToken);
//            System.out.println("getUserInfo success");
            return response;
        } catch (ApiException e) {
        	System.out.println("getUserInfo failed");
            e.printStackTrace();
            return null;
        }
    }
    public void getDepartment() {
    	List<RpLineStructure> ws = RpLineStructure.dao.find("select * from rp_line_structure where id >= 1 and id <=10");
    	Record	resp = new Record();
    	String[] array = new String[ws.size()];
    	for (int i = 0; i < ws.size(); i++) {
			array[i] = ws.get(i).getProductionLine();
		}
    	resp.set("arrayDep", array);
    	resp.set("code",200);
    	renderJson(resp);
    }
    public void getWorkshop() {
    	String selectDep = get("selectDep");
    	Record	resp = new Record();
    	String sql1 = "select * from rp_line_structure where pid =(select id from rp_line_structure where productionLine='"+selectDep+"' ) ";
    	String sql2 = "select * from rp_line_structure where pid = (select id from rp_line_structure  where pid =(select id from rp_line_structure where productionLine='"+selectDep+"' ) ORDER BY id ASC  LIMIT 1) ";
    	String sql3 ="select * from rp_shift_leader where pid=(select id from rp_line_structure  where pid =(select id from rp_line_structure where productionLine='"+selectDep+"' ) ORDER BY id ASC  LIMIT 1) ";
    	
    	//若未选择车间
    	if("".equals(selectDep) || selectDep==null) {
    		sql1 = "select * from rp_line_structure where pid >= 1 and pid <=10";
    		sql2 = "select * from rp_line_structure where pid = 11";
    		sql3 = "select * from rp_shift_leader where pid = 11";
    	}
    	
    	List<RpLineStructure> ws = RpLineStructure.dao.find(sql1);
    	String[] array = new String[ws.size()];
    	for (int i = 0; i < ws.size(); i++) {
			array[i] = ws.get(i).getProductionLine();
		}
    	resp.set("array", array);
    	
    	List<RpLineStructure> Line = RpLineStructure.dao.find(sql2);
		String[] arrayLine = new String[Line.size()];
    	for (int i = 0; i < Line.size(); i++) {
    		arrayLine[i] = Line.get(i).getProductionLine();
		}
    	resp.set("arrayLine", arrayLine);
    	//查询审核人
    	List<RpShiftLeader> auditor = RpShiftLeader.dao.find(sql3);
    	String[] arrayAuditor = new String[auditor.size()];
		String[] arrayAuditorNo = new String[auditor.size()];
    	for (int i = 0; i < auditor.size(); i++) {
    		arrayAuditor[i] = auditor.get(i).getShiftLeader();
    		arrayAuditorNo[i] = auditor.get(i).getShiftLeaderJobNo();
    	}
    	resp.set("arrayAuditor", arrayAuditor);
    	resp.set("arrayAuditorJobNo", arrayAuditorNo);
    	resp.set("code",200);
    	renderJson(resp);
    }
    public void getType() {
    	List<RpImproveType> type =RpImproveType.dao.findAll();
    	Record	resp = new Record();
    	String[] arrayType = new String[type.size()];
    	for (int i = 0; i < type.size(); i++) {
    		arrayType[i] = type.get(i).getType();
		}
    	resp.set("arrayType", arrayType);
    	resp.set("code",200);
    	renderJson(resp);
    }
    
    public	void getLine() {
    	String selectws = get("selectws");
    	Record	resp = new Record();
    	//查询精益管理员
    	List<User> userManager = User.dao.find("select * from user where username = 'manager'");
    	
//        String[] managerName = {userManager.get(0).getNickname()};
        String[] managerId = {userManager.get(0).getDingUserId()};
        resp.set("leanUserlist", managerId);

    	String sql1 = "select * from rp_line_structure where pid in (select id from rp_line_structure where productionLine='"+selectws+"' ) ";
    	String sql2 = "select * from rp_shift_leader where pid = (select id from rp_line_structure where productionLine='"+selectws+"')";
    	if("".equals(selectws) || selectws==null) {
    		sql1="select productionLine from rp_line_structure where pid = 11";
    		sql2="select * from rp_shift_leader where pid = 11";
    	}
    	
    	List<RpLineStructure> Line = RpLineStructure.dao.find(sql1);
    	List<RpShiftLeader> auditor =RpShiftLeader.dao.find(sql2);
    	
    	
    	
    	String[] arrayLine = new String[Line.size()];
    	for (int i = 0; i < Line.size(); i++) {
    		arrayLine[i] = Line.get(i).getProductionLine();
		}
    	resp.set("arrayLine", arrayLine);
    	
    	String[] arrayAuditor = new String[auditor.size()];
		String[] arrayAuditorNo = new String[auditor.size()];
    	for (int i = 0; i < auditor.size(); i++) {
    		arrayAuditor[i] = auditor.get(i).getShiftLeader();
    		arrayAuditorNo[i] = auditor.get(i).getShiftLeaderJobNo();
    	}
    	
    	resp.set("arrayAuditor", arrayAuditor);
    	resp.set("arrayAuditorJobNo", arrayAuditorNo);
    	resp.set("code",200);
    	renderJson(resp);
    	
    }
    public	void submitProposal() {
    	Record resp = new Record();
    	try {
    		RationalProposal qp = getModel(RationalProposal.class,"");
    		qp.setCreateTime(new Date());
			qp.setRpNo("FLPMRP"+SerialNumberUtil.generator("part_check_no"));
			String name = qp.get("find_username");
//			System.out.println(name);
			qp.save();
			resp.set("code", 200);
			resp.set("msg", "发起问题成功！");
		} catch (Exception e) {
			resp.set("code", 0);
			resp.set("msg", "发起问题失败，原因："+e.getMessage());
			e.printStackTrace();
		}
    	renderJson(resp);
    }
    //上传图片
    public void uploadFile(){

    	String picName = UUID.randomUUID().toString();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
    	String d = sdf.format(new Date());

    	UploadFile file = getFile("file","rationalproposal");
    	Record rsp = new Record();
		file.getFile().renameTo(new File("D:\\apache-tomcat-8.0.26\\webapps\\rationalproposal\\"+picName+".jpg"));
		String fileName = ""+d+"_check_"+picName+".jpg;";
		rsp.set("fileName", fileName);
		rsp.set("picName", picName+".jpg;");
    	renderJson(rsp);
    }
    @SuppressWarnings("null")
	public void getAuditor() {
    	Record	resp = new Record();
    	String userid = getPara("userid");
    	
    	if("".equals(userid) || userid==null) {
    		return;
    	}
    	
    	List<User> userManager = User.dao.find("select * from user where username = 'manager'");
        String[] managerName = {null} ;
        String[] managerId = {null};
        Boolean isShiftLeader = false;
        
		List<RpShiftLeader> auditorForLeader = RpShiftLeader.dao.find("select * from rp_shift_leader");
		//如果发起人是班长，返回精益管理员信息
    	for (int i = 0; i < auditorForLeader.size(); i++) {
    		if(auditorForLeader.get(i).getShiftLeaderJobNo().equals(userid)) {
    			isShiftLeader = true;
    			managerName[0] = userManager.get(0).getNickname();
    			managerId[0] = userManager.get(0).getDingUserId();
    			
    		}
    	}

    	String selectws = getPara("selectws");
    	String sql = "select * from rp_shift_leader where pid = (select id from rp_line_structure where productionLine='"+selectws+"'";
    	//如果未选择车间则默认返回pid为11的车间的审核人
    	if("".equals(selectws) || selectws==null) {
    		sql = "select * from rp_shift_leader where pid = 11";
    		
    	}
    	
    	List<RpShiftLeader> auditor =RpShiftLeader.dao.find(sql);
    	String[] arrayAuditor = new String[auditor.size()];
		String[] arrayAuditorNo = new String[auditor.size()];
		
    	for (int i = 0; i < auditor.size(); i++) {
    		arrayAuditor[i] = auditor.get(i).getShiftLeader();
    		arrayAuditorNo[i] = auditor.get(i).getShiftLeaderJobNo();

    	}
    	
    	resp.set("isShiftLeader", isShiftLeader);
		resp.set("managerName", managerName);
    	resp.set("managerId", managerId);
    	resp.set("arrayAuditor", arrayAuditor);
    	resp.set("arrayAuditorJobNo", arrayAuditorNo);
    	resp.set("code",200);

    	renderJson(resp);
    }
	
    /**
     * 
     */
    public void getLeaderList(){
    	List<UserRole> ur = UserRole.dao.find("select * from user_role where role_id = 14");
    	String[] leaderArray = new String[ur.size()];
    	for (int i = 0; i < leaderArray.length; i++) {
    		leaderArray[i] = ur.get(i).getStr("user_id");
		}
    	Record resp = new Record();
    	resp.set("code", 0);
    	resp.set("leaders", leaderArray);
    	renderJson(resp);
    }
}
