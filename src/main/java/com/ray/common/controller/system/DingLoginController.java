package com.ray.common.controller.system;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiSnsGetuserinfoBycodeRequest;
import com.dingtalk.api.request.OapiUserGetRequest;
import com.dingtalk.api.request.OapiUserGetUseridByUnionidRequest;
import com.dingtalk.api.request.OapiUserGetuserinfoRequest;
import com.dingtalk.api.response.OapiSnsGetuserinfoBycodeResponse;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.dingtalk.api.response.OapiUserGetUseridByUnionidResponse;
import com.dingtalk.api.response.OapiUserGetuserinfoResponse;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.ray.common.model.UserRole;
import com.ray.common.ding.AccessTokenUtil;
import com.taobao.api.ApiException;

public class DingLoginController extends Controller {
	/**
	 * WEB端扫码登录
	 * @throws ApiException
	 */
	public void index() throws ApiException{ 
		DefaultDingTalkClient  client = new DefaultDingTalkClient("https://oapi.dingtalk.com/sns/getuserinfo_bycode");
		OapiSnsGetuserinfoBycodeRequest req = new OapiSnsGetuserinfoBycodeRequest();
		req.setTmpAuthCode(getPara("code"));
		OapiSnsGetuserinfoBycodeResponse response = client.execute(req,"dingoawhk0qy7xdambxeqc","IYXWOiIegeBcGZGUWDbyAFOzigW80PrVJbQ7I4J6a7DCFHDbHG-N_hLLixD_-XJ9");
		String dingtoken = AccessTokenUtil.getToken();
		//获取UserId
		DingTalkClient client2 = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/getUseridByUnionid");
		OapiUserGetUseridByUnionidRequest request2 = new OapiUserGetUseridByUnionidRequest();
		request2.setUnionid(response.getUserInfo().getUnionid());
		request2.setHttpMethod("GET");
		OapiUserGetUseridByUnionidResponse userInfo = client2.execute(request2, dingtoken);
		
		if(0==userInfo.getErrcode()){
			//获取用户详情
			DingTalkClient client3 = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get");
			OapiUserGetRequest request = new OapiUserGetRequest();
			request.setUserid(userInfo.getUserid());
			request.setHttpMethod("GET");
			OapiUserGetResponse response3 = client3.execute(request, dingtoken);
			Record user = Db.findFirst("select * from user where ding_user_id = '"+response3.getUserid()+"'");
			if(user==null) {
				user = new Record();
				user.set("username", response3.getMobile());
				user.set("password", response3.getMobile());
				user.set("nickname", response3.getName());
				user.set("ding_user_id", response3.getUserid());
				Db.save("user", user);
				UserRole ur = new UserRole();
				ur.setRoleId(2);
				ur.setUserId(user.get("id"));
				ur.save(); 
			}
			user.set("dingUserInfo", response3);
			UsernamePasswordToken token = new UsernamePasswordToken(user.get("username"), user.getStr("password"));
			Subject subject = SecurityUtils.getSubject();
			subject.login(token);
			Record record = Db.findFirst("SELECT GROUP_CONCAT(role_name) AS roles FROM roles WHERE id IN (SELECT role_id FROM user_role WHERE user_id = '"+user.get("id")+"')");
			user.set("roles", record.getStr("roles"));
			subject.getSession().setAttribute("user", user);
			redirect("/index");
		}else{
			redirect("/loginInit?code=1&icon=5");
		}
	}
	
	/**
	 * 钉钉PC端免登
	 */
	public void dingLogin(){
		String accessToken = AccessTokenUtil.getToken();
		DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/getuserinfo");
		OapiUserGetuserinfoRequest request = new OapiUserGetuserinfoRequest();
		request.setCode(get(0));
		request.setHttpMethod("GET");
		OapiUserGetuserinfoResponse response = null;
		try {
			response = client.execute(request, accessToken);
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String userId = response.getUserid();
		DingTalkClient client3 = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get");
		OapiUserGetRequest request1 = new OapiUserGetRequest();
		request1.setUserid(userId);
		request1.setHttpMethod("GET");
		OapiUserGetResponse userinfo = null;
		try {
			userinfo = client3.execute(request1, accessToken);
			Record user = Db.findFirst("select * from user where ding_user_id = '"+userinfo.getUserid()+"'");
			if(user==null) {
				user = new Record();
				user.set("username", userinfo.getMobile());
				user.set("password", userinfo.getMobile());
				user.set("nickname", userinfo.getName());
				user.set("ding_user_id", userinfo.getUserid());
				Db.save("user", user);
				UserRole ur = new UserRole();
				ur.setRoleId(2);
				ur.setUserId(user.getInt("id"));
				ur.save(); 
			}
			user.set("dingUserInfo", userinfo);
			UsernamePasswordToken token = new UsernamePasswordToken(user.get("username"), user.getStr("password"));
			Subject subject = SecurityUtils.getSubject();
			subject.login(token);
			Record record = Db.findFirst("SELECT GROUP_CONCAT(role_name) AS roles FROM roles WHERE id IN (SELECT role_id FROM user_role WHERE user_id = '"+user.get("id")+"')");
			user.set("roles", record.getStr("roles"));
			subject.getSession().setAttribute("user", user);
			redirect("/index");
		} catch (ApiException e) {
			e.printStackTrace();
			redirect("/loginInit?code=1&icon=5");
		}
	}
}
