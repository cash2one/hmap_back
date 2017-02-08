/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved. Project
 * Name:hstaffParent Package Name:hstaff.core.thirdparty.dto Date:2016/7/17 0017 Create
 * By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.system.domain;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hand.hap.system.dto.BaseDTO;

@Table(name = "hms_thirdparty_app")
public class HmsThirdpartyApp extends BaseDTO {
  @Id
  @GeneratedValue(generator = "UUID")
  private String id;
  private String appName;
  private String appCode;
  private String appDesc;
  private String appIcon;
  private String appKey;
  private String appSecret;
  private String freeFlag;
  private Date expiredDate;
  private String enableFlag;
  private String appHomepage;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getAppName() {
    return appName;
  }

  public void setAppName(String appName) {
    this.appName = appName;
  }

  public String getAppCode() {
    return appCode;
  }

  public void setAppCode(String appCode) {
    this.appCode = appCode;
  }

  public String getAppDesc() {
    return appDesc;
  }

  public void setAppDesc(String appDesc) {
    this.appDesc = appDesc;
  }

  public String getAppIcon() {
    return appIcon;
  }

  public void setAppIcon(String appIcon) {
    this.appIcon = appIcon;
  }

  public String getAppKey() {
    return appKey;
  }

  public void setAppKey(String appKey) {
    this.appKey = appKey;
  }

  public String getAppSecret() {
    return appSecret;
  }

  public void setAppSecret(String appSecret) {
    this.appSecret = appSecret;
  }

  public String getFreeFlag() {
    return freeFlag;
  }

  public void setFreeFlag(String freeFlag) {
    this.freeFlag = freeFlag;
  }

  public Date getExpiredDate() {
    return expiredDate;
  }

  public void setExpiredDate(Date expiredDate) {
    this.expiredDate = expiredDate;
  }

  public String getEnableFlag() {
    return enableFlag;
  }

  public void setEnableFlag(String enableFlag) {
    this.enableFlag = enableFlag;
  }

  public String getAppHomepage() {
    return appHomepage;
  }

  public void setAppHomepage(String appHomepage) {
    this.appHomepage = appHomepage;
  }
}
