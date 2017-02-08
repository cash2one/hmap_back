package hmap.core.hms.service.impl;

import hmap.core.hms.system.domain.HmsThirdpartyApp;
import hmap.core.hms.domain.HmsWxToken;
import hmap.core.hms.dto.HmsWxResponseDto;
import hmap.core.hms.system.mapper.HmsThirdpartyAppMapper;
import hmap.core.hms.mapper.HmsWxTokenMapper;
import hmap.core.hms.service.IWxTokenService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

@Service
public class WxTokenServiceImpl implements IWxTokenService {
	@Autowired
	private HmsThirdpartyAppMapper thirdpartyAppMapper;
	@Autowired
	private HmsWxTokenMapper wxTokenMapper;

	private Logger logger = LoggerFactory.getLogger(WxTokenServiceImpl.class);
	private final String uploadMediaUrl = "https://qyapi.weixin.qq.com/cgi-bin/material/add_material?access_token=ACCESS_TOKEN&type=TYPE";
	private final String https_url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=ID&corpsecret=SECRET";
	private final String createStaffUrl = "https://qyapi.weixin.qq.com/cgi-bin/user/create?access_token=ACCESS_TOKEN";
	private final String createDeptUrl = "https://qyapi.weixin.qq.com/cgi-bin/department/create?access_token=ACCESS_TOKEN";
	private final String sendMessage = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=ACCESS_TOKEN";
	private final String deptListUrl = "https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token=ACCESS_TOKEN";
	private final String staffDetailUrl = "https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&userid=USERID";
	private final String deptStaffListUrl = "https://qyapi.weixin.qq.com/cgi-bin/user/simplelist?access_token=ACCESS_TOKEN&department_id=DEPARTMENT_ID&fetch_child=FETCH_CHILD&status=STATUS";
	private final String deptStaffDetailListUrl = "https://qyapi.weixin.qq.com/cgi-bin/user/list?access_token=ACCESS_TOKEN&department_id=DEPARTMENT_ID&fetch_child=FETCH_CHILD&status=STATUS";
	private final String upddateStaff = "https://qyapi.weixin.qq.com/cgi-bin/user/update?access_token=ACCESS_TOKEN";
	private final String deleteStaffUrl = "https://qyapi.weixin.qq.com/cgi-bin/user/delete?access_token=ACCESS_TOKEN&userid=USERID";
	private final String updateDeptUrl = "https://qyapi.weixin.qq.com/cgi-bin/department/update?access_token=ACCESS_TOKEN";
	private final String deleteDeptUrl = "https://qyapi.weixin.qq.com/cgi-bin/department/delete?access_token=ACCESS_TOKEN&id=ID";

	@Override//需要在wxtoken表中加入一行字段
	public String connectForToken() {
        //暂时这样，后续会需要传入参数来获取appKey
        String appKey = thirdpartyAppMapper.selectByCode("wx").getAppKey();
		HmsWxToken wxToken = wxTokenMapper.selectByAppId(appKey);
		String token =null;
		Long newTime = System.currentTimeMillis();
		if(wxToken==null||Long.parseLong(wxToken.getAttribute1())<=newTime){
			JSONObject jo = postForToken();
			if(jo.containsKey("access_token")){
				token = jo.getString("access_token");
				Long expiresTime = Long.parseLong(jo.getString("expires_in"))*1000+newTime;
				if(wxToken==null){
					wxToken = new HmsWxToken();
					wxToken.setId(UUID.randomUUID().toString());
					wxToken.setAppId(appKey);
					wxToken.setAccessToken(token);
					wxToken.setAttribute1(expiresTime.toString());
					wxTokenMapper.insertSelective(wxToken);
				}else if(token.equals(wxToken.getAccessToken())){
					wxToken.setAttribute1(expiresTime.toString());
					wxTokenMapper.updateByPrimaryKeySelective(wxToken);
				}else{
					wxToken.setAccessToken(token);
					wxToken.setAttribute1(expiresTime.toString());
					wxTokenMapper.updateByPrimaryKeySelective(wxToken);
				}
				return token;

			}

		}else{
			return wxToken.getAccessToken();
		}
		return null;
	}

	private JSONObject postForToken(){
		// TODO Auto-generated method stub
		HmsThirdpartyApp hta = thirdpartyAppMapper.selectByCode("wx");
		String id = hta.getAppKey();
		String secret = hta.getAppSecret();
		String token = null;
		String postUrl = https_url.replace("ID", id).replace("SECRET", secret);
		String retMsg = "";
		JSONObject obj = null;
		System.out.println("https_url=" + postUrl);
		HttpURLConnection conn = null;
		InputStream sendStatus = null;
		try {
			URL url = new URL(postUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			// conn.setRequestProperty("Content-type", "application/json");
			conn.setConnectTimeout(10000);
			conn.setReadTimeout(10000);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.connect();
			sendStatus = conn.getInputStream();
			int size = sendStatus.available();
			byte[] jsonBytes = new byte[size];
			sendStatus.read(jsonBytes);
			retMsg = new String(jsonBytes, "UTF-8");
			sendStatus.close();
			obj = JSONObject.fromObject(retMsg);
		} catch (Exception e) {
			logger.error("error:"+e.toString());
			e.printStackTrace();
		} finally {
			try {
				sendStatus.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error("error:"+e.toString());
				e.printStackTrace();
			}
			conn.disconnect();
		}

		return obj;
	}
	// 公用post方法
	public String post(String body, String https_url) {
		// TODO Auto-generated method stub
		HmsWxResponseDto wxResponseDto = new HmsWxResponseDto();
		String retMsg;
		OutputStream os = null;

		InputStream sendStatus = null;
		HttpURLConnection conn = null;
		try {
			URL url = new URL(https_url);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-type", "application/json");
			conn.setConnectTimeout(10000);
			conn.setReadTimeout(5000);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.connect();

			os = conn.getOutputStream();
			os.write(body.getBytes("UTF-8"));
			os.flush();

			sendStatus = conn.getInputStream();
			int size = sendStatus.available();
			byte[] jsonBytes = new byte[size];
			sendStatus.read(jsonBytes);
			retMsg = new String(jsonBytes, "UTF-8");

			JSONObject obj = JSONObject.fromObject(retMsg);
			wxResponseDto.setErrcode(obj.getString("errcode"));
			wxResponseDto.setErrmsg(obj.getString("errmsg"));
		} catch (Exception e) {
			logger.error("推送失败:" + body);
			e.printStackTrace();
			return null;
		} finally {
			try {
				sendStatus.close();
				os.close();
				conn.disconnect();
			} catch (IOException e) {
				logger.error("推送失败:" + body);
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return retMsg;
	}

	public String get(String https_url) {
		String inMsg = null;
		URL url;
		try {
			url = new URL(https_url);
			HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
			//add by hxm for 中文乱码
			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream(), "utf-8"));
			StringBuffer sb = new StringBuffer();

			String oneLine = new String("");
			while ((oneLine = br.readLine()) != null) {
				sb.append(oneLine);
			}
			inMsg = new String(sb.toString());
			con.disconnect();
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return inMsg;
	}


}
