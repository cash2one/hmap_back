/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.hms.service.impl Date:2016/8/4 0004 Create By:zongyun.zhou@hand-china.com
 */
package hmap.core.hms.uploader.service.impl;

import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import org.springframework.beans.factory.annotation.Autowired;

import com.jcraft.jsch.*;

import hmap.core.hms.uploader.dto.UploadFileDTO;
import hmap.core.hms.uploader.dto.UploadFileWithStreamDTO;
import hmap.core.hms.system.service.IHmsSystemConfigService;
import hmap.core.hms.system.service.ILogService;
import hmap.core.util.FileHelper;
import net.coobird.thumbnailator.Thumbnails;


public class SftpUploaderServiceImpl extends AbstractUploaderServiceImpl {
  @Autowired
  IHmsSystemConfigService systemConfigService;
  @Autowired
  private ILogService iLogService;
  private Session sshSession;
  private ChannelSftp sftp;
  private String host = null;
  private String port = null;
  private String username = null;
  private String password = null;
  private String uploadDir = null;
  private String outUrlPrefix = null;
  private String thumbnailScale = null;
  private String thumbnailOutputQuality = null;

  private String hostValue = null;
  private String portValue = null;
  private String usernameValue = null;
  private String passwordValue = null;
  private String uploadDirValue = null;
  private String outUrlPrefixValue = null;
  private String thumbnailScaleValue = null;
  private String thumbnailOutputQualityValue = null;

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public String getPort() {
    return port;
  }

  public void setPort(String port) {
    this.port = port;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getUploadDir() {
    return uploadDir;
  }

  public void setUploadDir(String uploadDir) {
    this.uploadDir = uploadDir;
  }

  public String getOutUrlPrefix() {
    return outUrlPrefix;
  }

  public void setOutUrlPrefix(String outUrlPrefix) {
    this.outUrlPrefix = outUrlPrefix;
  }

  public String getThumbnailScale() {
    return thumbnailScale;
  }

  public void setThumbnailScale(String thumbnailScale) {
    this.thumbnailScale = thumbnailScale;
  }

  public String getThumbnailOutputQuality() {
    return thumbnailOutputQuality;
  }

  public void setThumbnailOutputQuality(String thumbnailOutputQuality) {
    this.thumbnailOutputQuality = thumbnailOutputQuality;
  }

  private void loadConfig() {
    hostValue = systemConfigService.selectByConfigKey(host).getConfigValue();
    portValue = systemConfigService.selectByConfigKey(port).getConfigValue();
    usernameValue = systemConfigService.selectByConfigKey(username).getConfigValue();
    passwordValue = systemConfigService.selectByConfigKey(password).getConfigValue();
    uploadDirValue = systemConfigService.selectByConfigKey(uploadDir).getConfigValue();
    outUrlPrefixValue = systemConfigService.selectByConfigKey(outUrlPrefix).getConfigValue();
    thumbnailScaleValue = systemConfigService.selectByConfigKey(thumbnailScale).getConfigValue();
    thumbnailOutputQualityValue =
        systemConfigService.selectByConfigKey(thumbnailOutputQuality).getConfigValue();
  }

  /**
   * 连接sftp服务器
   *
   * @return
   */
  public Boolean connect() {
    this.loadConfig();
    try {
      JSch jsch = new JSch();
      jsch.getSession(usernameValue, hostValue, Integer.parseInt(portValue));
      sshSession = jsch.getSession(usernameValue, hostValue, Integer.parseInt(portValue));
      sshSession.setPassword(passwordValue);
      Properties sshConfig = new Properties();
      sshConfig.put("StrictHostKeyChecking", "no");
      sshSession.setConfig(sshConfig);
      sshSession.connect();
      Channel channel = sshSession.openChannel("sftp");
      channel.connect();
      sftp = (ChannelSftp) channel;
      iLogService.serviceLogInfo("Connected to " + hostValue + ".");
      return true;
    } catch (Exception e) {
      iLogService.serviceLogError("sftp connect fail");
      disconnect();
    }
    return false;
  }

  /**
   * 关闭连接
   */

  public void disconnect() {
    if (sftp != null) {
      sftp.exit();
    }
    if (sshSession != null) {
      sshSession.disconnect();
    }
    iLogService.serviceLogInfo("sshSession disconnect");
  }

  @Override
  public String upload(String directory, InputStream in, String fileName) {
    this.connect();
    SimpleDateFormat parser = new SimpleDateFormat("yyyyMMdd");
    String timeStamp = parser.format(new Date());
    String dir = directory + "/" + timeStamp;
    System.out.println(dir);
    try {
      sftp.cd(dir);
    } catch (Exception e) {
      try {
        sftp.mkdir(dir);
        sftp.cd(dir);
      } catch (SftpException e1) {
        e1.printStackTrace();
      }
    }
    try {
      sftp.put(in, fileName);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.disconnect();
    }

    return timeStamp;
  }


  public String saveLocal(String directory, InputStream in, String fileName) {
    OutputStream outputStream = null;
    SimpleDateFormat parser = new SimpleDateFormat("yyyyMMdd");
    String timeStamp = parser.format(new Date());
    String dir = directory + "/" + timeStamp;
    String tmpFileName = dir.concat(fileName);
    File file = new File(tmpFileName);
    File parent = file.getParentFile();
    if (parent != null && !parent.exists()) {
      parent.mkdirs();
    }
    try {
      file.createNewFile();
      int length = 0;
      byte b[] = new byte[1024];
      outputStream = new FileOutputStream(file);
      while (-1 != (length = in.read(b))) {
        outputStream.write(b, 0, length);
      }

    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        outputStream.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return tmpFileName;
  }

  /**
   * 下载文件
   */
  @Override
  public void download(String downloadFile, String dst) {
    this.connect();
    try {
      sftp.get(downloadFile, dst);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.disconnect();
    }
  }

  /**
   * 删除文件
   */
  public void delete(String directory, String deleteFile) {
    try {
      sftp.cd(directory);
      sftp.rm(deleteFile);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 创建指定文件夹
   *
   * @param dirName dirName
   */
  public void mkDir(String basePath, String dirName) {

    String[] dirs = dirName.split("/");
    try {
      sftp.cd(basePath);
      for (int i = 0; i < dirs.length; i++) {
        boolean dirExists = openDir(dirs[i]);
        if (!dirExists) {
          sftp.mkdir(dirs[i]);
          sftp.cd(dirs[i]);
        }

      }
    } catch (SftpException e) {
      iLogService.serviceLogError("mkDir Exception", e);
    }
  }

  /**
   * 打开指定目录
   *
   * @param directory directory
   * @return 是否打开目录
   */
  public boolean openDir(String directory) {
    try {
      sftp.cd(directory);
      return true;
    } catch (SftpException e) {
      iLogService.serviceLogError("openDir Exception", e);
      return false;
    }
  }

  public void rename(String oldPath, String newPath) {
    try {
      sftp.rename(oldPath, newPath);
    } catch (Exception e) {
      iLogService.serviceLogError("openDir Exception", e);
    }
  }

  @Override
  public UploadFileDTO uploadFile(InputStream in, String fileName, boolean needThumbnail)
      throws IOException {
    this.loadConfig();
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    byte[] buf = new byte[1024];
    int n = 0;
    while ((n = in.read(buf)) >= 0)
      baos.write(buf, 0, n);
    byte[] content = baos.toByteArray();
    InputStream fileIn = new ByteArrayInputStream(content);

    UploadFileDTO upd = new UploadFileDTO();
    String thumbnailImg = "";
    FileHelper fileNameReplace = new FileHelper();
    Map<String, String> objectMap = fileNameReplace.fileNameFormat(fileName);
    String mkDir = this.upload(uploadDirValue, fileIn, objectMap.get("fileName"));
    upd.setObjectUrl(
        outUrlPrefixValue.concat("/").concat(mkDir).concat("/").concat(objectMap.get("fileName")));
    if (needThumbnail) {
      InputStream fileThumbnail = new ByteArrayInputStream(content);
      // 生成缩略图
      thumbnailImg = objectMap.get("replaceStr") + objectMap.get("dateString").concat("_thumbnail")
          .concat(".").concat(objectMap.get("extName"));
      BufferedImage image =
          Thumbnails.of(fileThumbnail).scale(Float.parseFloat(thumbnailScaleValue))
              .outputQuality(Float.parseFloat(thumbnailOutputQualityValue)).asBufferedImage();
      System.out.print(image.getHeight());
      System.out.print(image.getWidth());
      ByteArrayOutputStream os = new ByteArrayOutputStream();
      ImageOutputStream imOut = ImageIO.createImageOutputStream(os);
      ImageIO.write(image, objectMap.get("extName"), imOut);
      InputStream thumbnailIn = new ByteArrayInputStream(os.toByteArray());
      this.upload(uploadDirValue, thumbnailIn, thumbnailImg);
      upd.setImgThumbnailUrl(outUrlPrefixValue.concat(thumbnailImg));
    }
    return upd;
  }

  @Override
  public UploadFileWithStreamDTO uploadFileWithStream(InputStream in, String fileName)
      throws IOException {
    this.loadConfig();
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    byte[] buf = new byte[1024];
    int n = 0;
    while ((n = in.read(buf)) >= 0)
      baos.write(buf, 0, n);
    byte[] content = baos.toByteArray();
    InputStream fileIn = new ByteArrayInputStream(content);

    UploadFileWithStreamDTO upd = new UploadFileWithStreamDTO();
    FileHelper fileNameReplace = new FileHelper();
    Map<String, String> objectMap = fileNameReplace.fileNameFormat(fileName);
    String mkDir = this.upload(uploadDirValue, fileIn, objectMap.get("fileName"));
    upd.setObjectUrl(
        outUrlPrefixValue.concat("/").concat(mkDir).concat("/").concat(objectMap.get("fileName")));
    upd.setObjectName(fileName);

    InputStream returnStream = new ByteArrayInputStream(content);
    upd.setFileInputStream(returnStream);

    String localPath = systemConfigService.getConfigValue("local.path");
    InputStream fileStream = new ByteArrayInputStream(content);

    String tmpFileName = this.saveLocal(localPath, fileStream, objectMap.get("fileName"));

    upd.setLocalPath(tmpFileName);

    fileStream.close();
    fileIn.close();
    return upd;
  }

  @Override
  public UploadFileDTO uploadFile(String directory, InputStream in, String fileName) {
    this.connect();
    SimpleDateFormat parser = new SimpleDateFormat("yyyyMMdd");
    String timeStamp = parser.format(new Date());
    String dir = directory + "/" + timeStamp;
    System.out.println(dir);
    try {
      sftp.cd(dir);
    } catch (Exception e) {
      try {
        sftp.mkdir(dir);
        sftp.cd(dir);
      } catch (SftpException e1) {
        e1.printStackTrace();
      }
    }
    try {
      sftp.put(in, fileName);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.disconnect();
    }
    UploadFileDTO upd = new UploadFileDTO();
    upd.setObjectUrl(dir.concat("/").concat(fileName));
    upd.setObjectName(fileName);
    System.out.println(upd.getObjectUrl());
    return upd;
  }
}
