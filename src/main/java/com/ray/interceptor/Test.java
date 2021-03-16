package com.ray.interceptor;

import java.util.Date;

import com.jfinal.plugin.activerecord.Record;
import com.ray.common.interceptor.AopContext;
import com.ray.common.interceptor.MetaObjectIntercept;

public class Test extends MetaObjectIntercept{
	
	@Override
	public String queryBefore(AopContext ac) throws Exception {
		ac.sql = ac.sql+" and create_user_id = '"+ac.user.get("id")+"'";
		return ac.sql;
	}
	
	@Override
	public String addBefore(AopContext ac) throws Exception {
		ac.record.set("create_user_id", ac.user.get("id"));
		ac.record.set("create_user_name", ac.user.get("nickname"));
		return null;
	}
	
	@Override
	public String addAfter(AopContext ac) throws Exception {
		return null;
	}
	
	@Override
	public String addSucceed(AopContext ac) throws Exception {
		Record record = new Record();
		record.set("tt_id", ac.record.get("id"));
		return "事务外，无法新增关联表";
	}
	
	@Override
	public String updateBefore(AopContext ac) throws Exception {
		ac.record.set("update_time", new Date());
		return null;
	}
	
	@Override
	public String updateAfter(AopContext ac) throws Exception {
//		Record record = Db.findFirst("select * from aa where tt_id = "+ac.record.getInt("id"));
//		record.set("tt_id", 999);
//		Db.update("aa", record);
		return null;
	}
	
	@Override
	public String updateSucceed(AopContext ac) throws Exception {
		return null;
	}
	
	@Override
	public String deleteBefore(AopContext ac) throws Exception {
		if(ac.record.getInt("statu")==1) {
			return "项目进行中，无法删除";
		}else {
			return null;
		}
	}
}
