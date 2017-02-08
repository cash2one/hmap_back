/**
 * Copyright (c) 2017. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.hms.distribution.util Date:2017/1/16 0016 Create
 * By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.distribution.util;

import com.dd.plist.NSArray;
import com.dd.plist.NSDictionary;
import com.dd.plist.NSString;
import com.dd.plist.PropertyListParser;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ReadIpa {
  /**
   * 读取ipa
   */
  public static Map<String, Object> readIPA(String ipaURL) {
    Map<String, Object> map = new HashMap<String, Object>();
    try {
      File file = new File(ipaURL);
      InputStream is = new FileInputStream(file);
      InputStream is2 = new FileInputStream(file);
      ZipInputStream zipIns = new ZipInputStream(is);
      ZipInputStream zipIns2 = new ZipInputStream(is2);
      ZipEntry ze;
      ZipEntry ze2;
      InputStream infoIs = null;
      NSDictionary rootDict = null;
      String icon = null;
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

            // 我们可以根据info.plist结构获取任意我们需要的东西
            // 比如下面我获取图标名称，图标的目录结构请下面图片
            // 获取图标名称
            NSDictionary iconDict = (NSDictionary) rootDict.get("CFBundleIcons");

            while (null != iconDict) {
              if (iconDict.containsKey("CFBundlePrimaryIcon")) {
                NSDictionary CFBundlePrimaryIcon =
                    (NSDictionary) iconDict.get("CFBundlePrimaryIcon");
                if (CFBundlePrimaryIcon.containsKey("CFBundleIconFiles")) {
                  NSArray CFBundleIconFiles =
                      (NSArray) CFBundlePrimaryIcon.get("CFBundleIconFiles");
                  icon = CFBundleIconFiles.getArray()[0].toString();
                  if (icon.contains(".png")) {
                    icon = icon.replace(".png", "");
                  }
                  System.out.println("获取icon名称:" + icon);
                  break;
                }
              }
            }
            break;
          }
        }
      }

      // 根据图标名称下载图标文件到指定位置
      while ((ze2 = zipIns2.getNextEntry()) != null) {
        if (!ze2.isDirectory()) {
          String name = ze2.getName();
          System.out.println(name);
          if (name.contains(icon.trim())) {
            System.out.println(11111);
            FileOutputStream fos = new FileOutputStream(new File("D:\\Work\\icon.png"));
            int chunk = 0;
            byte[] data = new byte[1024];
            while (-1 != (chunk = zipIns2.read(data))) {
              fos.write(data, 0, chunk);
            }
            fos.close();
            break;
          }
        }
      }

      ////////////////////////////////////////////////////////////////
      // 如果想要查看有哪些key ，可以把下面注释放开
      // for (String keyName : rootDict.allKeys()) {
      // System.out.println(keyName + ":" + rootDict.get(keyName).toString());
      // }


      // 应用包名
      NSString parameters = (NSString) rootDict.get("CFBundleIdentifier");
      map.put("package", parameters.toString());
      // 应用版本名
      parameters = (NSString) rootDict.objectForKey("CFBundleShortVersionString");
      map.put("versionName", parameters.toString());
      // 应用版本号
      parameters = (NSString) rootDict.get("CFBundleVersion");
      map.put("versionCode", parameters.toString());

      /////////////////////////////////////////////////
      infoIs.close();
      is.close();
      zipIns.close();

    } catch (Exception e) {
        e.printStackTrace();
       System.out.println(e.getMessage());
      map.put("code", "fail");
      map.put("error", "读取ipa文件失败");
    }
    return map;
  }


  public static void main(String[] args) {
    // System.out.println("======apk=========");
    // String apkUrl = "src/shenmiaotaowang_966.apk";
    // Map<String,Object> mapApk = ReadIpa.readAPK(apkUrl);
    // for (String key : mapApk.keySet()) {
    // System.out.println(key + ":" + mapApk.get(key));
    // }
    System.out.println("======ipa==========");
    String ipaUrl = "D:\\Work\\测试环境版.ipa";
    Map<String, Object> mapIpa = ReadIpa.readIPA(ipaUrl);
    for (String key : mapIpa.keySet()) {
      System.out.println(key + ":" + mapIpa.get(key));
    }
  }
}
