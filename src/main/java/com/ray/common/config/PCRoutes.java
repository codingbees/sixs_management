package com.ray.common.config;

import com.jfinal.config.Routes;
import com.ray.controller.admin.LtestController;
import com.ray.controller.admin.TestController;

public class PCRoutes extends Routes {

	@Override
	public void config() {
		this.setBaseViewPath("/page");
		this.add("/ltest",LtestController.class);
		this.add("/test",TestController.class);

	}

}