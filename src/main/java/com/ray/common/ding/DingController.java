package com.ray.common.ding;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiGetJsapiTicketRequest;
import com.dingtalk.api.response.OapiGetJsapiTicketResponse;
import com.jfinal.plugin.activerecord.Record;
import com.ray.common.controller.BaseController;
import com.taobao.api.ApiException;

public class DingController extends BaseController{
	
	public static String getJsapiTicket(String accessToken){
		DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/get_jsapi_ticket");
        OapiGetJsapiTicketRequest req = new OapiGetJsapiTicketRequest();
        req.setTopHttpMethod("GET");
        try {
			OapiGetJsapiTicketResponse execute = client.execute(req, accessToken);
			return execute.getTicket();
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static String sign(String ticket, String nonceStr, long timeStamp, String url) throws OApiException {
		String plain = "jsapi_ticket=" + ticket + "&noncestr=" + nonceStr + "&timestamp=" + String.valueOf(timeStamp)
				+ "&url=" + url;
		try {
			MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
			sha1.reset();
			sha1.update(plain.getBytes("UTF-8"));
			return sha1.digest().toString();
		} catch (NoSuchAlgorithmException e) {
			throw new OApiException(0, e.getMessage());
		} catch (UnsupportedEncodingException e) {
			throw new OApiException(0, e.getMessage());
		}
	}
	
	public static String getConfig(HttpServletRequest request) {
        String urlString =request.getRequestURL().toString();
        String nonceStr = "abcdefgqweqweqwe";
        long timeStamp = System.currentTimeMillis() / 1000;
        String signedUrl = urlString;
        String accessToken = null;
        String ticket = null;
        String signature = null;
        String agentid = null;

        try {
            accessToken = AccessTokenUtil.getToken();

            ticket = getJsapiTicket(accessToken);
            signature = sign(ticket, nonceStr, timeStamp, signedUrl);
            agentid = String.valueOf(Env.AGENT_ID);

        } catch (OApiException e) {
            e.printStackTrace();
        }
        String configValue = "{jsticket:'" + ticket + "',signature:'" + signature + "',nonceStr:'" + nonceStr + "',timeStamp:'"
                + timeStamp + "',corpId:'" + Env.CORP_ID + "',agentid:'" + agentid + "'}";
        Record resp = new Record();
        resp.set("code", 200);
        resp.set("config", configValue);
        return configValue;
    }
}
