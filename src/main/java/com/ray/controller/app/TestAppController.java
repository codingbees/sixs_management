package com.ray.controller.app;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

public class TestAppController extends Controller {
	
    public void login() throws ApiException {
        //获取accessToken,注意正是代码要有异常流处理
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
        System.out.println("userId is :");
        System.out.println(userId);
        
        OapiUserGetResponse userinfo = getUserInfo(accessToken, userId);
        System.out.println("userinfo is:");
        System.out.println(userinfo);
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
        System.out.println("departments--------------------------------------------");
        System.out.println(departs);
        /*Map<String, Object> map = FastJson.getJson().parse(userinfo.getBody(), Map.class);
        Record user = new Record().setColumns(map);*/
        //返回结果
        Map<String, Object> resultMap = new HashMap<>();
        JSONObject jb = JSONObject.parseObject(userinfo.getBody());
        jb.put("departments", departs);
        resultMap.put("userinfo", jb.toString());
        ServiceResult serviceResult = ServiceResult.success(resultMap);
        System.out.println("serviceResult is:");
        System.out.println(serviceResult.toString());
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
            System.out.println("getUserInfo success");
            return response;
        } catch (ApiException e) {
        	System.out.println("getUserInfo failed");
            e.printStackTrace();
            return null;
        }
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
