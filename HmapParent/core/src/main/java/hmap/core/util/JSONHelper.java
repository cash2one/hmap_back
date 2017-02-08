/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved. Project
 * Name:hstaffParent Package Name:hstaff.core.util Date:2016/7/8 0008 Create
 * By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.util;

import hmap.core.hms.dto.WxWorkflowDetailDTO;
import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.DefaultValueProcessor;
import net.sf.json.processors.JsDateJsonValueProcessor;
import net.sf.json.processors.JsonValueProcessor;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;

public class JSONHelper {
  public static JSONObject getErrorJSONObject(String errorMsg) {
    JSONObject json = new JSONObject();
    json.put("status", "E");
    json.put("errorMsg", errorMsg == null ? "" : errorMsg);
    return json;
  }

  public static JSONObject getJSONObject(Object obj) {
    String pattern = "yyyy-MM-dd HH:mm:ss";
    final SimpleDateFormat fm = new SimpleDateFormat(pattern);

    JsonConfig jsonConfig = new JsonConfig();
    jsonConfig.registerDefaultValueProcessor(String.class, new DefaultValueProcessor() {
      public Object getDefaultValue(Class type) {
        return JSONNull.getInstance();
      }
    });
    jsonConfig.registerDefaultValueProcessor(Integer.class, new DefaultValueProcessor() {
      public Object getDefaultValue(Class type) {
        return JSONNull.getInstance();
      }
    });

    jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonValueProcessor() {
      @Override
      public Object processObjectValue(String key, Object value, JsonConfig cfg) {
        if (value == null) {
          return "";
        } else {
          return fm.format((Date) value);
        }
      }

      @Override
      public Object processArrayValue(Object date, JsonConfig arg1) {
        return fm.format((Date) date);
      }
    });
    JSONObject json = JSONObject.fromObject(obj, jsonConfig);
    return json;
  }

  public static JSONArray getSuccessJSONArray(List list) {
    JsonConfig jsonConfig = new JsonConfig();
    jsonConfig.registerDefaultValueProcessor(String.class, new DefaultValueProcessor() {
      public Object getDefaultValue(Class type) {
        return JSONNull.getInstance();
      }
    });
    jsonConfig.registerDefaultValueProcessor(Integer.class, new DefaultValueProcessor() {
      public Object getDefaultValue(Class type) {
        return JSONNull.getInstance();
      }
    });
    jsonConfig.registerDefaultValueProcessor(Date.class, new DefaultValueProcessor() {
      public Object getDefaultValue(Class type) {
        return JSONNull.getInstance();
      }
    });
    JSONArray json = JSONArray.fromObject(list, jsonConfig);
    return json;
  }

  public static JSONObject getEmptyJSONObject() {
    JSONObject json = new JSONObject();
    json.put("con_status", "S");
    json.put("status", "S");
    return json;
  }


}
