/**
 * Copyright (c) 2017. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.hms.distribution.util Date:2017/1/19 0019 Create
 * By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.distribution.service.impl;

import hmap.core.hms.distribution.dto.HmsAppDistributionDTO;
import hmap.core.hms.distribution.service.IPlistGeneratorService;
import hmap.core.hms.uploader.dto.UploadFileDTO;
import hmap.core.hms.system.service.IHmsSystemConfigService;
import hmap.core.hms.uploader.service.IHmsUploadObjectService;
import hmap.core.hms.system.service.ILogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;

/**
 *
 * @author ZSL
 *
 */
@Service
public class PlistGeneratorServiceImpl implements IPlistGeneratorService {
  @Autowired
  private IHmsSystemConfigService iHmsSystemConfigService;
  @Autowired
  private ILogService iLogService;
  @Autowired
  private IHmsUploadObjectService iHmsUploadObjectService;

  public String createPlist(HmsAppDistributionDTO dto) throws IOException {
    String tmpPlistLocation = iHmsSystemConfigService.getConfigValue("plist.location");
    UploadFileDTO upd = null;
    // 这个地址应该是创建的服务器地址，在这里用生成到本地磁盘地址
    // final String path = "D:/upload/plists/";
    File file = new File(tmpPlistLocation);
    if (!file.exists()) {
      file.mkdirs();
    }
    String plistFile = dto.getAppName() + System.currentTimeMillis() + ".plist";
    final String PLIST_PATH = tmpPlistLocation + plistFile;
    file = new File(PLIST_PATH);
    if (!file.exists()) {
      try {
        file.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    String plist = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
        + "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n"
        + "<plist version=\"1.0\">\n" + "<dict>\n" + "<key>items</key>\n" + "<array>\n" + "<dict>\n"
        + "<key>assets</key>\n" + "<array>\n"

        + "<dict>\n" + "<key>kind</key>\n"
        + "<string>software-package</string>\n" + "<key>url</key>\n"
        // 你之前所上传的ipa文件路径
        + "<string>"+dto.getDownloadUrl()+"</string>\n"
        + "</dict>\n"
        + "<dict>\n" + "<key>kind</key>\n"
        + "<string>display-image</string>\n" + "<key>needs-shine</key>\n"
        + "<true/>\n"
        + "<key>url</key>\n"
        // icon的路径
        + "<string>"+dto.getAppIcon()+"</string>\n"
        + "</dict>\n"
        + "<dict>\n" + "<key>kind</key>\n"
        + "<string>full-size-image</string>\n" + "<key>needs-shine</key>\n"
        + "<true/>\n"
        + "<key>url</key>\n"
        // icon的路径
        + "<string>"+dto.getAppIcon()+"</string>\n"
        + "</dict>\n"

        + "</array>\n" + "<key>metadata</key>\n" + "<dict>\n"
        + "<key>bundle-identifier</key>\n"
        // 这个是开发者账号用户名，也可以为空，为空安装时看不到图标，完成之后可以看到
        + "<string>"+dto.getPackageName()+"</string>\n" + "<key>bundle-version</key>\n"
        + "<string>"+dto.getLastestVersion()+"</string>\n" + "<key>kind</key>\n" + "<string>software</string>\n"
        + "<key>subtitle</key>\n" + "<string>下载</string>\n" + "<key>title</key>\n"
        + "<string>"+dto.getAppName()+"</string>\n" + "</dict>\n" + "</dict>\n" + "</array>\n" + "</dict>\n"
        + "</plist>";
    try {
      FileOutputStream output = new FileOutputStream(file);
      OutputStreamWriter writer;
      writer = new OutputStreamWriter(output, "UTF-8");
      writer.write(plist);
      writer.close();
      output.close();
      InputStream in = new FileInputStream(file);
      upd = iHmsUploadObjectService.uploadFile(in, plistFile);
    } catch (Exception e) {

      iLogService.serviceLogError("==========创建plist文件异常：" + e.getMessage());
    }
    iLogService.serviceLogInfo("==========成功创建plist文件");
    // 开始将plist上传到ftp服务器上
    // iHmsUploadObjectService.uploadFile(file,false);
    return upd.getObjectUrl();
  }

  public static String createHtml(String plistPath) {
    System.out.println("==========开始创建html文件");

    // 这个地址应该是生成的服务器地址，在这里用生成到本地磁盘地址
    final String path = "D:/upload/htmls/";
    File file = new File(path);
    if (!file.exists()) {
      file.mkdirs();
    }
    String plistFile = "a.plist";
    final String PLIST_PATH = path + plistFile;
    file = new File(PLIST_PATH);
    if (!file.exists()) {
      try {
        file.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    String html = "<!DOCTYPE html>\n" + "<html lang=\"en\">\n" + "<head>\n"
        + "<meta charset=\"UTF-8\">\n" + "<title>下载</title>\n"
        + "<script type=\"text/javascript\">\n" + "var url = '" + plistPath + "';\n"
        + "window.location.href = \"itms-services://?action=download-manifest&url=\" + url;\n"
        + "</script>\n" + "</head>\n" + "<body></body>\n" + "</html>";

    try {
      FileOutputStream output = new FileOutputStream(file);
      OutputStreamWriter writer = new OutputStreamWriter(output, "UTF-8");
      writer.write(html);
      writer.close();
      output.close();
    } catch (IOException e) {
      System.err.println("==========创建html文件异常：" + e.getMessage());
    }
    System.out.print("==========成功创建html文件");

    return "success";
  }

  public static void main(String[] args) throws IOException {
    // String plistPath = PlistUtil.createPlist();
    // createHtml(plistPath);
  }
}
