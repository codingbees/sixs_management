package com.ray.common.config;


import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.template.Engine;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
import com.jfinal.ext.plugin.shiro.ShiroInterceptor;
import com.jfinal.ext.plugin.shiro.ShiroPlugin;
import com.jfinal.server.undertow.UndertowServer;
import com.jfinal.plugin.druid.DruidPlugin;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.render.ViewType;
import com.ray.common.interceptor.MetaObjectIntercept;
import com.ray.common.model._MappingKit;
import com.ray.common.quartz.QuartzPlugin;
import com.ray.common.shiro.CustomAuth;
import com.ray.common.ding.DingController;
import com.ray.util.Commen;
import com.ray.util.Cron4jPlugin;


public class MainConfig extends JFinalConfig {
	/**
	 * 将全局配置提出来 方便其他地方重用
	 */
	private static Prop p;
	private WallFilter wallFilter;
	private Routes routes;
	private static MetaObjectIntercept defaultMetaObjectIntercept = null; 
	/**
	 * 配置JFinal常量
	 */
	@Override
	public void configConstant(Constants me) {
		//读取数据库配置文件
		loadConfig();
		//设置当前是否为开发模式
		me.setDevMode(p.getBoolean("devMode"));
		//设置默认上传文件保存路径 getFile等使用
		me.setBaseUploadPath(p.get("domin_path")+"/temp");
		//设置上传最大限制尺寸
		//me.setMaxPostSize(1024*1024*10);
		//设置默认下载文件路径 renderFile使用
		me.setBaseDownloadPath(p.get("domin_path"));
		//设置默认视图类型
		me.setViewType(ViewType.JFINAL_TEMPLATE);
		//设置404渲染视图
		//me.setError404View("404.html");
		me.setJsonDatePattern("yyyy-MM-dd");
		//设置启用依赖注入
		me.setInjectDependency(true);
		   
		
	}
	/**
	 * 配置项目路由
	 * 路由拆分到 FrontRutes 与 AdminRoutes 之中配置的好处：
	 * 1：可分别配置不同的 baseViewPath 与 Interceptor
	 * 2：避免多人协同开发时，频繁修改此文件带来的版本冲突
	 * 3：避免本文件中内容过多，拆分后可读性增强
	 * 4：便于分模块管理路由
	 */
	@Override
	public void configRoute(Routes me) {
		me.add(new AdminRoutes());//配置后台管理系统路由
		me.add(new PCRoutes());//配置PC端业务路由
		me.add(new DDRoutes());//配置钉钉E应用端业务路由
		me.add(new BaseRoutes());//配置基础模板路由
		this.routes = me;
	}
	// 先加载开发环境配置，再追加生产环境的少量配置覆盖掉开发环境配置
	static void loadConfig() {
		if (p == null) {
			p = PropKit.use("config-dev.properties").appendIfExists("config-pro.properties");
		}
	}
	/**
	 * 获取数据库插件
	 * 抽取成独立的方法，便于重用该方法，减少代码冗余
	 */
	public static DruidPlugin getDruidPlugin() {
		loadConfig();
		return new DruidPlugin(p.get("jdbcUrl"), p.get("user"), p.get("password"));
	}
	
	public static DruidPlugin getSYSDruidPlugin() {
		loadConfig();
		return new DruidPlugin(p.get("sys_jdbcUrl"), p.get("user"), p.get("password"));
	}
	/**
	 * 配置JFinal插件
	 * 数据库连接池
	 * ActiveRecordPlugin
	 * 缓存
	 * 定时任务
	 * 自定义插件
	 */
	@Override
	public void configPlugin(Plugins me) {
		loadConfig();
		//配置数据库连接池插件
		DruidPlugin dbPlugin=getDruidPlugin();
		wallFilter = new WallFilter();			// 加强数据库安全
		wallFilter.setDbType("mysql");
		dbPlugin.addFilter(wallFilter);
		dbPlugin.addFilter(new StatFilter());	// 添加 StatFilter 才会有统计数据
		//数据映射 配置ActiveRecord插件
		ActiveRecordPlugin arp=new ActiveRecordPlugin(dbPlugin);
		arp.setShowSql(p.getBoolean("devMode"));
		arp.setDialect(new MysqlDialect());
		dbPlugin.setDriverClass("com.mysql.jdbc.Driver");
		
		/********在此添加数据库 表-Model 映射*********/
		//如果使用了JFinal Model 生成器 生成了BaseModel 把下面注释解开即可
		_MappingKit.mapping(arp);
		
		//任务调度
		me.add(new Cron4jPlugin());
//		Cron4jPlugin cp = new Cron4jPlugin(PropKit.use("cron4j.properties"));
//		me.add(cp);
		//shiro
		ShiroPlugin shiroPlugin = new ShiroPlugin(this.routes);
		shiroPlugin.setLoginUrl("/loginInit");
		shiroPlugin.setSuccessUrl("/index");
		shiroPlugin.setUnauthorizedUrl("/error401");
		me.add(shiroPlugin);
		
		//添加到插件列表中
		me.add(dbPlugin);
		me.add(arp);
		
		
		DruidPlugin sysdbPlugin=getSYSDruidPlugin();
		sysdbPlugin.addFilter(wallFilter);
		sysdbPlugin.addFilter(new StatFilter());	// 添加 StatFilter 才会有统计数据
		//数据映射 配置ActiveRecord插件
		ActiveRecordPlugin sysarp=new ActiveRecordPlugin("sys",sysdbPlugin);
		sysarp.setShowSql(p.getBoolean("devMode"));
		sysarp.setDialect(new MysqlDialect());
		sysdbPlugin.setDriverClass("com.mysql.jdbc.Driver");
		me.add(sysdbPlugin);
		me.add(sysarp);
		//定时任务
		QuartzPlugin quartz = new QuartzPlugin();
		me.add(quartz);
		
		//event事件驱动
//		EventPlugin event = new EventPlugin();
//		event.async();
//		event.enableClassScan();
//		me.add(event);
	}
	/**
	 * 配置全局拦截器
	 */
	@Override
	public void configInterceptor(Interceptors me) {
		me.addGlobalActionInterceptor(new ShiroInterceptor());
		me.addGlobalActionInterceptor(new SessionInViewInterceptor());
		setDefaultMetaObjectIntercept(new com.ray.common.interceptor.BaseMetaObjectIntercept());
	}
	/**
	 * 配置全局处理器
	 */
	@Override
	public void configHandler(Handlers me) {
		//说明：druid的统计页面涉及安全性 需要自行处理根据登录权限判断是否能访问统计页面 
		//me.add(DruidKit.getDruidStatViewHandler()); // druid 统计页面功能
	}
	/**
	 * JFinal启动后调用
	 */
	@Override
	public void afterJFinalStart() {
		// 让 druid 允许在 sql 中使用 union
		// https://github.com/alibaba/druid/wiki/%E9%85%8D%E7%BD%AE-wallfilter
		//wallFilter.getConfig().setSelectUnionCheck(false);
	}
	
	/**
	 * 配置模板引擎 
	 */
	@Override
	public void configEngine(Engine me) {
		//配置模板支持热加载
		me.setDevMode(p.getBoolean("engineDevMode", false));
		Record system = new Record();
		system.set("company", PropKit.get("company"));
		me.addSharedMethod(CustomAuth.class);
		me.addSharedMethod(DingController.class);
		me.addSharedObject("system", system);
		me.addSharedObject("domin_url", PropKit.get("domin_url"));
		me.addSharedObject("system_url", PropKit.get("system_url"));
		me.addSharedMethod(new Commen());
		//这里只有选择JFinal TPL的时候才用
		//配置共享函数模板
		//me.addSharedFunction("/view/common/layout.html")
	}
	
	public static void setDefaultMetaObjectIntercept(MetaObjectIntercept defaultMetaObjectIntercept) {
		MainConfig.defaultMetaObjectIntercept = defaultMetaObjectIntercept;
	}
	
	public static MetaObjectIntercept getDefaultMetaObjectIntercept() {
		return defaultMetaObjectIntercept;
	}
	
	public static void main(String[] args) {
		UndertowServer server = UndertowServer.create(MainConfig.class,"undertow.properties").configWeb( builder -> {
	         // 配置 Filter
			builder.addListener("org.apache.shiro.web.env.EnvironmentLoaderListener");
			builder.addFilter("kickout", "com.ray.common.shiro.KickoutSessionControlFilter");
	         builder.addFilter("Shiro", "org.apache.shiro.web.servlet.ShiroFilter");
	         builder.addFilterUrlMapping("Shiro", "/*");
	         builder.addFilterUrlMapping("kickout", "/kickout");
	      }).addHotSwapClassPrefix("com.github.jieblog.");
		
		server.start();
	}
	

}