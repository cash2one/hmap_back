/**
 * Copyright (c) 2017. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.util.analyse Date:2017/1/11 0011 Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.distribution.util;

import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ApkUtil {
  public static final String VERSION_CODE = "versionCode";
  public static final String VERSION_NAME = "versionName";
  public static final String SDK_VERSION = "sdkVersion";
  public static final String TARGET_SDK_VERSION = "targetSdkVersion";
  public static final String USES_PERMISSION = "uses-permission";
  public static final String APPLICATION_LABEL = "application-label";
  public static final String APPLICATION_ICON = "application-icon";
  public static final String APPLICATION_ICON_640 = "application-icon-640";
  public static final String USES_FEATURE = "uses-feature";
  public static final String USES_IMPLIED_FEATURE = "uses-implied-feature";
  public static final String SUPPORTS_SCREENS = "supports-screens";
  public static final String SUPPORTS_ANY_DENSITY = "supports-any-density";
  public static final String DENSITIES = "densities";
  public static final String PACKAGE = "package";
  public static final String APPLICATION = "application:";
  public static final String LAUNCHABLE_ACTIVITY = "launchable-activity";

  private ProcessBuilder mBuilder;
  private static final String SPLIT_REGEX = "(: )|(=')|(' )|'";
  private static final String FEATURE_SPLIT_REGEX = "(:')|(',')|'";

  private Map<String,String> apkMap=new HashMap<String,String>();
  /**
   * aapt所在的目录。
   */
  // windows环境下直接指向appt.exe
  // 比如你可以放在lib下
  // private String mAaptPath = "lib/aapt";
  // 下面是linux下
//  private String mAaptPath = "/usr/local/python/img/aapt";
  private String mAaptPath = "D:\\DevTool\\adt-bundle-windows-x86_64-20140624\\sdk\\build-tools\\23.0.1\\aapt.exe";
  public ApkUtil() {
    mBuilder = new ProcessBuilder();
    mBuilder.redirectErrorStream(true);
  }

  /**
   * 返回一个apk程序的信息。
   *
   * @param apkPath apk的路径。
   * @return apkInfo 一个Apk的信息。
   */
  public ApkInfo getApkInfo(String apkPath) throws Exception {

    // 通过命令调用aapt工具解析apk文件
    Process process = mBuilder.command(mAaptPath, "d", "badging", apkPath).start();
    InputStream is = null;
    is = process.getInputStream();
    BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf8"));
    String tmp = br.readLine();
    try {
      if (tmp == null || !tmp.startsWith("package")) {
        throw new Exception("参数不正确，无法正常解析APK包。输出结果为:\n" + tmp + "...");
      }
      ApkInfo apkInfo = new ApkInfo();
      do {
        System.out.println("==============apkInfo");
        System.out.println(tmp);
        setApkInfoProperty(apkInfo, tmp);
      } while ((tmp = br.readLine()) != null);
      return apkInfo;
    } catch (Exception e) {
      throw e;
    } finally {
      process.destroy();
      closeIO(is);
      closeIO(br);
    }
  }

  /**
   * 设置APK的属性信息。
   *
   * @param apkInfo
   * @param source
   */
  private void setApkInfoProperty(ApkInfo apkInfo, String source) {
    if (source.startsWith(PACKAGE)) {
      String[] packageInfo = source.split(SPLIT_REGEX);
      apkInfo.setPackageName(packageInfo[2]);
      apkInfo.setVersionCode(packageInfo[4]);
      apkInfo.setVersionName(packageInfo[6]);
//      splitPackageInfo(apkInfo, source);
      apkMap.put("package",packageInfo[2]);
      apkMap.put("versionName",packageInfo[6]);

    } else if (source.startsWith(LAUNCHABLE_ACTIVITY)) {
      apkInfo.setLaunchableActivity(getPropertyInQuote(source));
    } else if (source.startsWith(SDK_VERSION)) {
      apkInfo.setSdkVersion(getPropertyInQuote(source));
    } else if (source.startsWith(TARGET_SDK_VERSION)) {
      apkInfo.setTargetSdkVersion(getPropertyInQuote(source));
    } else if (source.startsWith(USES_PERMISSION)) {
      apkInfo.addToUsesPermissions(getPropertyInQuote(source));
    } else if (source.startsWith(APPLICATION_LABEL)) {
      // window下获取应用名称
      apkInfo.setApplicationLable(getPropertyInQuote(source));
      apkMap.put("bundleName",getPropertyInQuote(source));
      apkMap.put("bundleDisplayName",getPropertyInQuote(source));
    }
    else if (source.startsWith(APPLICATION_ICON_640)) {
//      apkInfo.addToApplicationIcons(getKeyBeforeColon(source), getPropertyInQuote(source));
      apkMap.put("icon",getPropertyInQuote(source));
    }
//    else if (source.startsWith(APPLICATION_ICON)) {
//      apkInfo.addToApplicationIcons(getKeyBeforeColon(source), getPropertyInQuote(source));
//    }
else if (source.startsWith(APPLICATION)) {
      String[] rs = source.split("( icon=')|'");
      apkInfo.setApplicationIcon(rs[rs.length - 1]);
      // linux下获取应用名称
      apkInfo.setApplicationLable(rs[1]);
      apkMap.put("bundleName",rs[1]);
      apkMap.put("bundleDisplayName",rs[1]);
    } else if (source.startsWith(USES_FEATURE)) {
      apkInfo.addToFeatures(getPropertyInQuote(source));
    } else if (source.startsWith(USES_IMPLIED_FEATURE)) {
      apkInfo.addToImpliedFeatures(getFeature(source));
    } else {
      // System.out.println(source);
    }
  }

  private ImpliedFeature getFeature(String source) {
    String[] result = source.split(FEATURE_SPLIT_REGEX);
    ImpliedFeature impliedFeature = new ImpliedFeature(result[1], result[2]);
    return impliedFeature;
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

  public void uploadAndroidIcon(String apkUrl,String iconPath) throws IOException {
    ZipFile zipFile = new ZipFile(apkUrl);
    Enumeration<?> enumeration = zipFile.entries();
    ZipEntry zipEntry = null;
    while (enumeration.hasMoreElements()) {
      zipEntry = (ZipEntry) enumeration.nextElement();
      //我知道图片的图标名称就叫appicon_logo,所以可以这样获取
      if (zipEntry.getName().contains(iconPath)) {
        int length = 0;
        byte b[] = new byte [1024];
        OutputStream outputStream = new FileOutputStream(
            new File("D:\\img\\apkicon.png"));
        InputStream inputStream = zipFile.getInputStream(zipEntry);
        while (-1 != (length = inputStream.read(b))){
          outputStream.write(b, 0, length);
        }
        outputStream.close();
        break;
      }
    }
  }
  /**
   * 返回冒号前的属性名称
   *
   * @param source
   * @return
   */
  private String getKeyBeforeColon(String source) {
    return source.substring(0, source.indexOf(':'));
  }

  /**
   * 分离出包名、版本等信息。
   *
   * @param apkInfo
   * @param packageSource
   */
  private void splitPackageInfo(ApkInfo apkInfo, String packageSource) {
    String[] packageInfo = packageSource.split(SPLIT_REGEX);
    apkInfo.setPackageName(packageInfo[2]);
    apkInfo.setVersionCode(packageInfo[4]);
    apkInfo.setVersionName(packageInfo[6]);
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

  public static void main(String[] args) {
    try {
      String demo = "D:\\Work\\HandPortal2.4.0.apk";
      ApkUtil u=new ApkUtil();
      ApkInfo apkInfo = u.getApkInfo(demo);
//      System.out.println(apkInfo);
//      apkMap.forEach();
      u.apkMap.forEach( (key,value) -> System.err.println(key +" : "+value) );
      u.uploadAndroidIcon(demo,"res/drawable-xxxhdpi-v4/icon.png");
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  public String getmAaptPath() {
    return mAaptPath;
  }

  public void setmAaptPath(String mAaptPath) {
    this.mAaptPath = mAaptPath;
  }
}
