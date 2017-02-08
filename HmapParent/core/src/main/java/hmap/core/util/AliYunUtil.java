/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved. Project
 * Name:hstaffParent Package Name:hstaff.core.thirdparty.service.impl Date:2016/7/12 0012 Create
 * By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.util;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import hmap.core.hms.system.service.IHmsSystemConfigService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.Map;


public class AliYunUtil  {
  private Logger logger = LoggerFactory.getLogger(AliYunUtil.class);
  static String endpoint = null;
  static String accessId = null;
  static String accessKey = null;
  static String bucket = null;
  static String dir = "img/";
  static String host = null;

  private static void loadConfig(IHmsSystemConfigService systemConfigService) {
    endpoint=systemConfigService.selectByConfigKey("aliyun.endpoint").getConfigValue();
    accessId=systemConfigService.selectByConfigKey("aliyun.accessId").getConfigValue();
    accessKey=systemConfigService.selectByConfigKey("aliyun.accessKey").getConfigValue();
    bucket=systemConfigService.selectByConfigKey("aliyun.bucket").getConfigValue();
    host = "http://" + bucket + "." + endpoint;
    }

  public static boolean delete(IHmsSystemConfigService systemConfigService,String name) {
    loadConfig(systemConfigService);
    OSSClient client = new OSSClient(endpoint, accessId, accessKey);
    JSONObject ja1 = null;
    try {
      client.deleteObject(bucket,name);

    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  public static JSONObject createOssPolicy(IHmsSystemConfigService systemConfigService) {
    loadConfig(systemConfigService);
    OSSClient client = new OSSClient(endpoint, accessId, accessKey);
    JSONObject ja1 = null;
    try {

      long expireTime = 7200000;
      long expireEndTime = System.currentTimeMillis() + expireTime * 1000;//失效时间，不需要多次获取

      Date expiration = new Date(expireEndTime);
      PolicyConditions policyConds = new PolicyConditions();
      policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
      policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

      String postPolicy = client.generatePostPolicy(expiration, policyConds);
      byte[] binaryData = postPolicy.getBytes("utf-8");
      String encodedPolicy = BinaryUtil.toBase64String(binaryData);
      String postSignature = client.calculatePostSignature(postPolicy);

      Map<String, String> respMap = new LinkedHashMap<String, String>();
      respMap.put("accessid", accessId);
      respMap.put("policy", encodedPolicy);
      respMap.put("signature", postSignature);
      respMap.put("dir", dir);
      respMap.put("host", host);
      respMap.put("expire", String.valueOf(expireEndTime / 1000));
      ja1 = JSONObject.fromObject(respMap);
      System.out.println(ja1.toString());

    } catch (Exception e) {
      e.printStackTrace();
    }
    return ja1;
  }
    public OSSClient getOSSClient(){
        // 创建OSSClient实例
        OSSClient client = new OSSClient(endpoint, accessId, accessKey);
        return client;
    }

}
