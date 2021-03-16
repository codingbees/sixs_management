package com.ray.job;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.request.OapiV2UserGetbymobileRequest;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.dingtalk.api.response.OapiV2UserGetbymobileResponse;
import com.jfinal.core.NotAction;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.ray.common.ding.AccessTokenUtil;
import com.ray.common.quartz.AbsJob;
import com.taobao.api.ApiException;

import java.util.List;

public class EveryMinJob extends AbsJob{
	
	protected void process(org.quartz.JobExecutionContext context) {

		System.out.println("每分任务已启动");

	}

}
