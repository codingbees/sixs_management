package com.ray.controller.admin;

import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Record;
import com.ray.common.controller.BaseController;
import com.ray.common.model.S6sDistriction;
import com.ray.event.UserSaveEvent;

import net.dreamlu.event.EventKit;

import java.util.List;

public class TestController extends BaseController {
	
	
	public void ttt() {
//		YwTest model = getModel(YwTest.class, "",true);
//		model.setName(get("comboValue"));
//		model.update();
		Record user = (Record)getSessionAttr("user");
		EventKit.post(new UserSaveEvent(user));
		renderJson(Ret.ok("msg", "嘻嘻"));
	}
	public void getdata(){
		Record record = new Record();
		List<S6sDistriction> total = S6sDistriction.dao.find("select principal,dis_name,id as dis_id,staff_qty from s6s_distriction ");
		record.set("total",total);
		renderJson(record);
	}
}
