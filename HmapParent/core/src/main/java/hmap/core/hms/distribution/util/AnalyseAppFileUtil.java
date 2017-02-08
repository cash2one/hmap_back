/**
 * Copyright (c) 2017. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.util Date:2017/1/5 0005 Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.distribution.util;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.dd.plist.*;
import hmap.core.hms.uploader.dto.UploadFileDTO;

/**
 *
 * @author ZSL
 *
 */
public final class AnalyseAppFileUtil {
  /**
   * 读取ipa
   */
  private static Map<String, String> readIPA(InputStream is) {
    Map<String, String> map = new HashMap<String, String>();
    try {
      ByteArrayOutputStream baosTmp = new ByteArrayOutputStream();
      byte[] buffer = new byte[1024];
      int len;
      while ((len = is.read(buffer)) > -1 ) {
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
            //upd = hmsUploadObjectService.uploadFile(fileIn, "webIcon.png");
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
      infoIs.close();
      is.close();
      zipIns.close();
      zipIcon.close();

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


  public static void main(String[] args) throws FileNotFoundException {
    System.out.println("======apk=========");
    // String apkUrl = "D:\\Work\\Project\\珠峰保险\\珠峰保险1223\\Joywok_v2.5.0_20161222_Official.apk";
    // Map<String, Object> mapApk = ReadUtil.readAPK(apkUrl);
    // for (String key : mapApk.keySet()) {
    // System.out.println(key + ":" + mapApk.get(key));
    // }
    // System.out.println("======ipa==========");
    String ipaUrl = "D:\\Work\\测试环境版.ipa";
    File file = new File(ipaUrl);
    InputStream is = new FileInputStream(file);
    Map<String, String> mapIpa = AnalyseAppFileUtil.readIPA(is);
    for (String key : mapIpa.keySet()) {
      System.out.println(key + ":" + mapIpa.get(key));
    }
  }
}
