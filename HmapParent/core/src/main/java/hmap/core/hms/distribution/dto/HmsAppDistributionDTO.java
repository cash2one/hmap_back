/**
 * Copyright (c) 2017. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.hms.distribution.dto Date:2017/1/23 0023 Create
 * By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.distribution.dto;

import com.hand.hap.system.dto.BaseDTO;

public class HmsAppDistributionDTO extends BaseDTO {
  private String appId;
  private String appName;
  private String appIcon;
  private String appPlatform;
  private String appDescription;
  private String randomCode;
  private String qrCode;
  private String bundleName;
  private String packageName;
  private String lastestVersionId;
  private String lastestVersion;
  private String downloadUrl;
  private String plistUrl;
  private String appFile;
  private String appSize;
  private int buildNum;

  public String getAppId() {
    return appId;
  }

  public void setAppId(String appId) {
    this.appId = appId;
  }

  public String getAppName() {
    return appName;
  }

  public void setAppName(String appName) {
    this.appName = appName;
  }

  public String getAppIcon() {
    return appIcon;
  }

  public void setAppIcon(String appIcon) {
    this.appIcon = appIcon;
  }

  public String getAppPlatform() {
    return appPlatform;
  }

  public void setAppPlatform(String appPlatform) {
    this.appPlatform = appPlatform;
  }

  public String getAppDescription() {
    return appDescription;
  }

  public void setAppDescription(String appDescription) {
    this.appDescription = appDescription;
  }

  public String getRandomCode() {
    return randomCode;
  }

  public void setRandomCode(String randomCode) {
    this.randomCode = randomCode;
  }

  public String getQrCode() {
    return qrCode;
  }

  public void setQrCode(String qrCode) {
    this.qrCode = qrCode;
  }

  public String getBundleName() {
    return bundleName;
  }

  public void setBundleName(String bundleName) {
    this.bundleName = bundleName;
  }

  public String getPackageName() {
    return packageName;
  }

  public void setPackageName(String packageName) {
    this.packageName = packageName;
  }

  public String getLastestVersionId() {
    return lastestVersionId;
  }

  public void setLastestVersionId(String lastestVersionId) {
    this.lastestVersionId = lastestVersionId;
  }

  public String getLastestVersion() {
    return lastestVersion;
  }

  public void setLastestVersion(String lastestVersion) {
    this.lastestVersion = lastestVersion;
  }

  public String getDownloadUrl() {
    return downloadUrl;
  }

  public void setDownloadUrl(String downloadUrl) {
    this.downloadUrl = downloadUrl;
  }

  public String getAppFile() {
    return appFile;
  }

  public void setAppFile(String appFile) {
    this.appFile = appFile;
  }

  public String getAppSize() {
    return appSize;
  }

  public void setAppSize(String appSize) {
    this.appSize = appSize;
  }

  public int getBuildNum() {
    return buildNum;
  }

  public void setBuildNum(int buildNum) {
    this.buildNum = buildNum;
  }

  public String getPlistUrl() {
    return plistUrl;
  }

  public void setPlistUrl(String plistUrl) {
    this.plistUrl = plistUrl;
  }
}
