package com.ray.common.ding;

import java.util.ArrayList;
import java.util.List;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request.Form;
import com.taobao.api.ApiException;
/**
 * 钉钉消息推送工具类
 * @author Ray
 *
 */
public class DingMessage {
	
	/**
	 * 让步接收消息推送
	 * @param userids 接收人
	 * @param fcId 让步接收流程ID
	 * @param supplier 供应商
	 * @param part_no 零件号
	 * @param part_desc 零件名称
	 * @param qty 数量
	 * @throws ApiException
	 * @throws RuntimeException
	 */
	public static void sendFlowConcessive(int fcId,String userids,String supplier,String part_no,String part_desc,String qty) throws ApiException, RuntimeException{
		DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");
		OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
		request.setUseridList(userids);
		request.setAgentId(Env.AGENT_ID);
		request.setToAllUser(false);

		OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
		msg.setMsgtype("text");
		msg.setText(new OapiMessageCorpconversationAsyncsendV2Request.Text());
		msg.getText().setContent("test123");
		request.setMsg(msg);


		msg.setOa(new OapiMessageCorpconversationAsyncsendV2Request.OA());
		msg.getOa().setMessageUrl("eapp://pages/srtc_caDetail/srtc_caDetail?id="+fcId);
		msg.getOa().setHead(new OapiMessageCorpconversationAsyncsendV2Request.Head());
		msg.getOa().getHead().setText("head");
		msg.getOa().setBody(new OapiMessageCorpconversationAsyncsendV2Request.Body());
		msg.getOa().getBody().setContent("有一个新的让步接收流程需要您处理");
		List<Form> form = new ArrayList<>();
		Form form1  = new Form();
		form1.setKey("供应商：");
		form1.setValue(supplier);
		Form form5  = new Form();
		form5.setKey("零件号：");
		form5.setValue(part_no);
		Form form3  = new Form();
		form3.setKey("零件名称：");
		form3.setValue(part_desc);
		Form form6  = new Form();
		form6.setKey("数量：");
		form6.setValue(qty);
		form.add(form1);
		form.add(form5);
		form.add(form3);
		form.add(form6);
		msg.getOa().getBody().setForm(form);
		msg.getOa().getBody().setAuthor("WMS");
		msg.setMsgtype("oa");
		request.setMsg(msg);
		client.execute(request,AccessTokenUtil.getToken());
	}
}
