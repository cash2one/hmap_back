/**
 * Copyright (c) 2017. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.hms.dto Date:2017/1/12 0012 Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.uploader.dto;

import java.io.InputStream;

public class UploadFileWithStreamDTO {
  private String objectUrl;
  private InputStream fileInputStream;
  private String objectName;
  private String localPath;

  public String getObjectUrl() {
    return objectUrl;
  }

  public void setObjectUrl(String objectUrl) {
    this.objectUrl = objectUrl;
  }

  public InputStream getFileInputStream() {
    return fileInputStream;
  }

  public void setFileInputStream(InputStream fileInputStream) {
    this.fileInputStream = fileInputStream;
  }

  public String getObjectName() {
    return objectName;
  }

  public void setObjectName(String objectName) {
    this.objectName = objectName;
  }

  public String getLocalPath() {
    return localPath;
  }

  public void setLocalPath(String localPath) {
    this.localPath = localPath;
  }
}
