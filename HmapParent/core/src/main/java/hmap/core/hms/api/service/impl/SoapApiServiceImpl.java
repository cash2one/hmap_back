package hmap.core.hms.api.service.impl;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

import hmap.core.util.JSONAndMap;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import hmap.core.hms.api.dto.HeaderAndLineDTO;
import hmap.core.hms.api.service.IApiService;
import net.sf.json.JSONObject;

/**
 * Created by user on 2016/7/29.
 */
@Service
public class SoapApiServiceImpl implements IApiService {

    private static final Logger logger = LoggerFactory.getLogger(SoapApiServiceImpl.class);

    public Map soapSend(HeaderAndLineDTO headerAndLineDTO, String xml) {

        //测试使用
//        xml = getSoapRequest("上海");
//        url = "http://www.webxml.com.cn/WebServices/WeatherWebService.asmx";

        StringBuffer data = new StringBuffer();
        try {
            InputStream is = getSoapInputStream(xml, headerAndLineDTO);//得到输入流
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                data.append(line);
            }
            bufferedReader.close();
            is.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Object> map = null;
        try {
            map = JSONAndMap.xml2map(data.toString(),headerAndLineDTO);
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return map;
    }


    /**
     * 仅测试soap使用
     * 获取soap请求头，并替换其中的标志符号为用户的输入符号
     *
     * @param city 用户输入城市名
     * @return 用户将要发送给服务器的soap请求
     */
    private static String getSoapRequest(String city) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
                + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" "
                + "xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>" +
                " <getWeatherbyCityName xmlns=\"http://WebXml.com.cn/\">"
                + "<theCityName>" + city
                + "</theCityName>  " +
                "  </getWeatherbyCityName>"
                + "</soap:Body></soap:Envelope>");
        return sb.toString();
    }

    /**
     * 用户把SOAP请求发送给服务器端，并返回服务器点返回的输入流
     *
     * @param xml 把最终的xml传入
     * @return 服务器端返回的输入流，供客户端读取
     * @throws Exception
     */
    public static InputStream getSoapInputStream(String xml, HeaderAndLineDTO headerAndLineDTO) throws Exception {
        try {

            String requestUrl = headerAndLineDTO.getDomainUrl() + headerAndLineDTO.getIftUrl();
            URL url = new URL(requestUrl);
            URLConnection conn = url.openConnection();
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Length", Integer.toString(xml.length()));
            conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");

            String basicBase64;
            if (headerAndLineDTO.getAuthType().equals("BASIC_AUTH")) {
                String e1 = headerAndLineDTO.getAuthUsername() + ":" + headerAndLineDTO.getAuthPassword();
                basicBase64 = new String(Base64.encodeBase64(e1.getBytes()));
                conn.setRequestProperty("Authorization", "Basic " + basicBase64);
            }
//            http://WebXml.com.cn/getWeatherbyCityName
//            http://WebXml.com.cn/qqCheckOnline
//            conn.setRequestProperty("SOAPAction", "http://WebXml.com.cn/getWeatherbyCityName");
            conn.connect();
            OutputStream os = conn.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os, "utf-8");
            osw.write(xml);
            osw.flush();
            osw.close();
            InputStream is = conn.getInputStream();
            return is;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public JSONObject invoke(HeaderAndLineDTO headerAndLineDTO, JSONObject inbound) {

        logger.info("inbound:{}", inbound);
        String xml = null;
        try {
            xml = JSONAndMap.jsonToXml(inbound.toString(), headerAndLineDTO.getNamespace());
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("jsonToMap error:{}", e);
        }
        xml = headerAndLineDTO.getBodyHeader() + xml + headerAndLineDTO.getBodyTail();
        //测试
//        xml = headerAndLineDTO.getBodyHeader() + "<ser:" + headerAndLineDTO.getLineCode() + "><param>" + xml + "</param></ser:" + headerAndLineDTO.getLineCode() + ">" + headerAndLineDTO.getBodyTail();
        logger.info("xml:"+xml);
        Map result = this.soapSend(headerAndLineDTO, xml);

        JSONObject jsonObject = JSONObject.fromObject(result);

        return jsonObject;
    }

}
