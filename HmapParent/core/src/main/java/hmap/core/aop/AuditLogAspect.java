/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.aop Date:2016/8/17 0017 Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.aop;

import hmap.core.hms.system.service.ILogService;
import hmap.core.security.SecurityUtils;
import hmap.core.util.JSONHelper;
import hmap.core.util.StatelessRequestHelper;
import net.logstash.logback.marker.LogstashMarker;
import net.sf.json.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;
import static net.logstash.logback.marker.Markers.*;

@Aspect
@Component
public class AuditLogAspect {
  private static final Logger logger = LoggerFactory.getLogger("auditLog");

  @Autowired
  private ILogService iLogService;
  private String requestPath = null; // 请求地址
  private Map<?, ?> inputParamMap = null; // 传入参数
  private String userName = SecurityUtils.getCurrentUserLogin();
  private String clientId = SecurityUtils.getCurrentClient();

  @Pointcut("within(@org.springframework.stereotype.Controller *)")
  public void auditLog() {}

  /**
   * 同时在所拦截方法的前后执行 在ProceedingJoinPoint.proceed()前后加逻辑
   */
  @Around("auditLog()")
  public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
    StringBuffer logBuffer = new StringBuffer();
    RequestAttributes ra = RequestContextHolder.getRequestAttributes();
    String ip = StatelessRequestHelper.getIp();
    ServletRequestAttributes sra = (ServletRequestAttributes) ra;
    HttpServletRequest request = sra.getRequest();
    Map<String, String> debugInfo = new HashMap<String, String>();
    // 用户名
    // userName = SecurityUtils.getCurrentUserLogin();
    // clientId=SecurityUtils.getCurrentClient();
    // 获取输入参数
    inputParamMap = request.getParameterMap();
    //获取body参数
    String str = "";
    String wholeStr = "";
    // 获取请求地址
    requestPath = request.getRequestURI();
    String appVersion = request.getHeader("X-hmapfront-version");
    String client = request.getHeader("X-hmapfront-client");
    String contentType =   request.getContentType();
    String requestMethod = request.getMethod();

    if("post".equals(requestMethod.toLowerCase())){//判断是不是POST
        if((contentType != null) && (contentType.toLowerCase().startsWith("multipart/"))){//判断是不是文件上传
            wholeStr = "{\"message\":\"文件上传,content-length长度为:"+request.getContentLengthLong()+"\"}";
        }else{
            BufferedReader br = request.getReader();
            while((str = br.readLine()) != null){
                wholeStr += str;
            }
        }
    }
     if(wholeStr.equals("")||wholeStr==null){
         wholeStr = "{}";
     }


    try {
      long startTime = System.currentTimeMillis();
      Object result = joinPoint.proceed();
      long costTime = System.currentTimeMillis() - startTime;
      // 记录执行请求耗时
      // logger.info(
      // "module:{},appName:{},clientId:{},userName:{},requestURI:{},serverIp:{},cost:{},parameter:{},client:{},appVersion:{},result:{}",
      // module, appName, clientId, userName, requestPath, ip, costTime,
      // JSONObject.fromObject(inputParamMap).toString(), client,
      // appVersion,JSONObject.fromObject(result).toString());
      // debugInfo.put("cost",String.valueOf(costTime));
      // debugInfo.put("requestURI",requestPath);
      // debugInfo.put("client",client);
      // debugInfo.put("appVersion",appVersion);
      // debugInfo.put("result",JSONHelper.getJSONObject(result).toString());
      // debugInfo.put("parameter",JSONObject.fromObject(inputParamMap).toString());
//      logBuffer.append("cost:").append(costTime).append(",requestURI:").append(requestPath)
//          .append(",parameter:").append(JSONObject.fromObject(inputParamMap).toString())
//          .append(",client:").append(client).append(",appVersion:").append(appVersion)
//          .append(",result:").append(JSONHelper.getJSONObject(result));
      // iLogService.auditLogInfo(logBuffer.toString());

//      LogstashMarker lm = append("cost", costTime).and(append("requestURI", requestPath))
////          .and(append("parameter", JSONObject.fromObject(inputParamMap).toString()))
//          .and(append("client", client)) .and(append("appVersion", appVersion));
////          .and(append("result", JSONHelper.getJSONObject(result)));

//      logger.info(append("cost", costTime).and(append("requestURI", requestPath))
//          //          .and(append("parameter", JSONObject.fromObject(inputParamMap).toString()))
//          .and(append("client", client)) .<LogstashMarker>and(append("appVersion1", appVersion)),"audit message");
      logBuffer = null;
      LogstashMarker lm = append("cost", costTime).<LogstashMarker>and(
          append("requestURI", requestPath))
          .and(append("client", client))
          .and(append("appVersion", appVersion))
          .and(append("requestMethod", requestMethod))
          .and(append("contentType", contentType))
          .and(appendRaw("parameter", JSONObject.fromObject(inputParamMap).toString()))
          .and(appendRaw("body", wholeStr))
                   .<LogstashMarker>and(appendRaw("result", JSONHelper.getJSONObject(result).toString()));
      iLogService.auditLogInfo(lm);
      return result;
    } catch (IllegalArgumentException e) {
      throw e;
    }

  }

  /**
   * 所拦截方法执行之后执行
   */
  @AfterThrowing(pointcut = "auditLog()", throwing = "e")
  public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
    // logger.error("Exception in {}.{}() with cause = {} and exception {}",
    // joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(),
    // e.getCause(), e);
    StringBuffer logBuffer = new StringBuffer();
    logBuffer.append("Exception in ").append(joinPoint.getSignature().getDeclaringTypeName())
        .append(":").append(joinPoint.getSignature().getName());
    iLogService.ctrlLogError(logBuffer.toString(), e);
    logBuffer = null;
  }

}
