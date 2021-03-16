package com.ray.common.config;

import com.jfinal.config.Routes;
import com.ray.common.controller.base.DataController;
import com.ray.common.controller.base.QuartzController;
import com.ray.common.controller.base.SingleController;
import com.ray.common.controller.base.ZZController;

public class BaseRoutes extends Routes {

	@Override
	public void config() {
		this.setBaseViewPath("/page");
		this.add("/single", SingleController.class,"sys/template");
		this.add("/zz", ZZController.class,"sys/template");
		this.add("/data",DataController.class,"sys/data");
		this.add("/quartz",QuartzController.class);
	}

}