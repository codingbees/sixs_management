package com.ray.common.config;

import com.jfinal.config.Routes;
import com.ray.controller.app.AppCheckController;
import com.ray.controller.app.AppController;
import com.ray.controller.app.PPController;
import com.ray.controller.app.SixSController;

public class DDRoutes extends Routes {

	@Override
	public void config() {
		this.add("/app",AppController.class);
		this.add("/app/check",AppCheckController.class);
		this.add("/app/sixs", SixSController.class);
		this.add("/app/pp", PPController.class);
	}

}