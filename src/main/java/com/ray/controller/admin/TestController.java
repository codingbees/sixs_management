package com.ray.controller.admin;

import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Record;
import com.ray.common.controller.BaseController;
import com.ray.event.UserSaveEvent;

import net.dreamlu.event.EventKit;

public class TestController extends BaseController {
	
	
	public void ttt() {
//		YwTest model = getModel(YwTest.class, "",true);
//		model.setName(get("comboValue"));
//		model.update();
		Record user = (Record)getSessionAttr("user");
		EventKit.post(new UserSaveEvent(user));
		renderJson(Ret.ok("msg", "嘻嘻"));
	}
}
