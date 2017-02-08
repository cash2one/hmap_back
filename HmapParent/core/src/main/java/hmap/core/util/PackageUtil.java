/**
 * Copyright (c) 2017. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.util Date:2017/1/11 0011 Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.util;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipInputStream;

import org.apkinfo.api.util.AXmlResourceParser;
import org.apkinfo.api.util.XmlPullParser;
import org.apkinfo.api.util.XmlPullParserException;

import com.dd.plist.NSDictionary;
import com.dd.plist.NSNumber;
import com.dd.plist.NSObject;
import com.dd.plist.NSString;
import com.dd.plist.PropertyListParser;

/**
 * 解析apk、ipa应用程序包,获得包名、应用程序名、版本号等信息。
 *
 * @author Eric.Sung
 * @date 2015.2.10
 *
 */
public class PackageUtil {
  /**
   * 解析IPA文件
   *
   * @author Eric.Sung
   * @date 2015.2.10
   */
  public static Map<String, Object> analysiIpa(InputStream is) {
    // result map
    Map<String, Object> resultMap = new HashMap<String, Object>();
    try {
      ZipInputStream zipIns = new ZipInputStream(is);
      ZipEntry ze;
      InputStream infoIs = null;
      while ((ze = zipIns.getNextEntry()) != null) {
        if (!ze.isDirectory()) {
          String name = ze.getName();
          if (name.contains("Info.plist")) {
            ByteArrayOutputStream _copy = new ByteArrayOutputStream();
            // int read = 0;
            int chunk = 0;
            byte[] data = new byte[256];
            while (-1 != (chunk = zipIns.read(data))) {
              // read += data.length;
              _copy.write(data, 0, chunk);
            }
            infoIs = new ByteArrayInputStream(_copy.toByteArray());
            break;
          }
        }
      }
      NSDictionary rootDict = (NSDictionary) PropertyListParser.parse(infoIs);
      String[] keyArray = rootDict.allKeys();
      for (String key : keyArray) {
        NSObject value = rootDict.objectForKey(key);
        if (key.equals("CFBundleSignature")) {
          continue;
        }
        if (value.getClass().equals(NSString.class) || value.getClass().equals(NSNumber.class)) {
          resultMap.put(key, value.toString());
        }
      }
      zipIns.close();
    } catch (FileNotFoundException e) {
      resultMap.put("error", e.getStackTrace());
    } catch (Exception e) {
      resultMap.put("error", e.getStackTrace());
    }
    return resultMap;
  }

  /**
   * 解析APK文件
   *
   * @author Eric.Sung
   * @date 2015.2.10
   * @param inputstream
   * @return Map<String,Map<String,Object>>
   */
  public static Map<String, Map<String, Object>> analysiApk(InputStream is) {
    // result
    Map<String, Map<String, Object>> resultMap = new HashMap<String, Map<String, Object>>();
    try {
      ZipInputStream zipIns = new ZipInputStream(is);
      zipIns.getNextEntry();
      AXmlResourceParser parser = new AXmlResourceParser();
      parser.open(zipIns);
      boolean flag = true;

      while (flag) {
        int type = parser.next();
        if (type == XmlPullParser.START_TAG) {
          int count = parser.getAttributeCount();
          String action = parser.getName().toUpperCase();
          if (action.equals("MANIFEST") || action.equals("APPLICATION")) {
            Map<String, Object> tempMap = new HashMap<String, Object>();
            for (int i = 0; i < count; i++) {
              String name = parser.getAttributeName(i);
              String value = parser.getAttributeValue(i);
              value = value == null ? "" : value;
              tempMap.put(name, value);
            }
            resultMap.put(action, tempMap);
          } else {
            Map<String, Object> manifest = resultMap.get("MANIFEST");
            Map<String, Object> application = resultMap.get("APPLICATION");
            if (manifest != null && application != null) {
              flag = false;
            }
            continue;
          }
        }
      }
    } catch (ZipException e) {
      Map<String, Object> errorMap = new HashMap<String, Object>();
      errorMap.put("cause", e.getCause());
      errorMap.put("message", e.getMessage());
      errorMap.put("stack", e.getStackTrace());
      resultMap.put("error", errorMap);
    } catch (IOException e) {
      Map<String, Object> errorMap = new HashMap<String, Object>();
      errorMap.put("cause", e.getCause());
      errorMap.put("message", e.getMessage());
      errorMap.put("stack", e.getStackTrace());
      resultMap.put("error", errorMap);
    } catch (XmlPullParserException e) {
      Map<String, Object> errorMap = new HashMap<String, Object>();
      errorMap.put("cause", e.getCause());
      errorMap.put("message", e.getMessage());
      errorMap.put("stack", e.getStackTrace());
      resultMap.put("error", errorMap);
    }
    return resultMap;
  }

  /**
   * Test ipa
   *
   * @author Eric.Sung
   * @date 2015.2.10
   */
  /*
   * public static void TestIpa(){ try { File file = new
   * File("D:/com.mojang.minecraftpe_0.10.4.ipa"); InputStream is = new FileInputStream(file);
   * Map<String,Object> result = analysiIpa(is); System.out.println(result); is.close(); } catch
   * (FileNotFoundException e) { e.printStackTrace(); } catch (Exception e) { e.printStackTrace(); }
   * }
   */

  /**
   * Test apk
   *
   * @author Eric.Sung
   * @date 2015.2.10
   */

  public static void TestApk() {
    File file = new File("D:\\Work\\Project\\珠峰保险\\珠峰保险1223\\Joywok_v2.5.0_20161222_Official.apk");
    try {
      FileInputStream fis = new FileInputStream(file);
      Map<String, Map<String, Object>> result = analysiApk(fis);
      System.out.println(result.toString());
      fis.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
    public static void main(String[] args) {
        PackageUtil pu=new PackageUtil();
        pu.TestApk();
    }

}
