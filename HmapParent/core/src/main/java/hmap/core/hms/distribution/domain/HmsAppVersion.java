/**
 * Copyright (c) 2017. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.hms.distribution.domain Date:2017/1/23 0023 Create
 * By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.distribution.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class HmsAppVersion {
  @Id
  @GeneratedValue(generator = "UUID")
  private String id;
  private String appId;
  private String version;
  private String appSize;
  private int downloadTimes;
  private String downloadUrl;
  private String qrCode;
  private int buildNum;
  private String isDisplay;
  private String isCurrentVersion;
  private String versionKey;
  private String plistUrl;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getAppId() {
    return appId;
  }

  public void setAppId(String appId) {
    this.appId = appId;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getAppSize() {
    return appSize;
  }

  public void setAppSize(String appSize) {
    this.appSize = appSize;
  }

  public int getDownloadTimes() {
    return downloadTimes;
  }

  public void setDownloadTimes(int downloadTimes) {
    this.downloadTimes = downloadTimes;
  }

  public String getDownloadUrl() {
    return downloadUrl;
  }

  public void setDownloadUrl(String downloadUrl) {
    this.downloadUrl = downloadUrl;
  }

  public String getQrCode() {
    return qrCode;
  }

  public void setQrCode(String qrCode) {
    this.qrCode = qrCode;
  }

  public int getBuildNum() {
    return buildNum;
  }

  public void setBuildNum(int buildNum) {
    this.buildNum = buildNum;
  }

  public String getIsDisplay() {
    return isDisplay;
  }

  public void setIsDisplay(String isDisplay) {
    this.isDisplay = isDisplay;
  }

  public String getIsCurrentVersion() {
    return isCurrentVersion;
  }

  public void setIsCurrentVersion(String isCurrentVersion) {
    this.isCurrentVersion = isCurrentVersion;
  }

  public String getVersionKey() {
    return versionKey;
  }

  public void setVersionKey(String versionKey) {
    this.versionKey = versionKey;
  }

  public String getPlistUrl() {
    return plistUrl;
  }

  public void setPlistUrl(String plistUrl) {
    this.plistUrl = plistUrl;
  }
}
