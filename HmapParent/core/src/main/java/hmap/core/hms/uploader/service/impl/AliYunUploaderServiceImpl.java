/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.hms.service.impl Date:2016/8/4 0004 Create By:zongyun.zhou@hand-china.com
 */
package hmap.core.hms.uploader.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;

import hmap.core.hms.uploader.dto.UploadFileDTO;
import hmap.core.hms.uploader.dto.UploadFileWithStreamDTO;
import hmap.core.hms.system.service.IHmsSystemConfigService;
import hmap.core.util.FileHelper;
import net.coobird.thumbnailator.Thumbnails;


public class AliYunUploaderServiceImpl extends AbstractUploaderServiceImpl {
  @Autowired
  IHmsSystemConfigService systemConfigService;
  private Logger logger = LoggerFactory.getLogger(this.getClass());

  private String endpoint = null;
  private String accessId = null;
  private String accessKey = null;
  private String bucket = null;
  private String host = null;
  private String thumbnailScale = null;
  private String thumbnailOutputQuality = null;
  private String dir = "img/";

  private void loadConfig() {
    endpoint = systemConfigService.selectByConfigKey("aliyun.endpoint").getConfigValue();
    accessId = systemConfigService.selectByConfigKey("aliyun.accessId").getConfigValue();
    accessKey = systemConfigService.selectByConfigKey("aliyun.accessKey").getConfigValue();
    bucket = systemConfigService.selectByConfigKey("aliyun.bucket").getConfigValue();
    host = systemConfigService.selectByConfigKey("aliyun.host").getConfigValue();
    thumbnailScale =
        systemConfigService.selectByConfigKey("aliyun.thumbnailScale").getConfigValue();
    thumbnailOutputQuality =
        systemConfigService.selectByConfigKey("aliyun.thumbnailOutputQuality").getConfigValue();
  }

  public OSSClient getOSSClient() {
    this.loadConfig();
    // 创建OSSClient实例
    OSSClient client = new OSSClient(endpoint, accessId, accessKey);
    return client;
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

    String thumbnailImg = "";
    UploadFileDTO upd = new UploadFileDTO();
    OSSClient client = this.getOSSClient();
    FileHelper fileNameReplace = new FileHelper();
    Map<String, String> objectMap = fileNameReplace.fileNameFormat(fileName);
    ObjectMetadata meta = new ObjectMetadata();
    meta.setContentLength(fileIn.available());
    client.putObject(new PutObjectRequest(bucket, objectMap.get("fileName"), fileIn));
    upd.setObjectUrl(host + "/" + objectMap.get("fileName"));

    if (needThumbnail) {
      InputStream fileThumbnail = new ByteArrayInputStream(content);
      // 生成缩略图
      thumbnailImg = objectMap.get("replaceStr") + objectMap.get("dateString").concat("_thumbnail")
          .concat(".").concat(objectMap.get("extName"));
      BufferedImage image = Thumbnails.of(fileThumbnail).scale(Float.parseFloat(thumbnailScale))
          .outputQuality(Float.parseFloat(thumbnailOutputQuality)).asBufferedImage();
      ByteArrayOutputStream os = new ByteArrayOutputStream();
      ImageOutputStream imOut = ImageIO.createImageOutputStream(os);
      ImageIO.write(image, objectMap.get("extName"), imOut);
      InputStream thumbnailIn = new ByteArrayInputStream(os.toByteArray());
      client.putObject(new PutObjectRequest(bucket, thumbnailImg, thumbnailIn));
      upd.setImgThumbnailUrl(host + "/" + thumbnailImg);
    }
    client.shutdown();
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
    OSSClient client = this.getOSSClient();
    FileHelper fileNameReplace = new FileHelper();
    Map<String, String> objectMap = fileNameReplace.fileNameFormat(fileName);
    ObjectMetadata meta = new ObjectMetadata();
    meta.setContentLength(fileIn.available());
    client.putObject(new PutObjectRequest(bucket, objectMap.get("fileName"), fileIn));
    upd.setObjectUrl(host + "/" + objectMap.get("fileName"));
    upd.setObjectName(fileName);
    InputStream returnStream = new ByteArrayInputStream(content);
    upd.setFileInputStream(returnStream);
    return upd;
  }

  @Override
  public UploadFileDTO uploadFile(String directory, InputStream in, String fileName) {
    return null;
  }

  @Override
  public String upload(String directory, InputStream in, String fileName) {
    return null;
  }

  @Override
  public void download(String downloadFile, String dst) {}
}
