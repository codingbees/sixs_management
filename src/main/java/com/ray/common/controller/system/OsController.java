package com.ray.common.controller.system;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiUserGetRequest;
import com.dingtalk.api.request.OapiUserSimplelistRequest;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.dingtalk.api.response.OapiUserSimplelistResponse;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.ray.common.ding.AccessTokenUtil;
import com.taobao.api.ApiException;
/**
 * 组织架构
 * @author Ray
 *
 */
public class OsController extends Controller {
	
	/**
	 * 跳转组织架构模块
	 */
	public void index(){
		render("os.html");
	}
	
	/**
	 * 获取所有部门
	 * @throws ApiException 
	 */
	/*public void getOrg() throws ApiException{
		DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list");
		OapiDepartmentListRequest request = new OapiDepartmentListRequest();
		request.setId("1");
		request.setFetchChild(true);
		request.setHttpMethod("GET");
		OapiDepartmentListResponse response = client.execute(request, AccessTokenUtil.getToken());
		List<Os> oss = new ArrayList<>();
		List<Department> departments = response.getDepartment();
		oss = recursionOs(Long.valueOf(1),departments);
		Record rsp = new Record();
		rsp.set("code", 0);
		rsp.set("data", oss);
		renderJson(rsp);
	}*/
	
	/**
	 * 递归整理组织架构JSON格式
	 * @param id
	 * @param departments
	 */
	/*@NotAction
	public List<Os> recursionOs(Long parentId,List<Department> departments){
		List<Os> oss = new ArrayList<>();
		for (int i = 0; i < departments.size(); i++) {
			if(departments.get(i).getParentid().equals(parentId)){
				Os os = new Os();
				os.setId(departments.get(i).getId());
				os.setLabel(departments.get(i).getName());
				os.setChildren(recursionOs(departments.get(i).getId(),departments));
				oss.add(os);
			}
		}
		return oss;
	}*/
	
	/**
	 * 根据部门ID获取用户列表
	 * @throws ApiException 
	 */
	public void getUserListByDepartId() throws ApiException{
		DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/simplelist");
		OapiUserSimplelistRequest request = new OapiUserSimplelistRequest();
		request.setDepartmentId(getParaToLong("departId"));
		request.setHttpMethod("GET");
		OapiUserSimplelistResponse response = client.execute(request, AccessTokenUtil.getToken());
		Record layTableRes = new Record();
		layTableRes.set("code", Integer.valueOf(0));
		layTableRes.set("msg", "");
		layTableRes.set("count", Integer.valueOf(response.getUserlist().size()));
		layTableRes.set("data", response.getUserlist());
		renderJson(layTableRes);
	}
	
	/**
	 * 查看员工详细信息
	 * @throws ApiException 
	 */
	public void userinfo() throws ApiException{
		DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get");
		OapiUserGetRequest request = new OapiUserGetRequest();
		request.setUserid(getPara(0));
		request.setHttpMethod("GET");
		OapiUserGetResponse response = client.execute(request, AccessTokenUtil.getToken());
		setAttr("user", response);
		Record roles = Db.findFirst("SELECT GROUP_CONCAT(role_id) role_ids FROM user_role WHERE user_id = '" + response.getUserid()+"'");
		setAttr("roleIds", roles.get("role_ids"));
		render("userinfo.html");
	}
	
	/**
	 * 弹出选择人员框
	 */
	public void selectUser(){
		render("dingSelectUser.html");
	}
}
