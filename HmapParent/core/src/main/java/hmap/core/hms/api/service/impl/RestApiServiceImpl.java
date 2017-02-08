package hmap.core.hms.api.service.impl;

import com.github.pagehelper.StringUtil;
import hmap.core.beans.TransferDataMapper;
import hmap.core.hms.api.domain.HmsOauthToken;
import hmap.core.hms.system.domain.HmsThirdpartyApp;
import hmap.core.hms.api.dto.HeaderAndLineDTO;
import hmap.core.hms.api.service.IApiService;
import hmap.core.hms.api.service.IHmsOauthTokenService;
import hmap.core.hms.system.service.IHmsThirdpartyAppService;
import hmap.core.security.SecurityUtils;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.DefaultValueProcessor;
import net.sf.json.processors.JsonValueProcessor;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Created by user on 2016/7/28.
 * update on 2017/1/3
 */
@Service
public class RestApiServiceImpl implements IApiService {

    private final Logger logger = LoggerFactory.getLogger(RestApiServiceImpl.class);
    @Autowired
    IHmsOauthTokenService hmsOauthTokenService;
    @Autowired
    IHmsThirdpartyAppService hmsThirdpartyAppService;
    @Resource(name = "restTemplate")
    RestTemplate restTemplate;


    HttpHeaders setHeaders(HeaderAndLineDTO headerAndLineDTO) {
        HttpHeaders httpHeaders = new HttpHeaders();

        if (headerAndLineDTO.getRequestContenttype() != null) {
            httpHeaders.set("Content-Type", headerAndLineDTO.getRequestContenttype());
        } else {
            httpHeaders.set("Content-Type", "application/json");
        }

        if (headerAndLineDTO.getRequestAccept() != null) {
            httpHeaders.set("Accept", headerAndLineDTO.getRequestAccept());
        }

        String basicBase64;
        if (headerAndLineDTO.getAuthType().equals("BASIC_AUTH")) {
            String e1 = headerAndLineDTO.getAuthUsername() + ":" + headerAndLineDTO.getAuthPassword();
            basicBase64 = new String(Base64.encodeBase64(e1.getBytes()));
            httpHeaders.set("Authorization", "Basic " + basicBase64);
        }

        return httpHeaders;
    }


    public String get(String url, HeaderAndLineDTO headerAndLineDTO, JSONObject params) {
        String resultData = "";
        // TODO get请求把参数拼在url后面，根据自己需求更改
        if (params != null && params.size() > 0) {
            Iterator iterator = params.keys();
            url += "?";
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                url += key + "=" + params.get(key) + "&";
            }
            url = url.substring(0, url.length() - 1);
        }

        logger.info("request url:{}", url);

        HttpHeaders headers = this.setHeaders(headerAndLineDTO);


        // 如果验证类型是OAUTH
        if (headerAndLineDTO.getAuthType().equals("OAUTH2")) {
            // 然后在调用api的时候在最后追加access_token
            if (headerAndLineDTO.getGrantType().equals("CLIENT_CREDENTIALS")) {
                Map<String, String> oauthConfig = new HashMap<String, String>();
                //TODO 要从第三方应用中查询
                HmsThirdpartyApp thirdpartyApp = hmsThirdpartyAppService.selectById(headerAndLineDTO.getThirdpartyId());
                oauthConfig.put("clientId", thirdpartyApp.getAppKey());
                oauthConfig.put("clientSecret", thirdpartyApp.getAppSecret());
                oauthConfig.put("grantType", headerAndLineDTO.getGrantType());
                oauthConfig.put("authenticationServerUrl",
                        headerAndLineDTO.getDomainUrl().concat(headerAndLineDTO.getAccessTokenUrl()));
                HmsOauthToken hmsOauthToken = hmsOauthTokenService.getAccessToken(oauthConfig,"N");
                logger.info("使用了oauth2验证，获得token为:{}", hmsOauthToken.getAccessToken());
                headers.set("ACCESS_TOKEN", hmsOauthToken.getAccessToken());
            }
        }

        HttpEntity httpEntity = new HttpEntity(headers);
        try {
            resultData = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class).getBody();
        } catch (RestClientException e) {
            logger.info("connect failed：{}", e.getMessage());
            e.printStackTrace();
        }

        logger.info("responseData:{}", resultData);
        return resultData;
    }

    public String post(String url, HeaderAndLineDTO headerAndLineDTO, String params) {
        String resultData = "";


        HttpHeaders headers = this.setHeaders(headerAndLineDTO);
        // 如果验证类型是OAUTH
        if (headerAndLineDTO.getAuthType().equals("OAUTH2")) {
            // 然后在调用api的时候在最后追加access_token

            Map<String, String> oauthConfig = new HashMap<String, String>();
            //TODO 要从第三方应用中查询
            HmsThirdpartyApp thirdpartyApp = hmsThirdpartyAppService.selectById(headerAndLineDTO.getThirdpartyId());
            oauthConfig.put("clientId", thirdpartyApp.getAppKey());
            oauthConfig.put("clientSecret", thirdpartyApp.getAppSecret());
            oauthConfig.put("grantType", headerAndLineDTO.getGrantType());
            oauthConfig.put("userName", SecurityUtils.getCurrentUserLogin());
            oauthConfig.put("grantType", headerAndLineDTO.getGrantType().toLowerCase());
            oauthConfig.put("authenticationServerUrl",
                    headerAndLineDTO.getDomainUrl().concat(headerAndLineDTO.getAccessTokenUrl()));
            HmsOauthToken hmsOauthToken = hmsOauthTokenService.getAccessToken(oauthConfig,"N");
            logger.info("使用了oauth2验证，获得token为:{}", hmsOauthToken.getAccessToken());
//          connection.setRequestProperty("access_token", hmsOauthToken.getAccessToken());
            headers.set("Authorization", "Bearer " + hmsOauthToken.getAccessToken());

        }


        HttpEntity httpEntity = new HttpEntity(params, headers);
        try {
            resultData = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class).getBody();
        } catch (RestClientException e) {
            logger.info("connect failed：{}", e.getMessage());
            e.printStackTrace();
        }

        logger.info("responseData:{}", resultData);
        return resultData;

    }

    @Override
    public JSONObject invoke(HeaderAndLineDTO headerAndLineDTO, JSONObject inbound) {
        String url = headerAndLineDTO.getDomainUrl() + headerAndLineDTO.getIftUrl();
        logger.info("request url:{}", url);
        String data = null;
        JSONObject json = null;
        String inboundParam = "";

        if (headerAndLineDTO.getRequestMethod().equals("POST")) {
            inboundParam = inbound.toString();
            // 如果用户定义了包装类，那么就需要将inbound进行包装，本类一般都是客户化开发
            // 根据需要来做，使用时需要进行动态加载 by zongyun.zhou@hand-china.com
            if (StringUtil.isNotEmpty(headerAndLineDTO.getMapperClass())) {
                ClassLoader cl = Thread.currentThread().getContextClassLoader();
                TransferDataMapper mapper = null;
                try {
                    Class c = cl.loadClass(headerAndLineDTO.getMapperClass());
                    mapper = (TransferDataMapper) c.newInstance();
                    inboundParam = mapper.requestDataMap(inbound);
                } catch (ClassNotFoundException e) {
                    logger.error("ClassNotFoundException:" + e.getMessage());
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    logger.error("InstantiationException:" + e.getMessage());
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    logger.error("IllegalAccessException:" + e.getMessage());
                    e.printStackTrace();
                }
                logger.info("params Xml :{}", inboundParam.toString());

                data = this.post(url, headerAndLineDTO, inboundParam);
                data = mapper.responseDataMap(data);

            } else {
                data = this.post(url, headerAndLineDTO, inboundParam);
            }

        } else if (headerAndLineDTO.getRequestMethod().equals("GET")) {
            data = this.get(url, headerAndLineDTO, inbound);
        }

        if (data != null && !"".equals(data)) {

            data = data.toString().replaceAll(":\\s*null", ":\"\"");

            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.registerDefaultValueProcessor(String.class, new DefaultValueProcessor() {
                public Object getDefaultValue(Class type) {
                    return JSONNull.getInstance();
                }
            });
            jsonConfig.registerJsonValueProcessor(Integer.class, new JsonValueProcessor() {
                @Override public Object processArrayValue(Object o, JsonConfig jsonConfig) {
                    return null;
                }
                @Override
                public Object processObjectValue(String s, Object o, JsonConfig jsonConfig) {
                    return ((Integer)o).toString();
                }
            });
            jsonConfig.registerJsonValueProcessor(Long.class, new JsonValueProcessor() {
                @Override public Object processArrayValue(Object o, JsonConfig jsonConfig) {
                    return null;
                }
                @Override
                public Object processObjectValue(String s, Object o, JsonConfig jsonConfig) {
                    return ((Long)o).toString();
                }
            });
            jsonConfig.registerJsonValueProcessor(BigDecimal.class, new JsonValueProcessor() {
                @Override public Object processArrayValue(Object o, JsonConfig jsonConfig) {
                    return null;
                }
                @Override
                public Object processObjectValue(String s, Object o, JsonConfig jsonConfig) {
                    return ((BigDecimal)o).toString();
                }
            });
            json = JSONObject.fromObject(data, jsonConfig);

        }

        return json;
    }
}
