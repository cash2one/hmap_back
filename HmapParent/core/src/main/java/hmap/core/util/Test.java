package hmap.core.util;

import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.sms.SmsClient;
import com.baidubce.services.sms.SmsClientConfiguration;
import com.baidubce.services.sms.model.CreateTemplateRequest;
import com.baidubce.services.sms.model.SendMessageRequest;
import com.baidubce.services.sms.model.SendMessageResponse;
import net.sf.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by shanhd on 2016/11/24.
 */
public class Test {
    public static boolean sendMsg(String phoneNo, String smsTemplateCode,   String smsParam) {
        String appkey = "d77c33cb897f43f8b47907b8a9a77e33";
        String secret = "dc66fe6cfb814e089c4117d3cf8dd91f";
        SmsClientConfiguration config = new SmsClientConfiguration();
        config.setCredentials(new DefaultBceCredentials(appkey, secret));
        SmsClient client = new SmsClient(config);

        // 1.多用户短信批量下发
        SendMessageRequest request = new SendMessageRequest();
        // 构建SendMessageRequest对象
        request.setTemplateId(smsTemplateCode);
        // 设置短信模板ID47​
        request.setContentVar(smsParam);
        // 设置短信模板内容变量的替换值
        request.setReceiver(JSONArray.toList(JSONArray.fromObject(phoneNo)));
        // 设置接收人列表
        SendMessageResponse sendResponse = client.sendMessage(request);
        // 请求Server51​
        System.out.println("========: "+sendResponse.getMessageId());
        if (sendResponse.getMessageId() != null) {      return true;    }    return false;  }

    public static void main(String[] args) {
        String msgBody = "{\"code\":\"" + 123456 + "\"}";
        String telephone = "['15084917836','18374989918']";
        boolean result =sendMsg(telephone, "smsTpl:862c2d90-a6ec-4855-800a-f72ae272838c", msgBody);

    }
}
