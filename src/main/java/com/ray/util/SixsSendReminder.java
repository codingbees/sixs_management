package com.ray.util;

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
import com.taobao.api.ApiException;

import java.util.Date;
import java.util.List;

public class SixsSendReminder implements Runnable {

    public void run() {
        System.out.println("Current system time: " + new Date());
        System.out.println("Another minute ticked away...");
    }
    public void findUsers(){
        String sql = "SELECT DATE_FORMAT(NOW(),'%Y-%m-%d') `today` ,s.*\n" +
                "FROM s6s_total s\n" +
                "WHERE DATEDIFF( first_req_date,DATE_FORMAT(NOW(),'%Y-%m-%d')) <=3\n" +
                "AND DATE_FORMAT(NOW(),'%Y-%m-%d')<=s.first_req_date";
        List<Record> list = Db.find(sql);
    }

    public void sendMessage() throws ApiException {



        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");
        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
        request.setAgentId(Long.valueOf(PropKit.get("AGENT_ID")));
        request.setUseridList("0918574611672077");
        request.setToAllUser(false);

        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setMsgtype("text");
        msg.setText(new OapiMessageCorpconversationAsyncsendV2Request.Text());
        msg.getText().setContent("您有待处理的工作");
        request.setMsg(msg);

        String accessToken = AccessTokenUtil.getToken();
        System.out.println("accessToken");
        System.out.println(accessToken);
		OapiMessageCorpconversationAsyncsendV2Response rsp = client.execute(request, accessToken);
		System.out.println(rsp.getBody());

        String my = "18188380424";
        String userid = getUserIdByPhone(my);

        Record record = new Record();
//		record.set("rspBody",rsp.getBody());
        record.set("userid",userid);

    }

    //根据手机号查询userid
    @NotAction
    public static String getUserIdByPhone(String mobile){
        Record record = new Record();
        try {
            String accessToken = AccessTokenUtil.getToken();
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/user/getbymobile");
            OapiV2UserGetbymobileRequest req = new OapiV2UserGetbymobileRequest();
            req.setMobile(mobile);
            OapiV2UserGetbymobileResponse rsp = client.execute(req, accessToken);
            System.out.println("getBody");
            System.out.println(rsp.getBody());
            record.set("resp",rsp.getBody());
        } catch (ApiException e) {
            e.printStackTrace();
            record.set("resp",e);
        }
        return record.toString();
    }
}
