package com.ray.common.config;

import com.jfinal.config.Routes;
import com.ray.common.controller.CommonController;
import com.ray.common.controller.MainController;
import com.ray.common.controller.system.DingLoginController;
import com.ray.common.controller.system.OsController;
import com.ray.common.controller.system.SysController;

public class AdminRoutes extends Routes {

	@Override
	public void config() {
		//针对一组路由配置baseViewPath
		//this.setBaseViewPath("/_view/_admin");
		//针对一组路由配置单独的拦截器
		//this.addInterceptor(new AdminAuthInterceptor());
		//针对后台管理系统配置路由+controller
		//this.add("/admin", AdminIndexController.class,"/index");
		this.setBaseViewPath("/page");
		this.add("/", MainController.class);
		this.add("/sys", SysController.class, "sys");
		this.add("/dl",DingLoginController.class);
		this.add("/os",OsController.class,"sys");
		this.add("/common",CommonController.class);
	}

}