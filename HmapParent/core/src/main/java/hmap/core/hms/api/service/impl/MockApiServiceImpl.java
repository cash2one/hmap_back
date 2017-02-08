/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.hms.service.impl Date:2016/11/2 0002 Create By:zongyun.zhou@hand-china.com
 */
package hmap.core.hms.api.service.impl;

import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hmap.core.hms.api.dto.HeaderAndLineDTO;
import hmap.core.hms.api.service.IApiService;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.DefaultValueProcessor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Service
public class MockApiServiceImpl implements IApiService {
    private final Logger logger = LoggerFactory.getLogger(MockApiServiceImpl.class);

    @Resource(name = "restTemplate")
    RestTemplate restTemplate;

    public String get(String url, HeaderAndLineDTO headerAndLineDTO, JSONObject params) {
        String resultData = "";
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

        HttpHeaders headers = new HttpHeaders();
        if (headerAndLineDTO.getRequestAccept() != null) {
            headers.set("Accept", headerAndLineDTO.getRequestAccept());
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

    @Override
    public JSONObject invoke(HeaderAndLineDTO headerAndLineDTO, JSONObject inbound) {
        String url = headerAndLineDTO.getMockUrl() + headerAndLineDTO.getMockLineUrl();
        logger.info("request url:{}", url);
        String data = null;
        JSONObject json = null;
        data = this.get(url, headerAndLineDTO, inbound);
        data = data.toString().replaceAll(":\\s*null", "\"\"");
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerDefaultValueProcessor(String.class, new DefaultValueProcessor() {
            public Object getDefaultValue(Class type) {
                return JSONNull.getInstance();
            }
        });
        json = JSONObject.fromObject(data, jsonConfig);
        return json;
    }
}
