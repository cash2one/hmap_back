package hmap.core.util;

import hmap.core.beans.TransferDataMapper;
import net.sf.json.JSONObject;

import java.util.Map;

/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved.
 * Project Name:HmapParent
 * Package Name:hmap.core.util
 * Date:2016/8/18
 * Create By:jiguang.sun@hand-china.com
 */


/*
* 映射类，处理项目不同参数格式问题
* */
public class ExampleMapper extends TransferDataMapper {

    @Override
    public String requestDataMap(JSONObject params) {
        String xml = null;
        try {
            xml = JSONAndMap.jsonToXml(params.toString(), null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return xml;
    }

    @Override
    public String responseDataMap(String params) {
        Map<String, Object> map = null;
        try {
            map = JSONAndMap.xml2map(params,null);
        } catch (Exception e) {
            throw new RuntimeException();
        }
        JSONObject jsonObject = new JSONObject();
        if(map!=null || map.size()>0){
            jsonObject = JSONObject.fromObject(map);
        }

        return jsonObject.toString();
    }
}
