/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.hms.service.impl Date:2016/11/30 0030 Create
 * By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.system.service.impl;

import hmap.core.hms.system.service.ILogService;
import hmap.core.security.SecurityUtils;
import hmap.core.util.JSONHelper;
import hmap.core.util.StatelessRequestHelper;
import net.logstash.logback.marker.LogstashMarker;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import static net.logstash.logback.marker.Markers.*;
@Service
public class LogServiceImpl implements ILogService {
  private static final Logger logger = LoggerFactory.getLogger(LogServiceImpl.class);
  private final Logger audit = LoggerFactory.getLogger("auditLog");
  private final Logger ctrl = LoggerFactory.getLogger("ctrlLog");
  private final Logger service = LoggerFactory.getLogger("serviceLog");
  private final Logger job = LoggerFactory.getLogger("jobLog");
  private final Logger consumer = LoggerFactory.getLogger("consumerLog");
  private final String SEPARATE = ",";
  @Value("${app.name}")
  private String appName;
  private String serverIp = null;
  private String userName = null;
  private String clientId = null;
  private StringBuffer logSb = null;
  private JSONObject logJson=null;
  String pattern = "yyyy-MM-dd HH:mm:ss.SSS";
  final SimpleDateFormat fm = new SimpleDateFormat(pattern);

  @PostConstruct
  public void init() {
    serverIp = StatelessRequestHelper.getIp();
  }

  private void preLog() {
    logSb = new StringBuffer();
    userName = SecurityUtils.getCurrentUserLogin();
    clientId = SecurityUtils.getCurrentClient();
    logSb.append("appName:").append(appName).append(",serverIp:").append(serverIp)
        .append(",userName:").append(userName).append(",clientId:").append(clientId);
  }
  private void preLog(String logLevel) {
    logJson=new JSONObject();
    userName = SecurityUtils.getCurrentUserLogin();
    clientId = SecurityUtils.getCurrentClient();
    logJson.put("logLevel",logLevel);
    logJson.put("time",fm.format(new Date()));
    logJson.put("appName",appName);
    logJson.put("serverIp",serverIp);
    logJson.put("userName",userName);
    logJson.put("clientId",clientId);
   }
  private void preLog(LogstashMarker lm) {
    userName = SecurityUtils.getCurrentUserLogin();
    clientId = SecurityUtils.getCurrentClient();
    lm=lm.and(append("userName",userName)).and(append("clientId",clientId));
  }
  private void clean() {
    logSb = null;
  }

  @Override public void auditLogInfo(String s) {
    preLog();
    audit.info(logSb.toString().concat(SEPARATE).concat(s));
    clean();
  }
  public void auditLogInfo(LogstashMarker lm) {
    preLog(lm);
    audit.info(lm,"audit log");
    clean();
  }
  @Override
  public void ctrlLogDebug(String s) {
    preLog();
    ctrl.debug(logSb.toString().concat(SEPARATE).concat(s));
    clean();
  }

  @Override
  public void ctrlLogInfo(String s) {
    preLog();

    ctrl.info(logSb.toString().concat(SEPARATE).concat(s));
    clean();
  }
  @Override
  public void ctrlLogInfo(Map logInfo) {
    preLog("INFO");
    logJson.putAll(logInfo);
    ctrl.info(JSONHelper.getJSONObject(logJson).toString());
    clean();
  }
  @Override
  public void ctrlLogError(String s) {
    preLog();
    ctrl.error(logSb.toString().concat(SEPARATE).concat(s));
    clean();
  }

  @Override
  public void ctrlLogError(String s, Throwable e) {
    preLog();
    ctrl.error(logSb.toString().concat(SEPARATE).concat(s), e);
    clean();
  }

  @Override
  public void serviceLogDebug(String s) {
    preLog();
    service.debug(logSb.toString().concat(SEPARATE).concat(s));
    clean();
  }

  @Override
  public void serviceLogInfo(String s) {
    preLog();
    service.info(logSb.toString().concat(SEPARATE).concat(s));
    clean();
  }

  @Override
  public void serviceLogError(String s) {
    preLog();
    service.error(logSb.toString().concat(SEPARATE).concat(s));
    clean();
  }

  @Override
  public void serviceLogError(String s, Throwable e) {
    preLog();
    service.error(logSb.toString().concat(SEPARATE).concat(s), e);
    clean();
  }

  @Override
  public void jobLogDebug(String s) {
    preLog();
    job.debug(logSb.toString().concat(SEPARATE).concat(s));
    clean();
  }

  @Override
  public void jobLogInfo(String s) {
    preLog();
    job.info(logSb.toString().concat(SEPARATE).concat(s));
    clean();
  }

  @Override
  public void jobLogError(String s) {
    preLog();
    job.error(logSb.toString().concat(SEPARATE).concat(s));
    clean();
  }

  @Override
  public void jobLogError(String s, Throwable e) {
    preLog();
    job.error(logSb.toString().concat(SEPARATE).concat(s), e);
    clean();
  }

  @Override
  public void consumerLogDebug(String s) {
    preLog();
    consumer.debug(logSb.toString().concat(SEPARATE).concat(s));
    clean();
  }

  @Override
  public void consumerLogInfo(String s) {
    preLog();
    consumer.info(logSb.toString().concat(SEPARATE).concat(s));
    clean();
  }

  @Override
  public void consumerLogError(String s) {
    preLog();
    consumer.error(logSb.toString().concat(SEPARATE).concat(s));
    clean();
  }

  @Override
  public void consumerLogError(String s, Throwable e) {
    preLog();
    consumer.error(logSb.toString().concat(SEPARATE).concat(s));
    clean();
  }
}
