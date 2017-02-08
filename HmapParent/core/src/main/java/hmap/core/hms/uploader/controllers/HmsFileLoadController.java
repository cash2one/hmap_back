package hmap.core.hms.uploader.controllers;

import hmap.core.hms.system.service.ILogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.hms.controllers Date:2016/12/1 Create By:chuanqi.cao@hand-china.com
 */
@Controller
@RequestMapping("/api")
public class HmsFileLoadController {
  @Autowired
  ILogService iLogService;

  // 用于使用流下载文件
  @RequestMapping(value = "/file/load")
  public void fileDownload(HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    BufferedInputStream bis;
    BufferedOutputStream bos;
    OutputStream fos;
    String url = request.getParameter("url");
    String dispositionKey = null;
    String lengthKey = null;
    String typeKey = null;
    List<String> dispositionList = null;
    List<String> lengthList = null;
    List<String> typeList = null;
    iLogService.ctrlLogInfo("url:" + url);
    url = URLDecoder.decode(url);
    try {
      URL e = new URL(url);
      System.out.println("打印出url:   " + url);
      HttpURLConnection connection = (HttpURLConnection) e.openConnection();
      connection.setRequestMethod("GET");
      connection.setDoInput(true);
      connection.connect();

      if (connection.getResponseCode() != 200) {
        throw new RuntimeException(
            "HTTP GET Request Failed with Error code : " + connection.getResponseCode());
      }
      Map<String, List<String>> map = connection.getHeaderFields();
      Set<String> keys = map.keySet();
      iLogService.ctrlLogInfo("keys size:" + keys.size());
      for (String s : keys) {
        if (StringUtils.isNotEmpty(s)) {
          iLogService.ctrlLogInfo("key:" + s);

          if (s.equalsIgnoreCase("Content-Disposition")) {
            dispositionKey = s;
          }
          if (s.equalsIgnoreCase("Content-Length")) {
            lengthKey = s;
          }
          if (s.equalsIgnoreCase("Content-Type")) {
            typeKey = s;
          }
        }
      }
      if (StringUtils.isNotEmpty(dispositionKey)) {
        dispositionList = map.get(dispositionKey);
      }
      if (StringUtils.isNotEmpty(lengthKey)) {
        lengthList = map.get(lengthKey);
      }
      if (StringUtils.isNotEmpty(typeKey)) {
        typeList = map.get(typeKey);
      }

      if (dispositionList!=null&&dispositionList.size() > 0) {
        response.setHeader("Content-Disposition",
            new String(dispositionList.get(0).getBytes("UTF-8"), "ISO_8859_1"));
      }
      if (lengthList!=null&&lengthList.size() > 0) {
        response.setHeader("Content-Length", lengthList.get(0));
      }
      if (typeList!=null&&typeList.size() > 0) {
        response.setHeader("Content-Type ", typeList.get(0));
      }
      bis = new BufferedInputStream(connection.getInputStream());
      fos = response.getOutputStream();
      bos = new BufferedOutputStream(fos);
      int byteRead;
      byte[] buffer = new byte[8192];
      while ((byteRead = bis.read(buffer, 0, 8192)) != -1) {
        bos.write(buffer, 0, byteRead);
      }

      bos.flush();
      bis.close();
      fos.close();
      bos.close();
      connection.disconnect();
    } catch (Exception e) {
      e.printStackTrace();
      iLogService.ctrlLogInfo("下载失败了");
    }

  }
}
