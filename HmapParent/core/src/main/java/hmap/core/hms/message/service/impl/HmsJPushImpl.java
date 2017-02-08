package hmap.core.hms.message.service.impl;

import hmap.core.hms.message.dto.HmsReceivedMessageDTO;
import hmap.core.hms.message.service.IHmsPush;
import hmap.core.hms.system.service.IHmsSystemConfigService;
import hmap.core.util.JPushJsonHelper;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@Transactional
public class HmsJPushImpl implements IHmsPush {
	@Autowired
	private IHmsSystemConfigService systemConfigService;
	private String path;
	private String appKey;
	private String secret;
	private Integer waitTime = 5000;
	private String appStoreAppKey;
	private String appStoreSecret;

	private static String key;
	private Logger logger = LoggerFactory.getLogger(HmsJPushImpl.class);

	public String getName() {
		return "jPush";
	}


	@SuppressWarnings("restriction")
	public String doPush(String body, String key) {
		// TODO Auto-generated method stub
		path = systemConfigService.getConfigValue("jpush.path");
		logger.info(body);
		String retMsg;
		try {
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-type", "application/json");
			conn.setRequestProperty("Authorization", "Basic " + key);
			conn.setConnectTimeout(10000);
			conn.setReadTimeout(waitTime);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.connect();

			OutputStream os = conn.getOutputStream();
			os.write(body.getBytes("UTF-8"));
			os.flush();
			os.close();

			InputStream sendStatus = conn.getInputStream();
			int size = sendStatus.available();
			byte[] jsonBytes = new byte[size];
			sendStatus.read(jsonBytes);
			retMsg = new String(jsonBytes, "UTF-8");
			sendStatus.close();
			conn.disconnect();
			JSONObject obj = JSONObject.fromObject(retMsg);
			if (obj.containsKey("error")) {
				return "error:" + obj.getString("error");
			} else {
				return obj.getString("msg_id");
			}
		} catch (Exception e) {
			logger.error("消息推送失败:" + body);
			e.printStackTrace();
			return "503";
		}
	}

	@Override
	public String doPush(HmsReceivedMessageDTO requestDto) {
		appKey = systemConfigService.getConfigValue("jpush.appKey");
		secret = systemConfigService.getConfigValue("jpush.secret");
		appStoreAppKey = systemConfigService.getConfigValue("jpush.appstoreKey");
		appStoreSecret = systemConfigService.getConfigValue("jpush.appstoreSecret");
		String messageId2 =null;

		String body = JPushJsonHelper.formBody(requestDto);
		String rawKey = appKey + ":" + secret;
		key = new sun.misc.BASE64Encoder().encode(rawKey.getBytes());
		String messageId = doPush(body,key);
		if(appStoreAppKey!=null && appStoreSecret!=null) {
			String appStoreRawKey = appStoreAppKey + ":" + appStoreSecret;
			String appStoreKey = new sun.misc.BASE64Encoder()
					.encode(appStoreRawKey.getBytes());
			messageId2 = doPush(body, appStoreKey);
		}
		if((messageId.indexOf("error")!=-1||messageId.equals("503")) && messageId2 !=null){
			return messageId2;
		}else{
			return messageId;
		}
		
		
	}

}
