/**
 * Copyright (c) 2017. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.hms.distribution.service.impl Date:2017/1/12 0012 Create
 * By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.distribution.service.impl;

import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import hmap.core.hms.distribution.exception.HmsAppAnalyseException;
import hmap.core.hms.distribution.util.ApkInfo;
import hmap.core.hms.system.service.IHmsSystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dd.plist.NSDictionary;
import com.dd.plist.NSString;
import com.dd.plist.PropertyListParser;

import hmap.core.hms.distribution.dto.HmsAppDistributionDTO;
import hmap.core.hms.distribution.service.IAnalyseAppService;
import hmap.core.hms.uploader.dto.UploadFileDTO;
import hmap.core.hms.uploader.service.IHmsUploadObjectService;

@Service
public class AnalyseAppServiceImpl implements IAnalyseAppService {
  public static final String PACKAGE = "package";
  public static final String APPLICATION = "application:";
  public static final String APPLICATION_LABEL = "application-label";
  public static final String APPLICATION_ICON_640 = "application-icon-640";
  private static final String SPLIT_REGEX = "(: )|(=')|(' )|'";
  @Autowired
  IHmsUploadObjectService hmsUploadObjectService;
  @Autowired
  IHmsSystemConfigService iHmsSystemConfigService;

  @Override
  public HmsAppDistributionDTO analyseIpa(InputStream is) {
    Map<String, String> ipaMap = this.readIPA(is);
    HmsAppDistributionDTO distributionAndVersionDTO = new HmsAppDistributionDTO();
    distributionAndVersionDTO.setAppIcon(ipaMap.get("icon"));
    distributionAndVersionDTO.setBundleName(ipaMap.get("bundleName"));
    distributionAndVersionDTO.setAppName(ipaMap.get("bundleDisplayName"));
    distributionAndVersionDTO.setAppPlatform("iOS");
    distributionAndVersionDTO.setPackageName(ipaMap.get("package"));
    distributionAndVersionDTO.setLastestVersion(ipaMap.get("versionName"));
    return distributionAndVersionDTO;
  }

  @Override
  public HmsAppDistributionDTO analyseApk(String apkPath) throws IOException {
    Map<String, String> ipaMap = this.readApk(apkPath);
    HmsAppDistributionDTO distributionAndVersionDTO = new HmsAppDistributionDTO();
    distributionAndVersionDTO.setAppIcon(ipaMap.get("icon"));
    distributionAndVersionDTO.setBundleName(ipaMap.get("bundleName"));
    distributionAndVersionDTO.setAppName(ipaMap.get("bundleDisplayName"));
    distributionAndVersionDTO.setAppPlatform("Android");
    distributionAndVersionDTO.setPackageName(ipaMap.get("package"));
    distributionAndVersionDTO.setLastestVersion(ipaMap.get("versionName"));
    return distributionAndVersionDTO;
  }

  /**
   * 返回出格式为name: 'value'中的value内容。
   *
   * @param source
   * @return
   */
  private String getPropertyInQuote(String source) {
    int index = source.indexOf("'") + 1;
    return source.substring(index, source.indexOf('\'', index));
  }

  /**
   * 释放资源。
   *
   * @param c 将关闭的资源
   */
  private final void closeIO(Closeable c) {
    if (c != null) {
      try {
        c.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 读取ipa
   */
  private Map<String, String> readIPA(InputStream is) {
    Map<String, String> map = new HashMap<String, String>();
    try {
      ByteArrayOutputStream baosTmp = new ByteArrayOutputStream();
      byte[] buffer = new byte[1024];
      int len;
      while ((len = is.read(buffer)) > -1) {
        baosTmp.write(buffer, 0, len);
      }
      baosTmp.flush();
      InputStream propertiesStream = new ByteArrayInputStream(baosTmp.toByteArray());
      InputStream iconStream = new ByteArrayInputStream(baosTmp.toByteArray());
      ZipInputStream zipIns = new ZipInputStream(propertiesStream);
      ZipInputStream zipIcon = new ZipInputStream(iconStream);
      ZipEntry ze;
      ZipEntry zeIcon;
      InputStream infoIs = null;
      NSDictionary rootDict = null;
      String icon = null;
      String webIcon = "AppIcon40x40@3x";
      UploadFileDTO upd = null;

      while ((ze = zipIns.getNextEntry()) != null) {
        if (!ze.isDirectory()) {
          String name = ze.getName();
          if (null != name && name.toLowerCase().contains("info.plist")) {
            ByteArrayOutputStream _copy = new ByteArrayOutputStream();
            int chunk = 0;
            byte[] data = new byte[1024];
            while (-1 != (chunk = zipIns.read(data))) {
              _copy.write(data, 0, chunk);
            }
            infoIs = new ByteArrayInputStream(_copy.toByteArray());
            rootDict = (NSDictionary) PropertyListParser.parse(infoIs);
            NSDictionary iconDict = (NSDictionary) rootDict.get("CFBundleIcons");
            break;
          }
        }
      }
      while ((zeIcon = zipIcon.getNextEntry()) != null) {
        if (!zeIcon.isDirectory()) {
          String name = zeIcon.getName();
          if (name.contains(webIcon)) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int n = 0;
            while ((n = zipIcon.read(buf)) >= 0)
              baos.write(buf, 0, n);
            byte[] content = baos.toByteArray();
            InputStream fileIn = new ByteArrayInputStream(content);
            upd = hmsUploadObjectService.uploadFile(fileIn, "webIcon.png");
            // 修改为使用上传服务，并获取上传后的url
          }
        }
      }
      // 应用包名
      NSString parameters = (NSString) rootDict.get("CFBundleIdentifier");
      map.put("package", parameters.toString());
      // 应用版本名
      parameters = (NSString) rootDict.objectForKey("CFBundleShortVersionString");
      map.put("versionName", parameters.toString());
      // 应用版本号
      parameters = (NSString) rootDict.get("CFBundleVersion");
      map.put("versionCode", parameters.toString());
      // 应用名称
      parameters = (NSString) rootDict.get("CFBundleName");
      map.put("bundleName", parameters.toString());
      // 应用显示名称
      parameters = (NSString) rootDict.get("CFBundleDisplayName");
      map.put("bundleDisplayName", parameters.toString());
      map.put("icon", upd.getObjectUrl());
      closeIO(is);
      closeIO(infoIs);
      closeIO(zipIns);
      closeIO(zipIcon);

    } catch (FileNotFoundException e1) {
      e1.printStackTrace();
    } catch (IOException e1) {
      e1.printStackTrace();
    } catch (Exception e) {
      map.put("code", "fail");
      map.put("error", "读取ipa文件失败");
    }
    return map;
  }

  private Map<String, String> readApk(String apkPath) throws IOException {
    Map<String, String> apkMap = new HashMap<String, String>();
    ProcessBuilder mBuilder = new ProcessBuilder();
    mBuilder.redirectErrorStream(true);
    String mAaptPath = iHmsSystemConfigService.getConfigValue("aapt.path");
    Process process = mBuilder.command(mAaptPath, "d", "badging", apkPath).start();
    InputStream is = null;
    is = process.getInputStream();
    BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf8"));
    String tmp = br.readLine();
    try {
      if (tmp == null || !tmp.startsWith("package")) {
        // App无效
        throw new HmsAppAnalyseException(HmsAppAnalyseException.EXCEPTION_CODE,
            HmsAppAnalyseException.APP_INVALID);
      }
      do {
        if (tmp.startsWith(PACKAGE)) {
          String[] packageInfo = tmp.split(SPLIT_REGEX);
          apkMap.put("package", packageInfo[2]);
          apkMap.put("versionName", packageInfo[6]);
        } else if (tmp.startsWith(APPLICATION_LABEL)) {
          // window下获取应用名称
          apkMap.put("bundleName", getPropertyInQuote(tmp));
          apkMap.put("bundleDisplayName", getPropertyInQuote(tmp));
        } else if (tmp.startsWith(APPLICATION_ICON_640)) {
          // 需要上传app icon
          String iconPath = getPropertyInQuote(tmp);
          String iconUrl = this.uploadAndroidIcon(apkPath, iconPath);
          apkMap.put("icon", iconUrl);
          // xuyao
        } else if (tmp.startsWith(APPLICATION)) {
          String[] rs = tmp.split("( icon=')|'");
          // linux下获取应用名称
          apkMap.put("bundleName", rs[1]);
          apkMap.put("bundleDisplayName", rs[1]);
        }
      } while ((tmp = br.readLine()) != null);

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      process.destroy();
      closeIO(is);
      closeIO(br);
    }
    return apkMap;
  }

  public String uploadAndroidIcon(String apkUrl, String iconPath) throws IOException {
    UploadFileDTO upd = null;
    ZipFile zipFile = new ZipFile(apkUrl);
    Enumeration<?> enumeration = zipFile.entries();
    ZipEntry zipEntry = null;
    while (enumeration.hasMoreElements()) {
      zipEntry = (ZipEntry) enumeration.nextElement();
      // 我知道图片的图标名称就叫appicon_logo,所以可以这样获取
      if (zipEntry.getName().contains(iconPath)) {
        int length = 0;
        byte b[] = new byte[1024];
        InputStream inputStream = zipFile.getInputStream(zipEntry);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int n = 0;
        while ((n = inputStream.read(buf)) >= 0)
          baos.write(buf, 0, n);
        byte[] content = baos.toByteArray();
        InputStream fileIn = new ByteArrayInputStream(content);
        upd = hmsUploadObjectService.uploadFile(fileIn, "webIcon.png");
        break;
      }
    }
    return upd.getObjectUrl();
  }
}
