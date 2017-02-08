package hmap.core.hms.message.service.impl;

import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.sms.SmsClient;
import com.baidubce.services.sms.SmsClientConfiguration;
import com.baidubce.services.sms.model.SendMessageRequest;
import com.baidubce.services.sms.model.SendMessageResponse;
import hmap.core.hms.message.dto.HmsReceivedMessageDTO;
import hmap.core.hms.message.service.IHmsPush;
import hmap.core.hms.system.service.IHmsSystemConfigService;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


public class HmsSmsImpl implements IHmsPush {
	@Autowired
	private IHmsSystemConfigService systemConfigService;
	private String appKey;
	private String secret;

	private Logger logger = LoggerFactory.getLogger(HmsSmsImpl.class);

	public String getName() {
		return "sms";
	}


	@Override
	public String doPush(HmsReceivedMessageDTO requestDto) {
		String appkey = systemConfigService.getConfigValue("sms.appKey");
		String secret = systemConfigService.getConfigValue("sms.secret");

		SmsClientConfiguration config = new SmsClientConfiguration();
		config.setCredentials(new DefaultBceCredentials(appkey, secret));
		SmsClient client = new SmsClient(config);
		// 1.多用户短信批量下发
		SendMessageRequest request = new SendMessageRequest();
		// 构建SendMessageRequest对象
		request.setTemplateId("smsTpl:"+requestDto.getAttribute1());
		// 设置短信模板ID47​
		request.setContentVar(requestDto.getContent());
		// 设置短信模板内容变量的替换值
		request.setReceiver(JSONArray.toList(JSONArray.fromObject(requestDto.getReceiverList())));
		// 设置接收人列表
		SendMessageResponse sendResponse = client.sendMessage(request);
		if (sendResponse.getMessageId() != null) {
			return sendResponse.getMessageId();
		} else {
			return "error";
		}

	}

}
