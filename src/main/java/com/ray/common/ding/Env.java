package com.ray.common.ding;

import com.jfinal.kit.PropKit;

/**
 * 企业应用接入时的常量定义
 */
public class Env {
    
    public static final Long AGENT_ID = Long.valueOf(PropKit.get("AGENT_ID"));
    
    public static final String APP_KEY = PropKit.get("APP_KEY");
    /**
     	* 开发者后台->企业自建应用->选择您创建的E应用->查看->AppSecret
     */
    public static final String APP_SECRET = PropKit.get("APP_SECRET");
    /**
     	* 企业corpid
     */
    public static final String CORP_ID = PropKit.get("CORP_ID");

    /**
     * DING API地址
     */
	public static final String OAPI_HOST = "https://oapi.dingtalk.com";
	/**
     * 钉钉网关gettoken地址
     */
    public static final String URL_GET_TOKKEN = "https://oapi.dingtalk.com/gettoken";

    /**
     *获取用户在企业内userId的接口URL
     */
    public static final String URL_GET_USER_INFO = "https://oapi.dingtalk.com/user/getuserinfo";

    /**
     *获取用户姓名的接口url
     */
    public static final String URL_USER_GET = "https://oapi.dingtalk.com/user/get";
	
}
