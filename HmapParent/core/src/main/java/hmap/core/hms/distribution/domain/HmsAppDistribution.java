package hmap.core.hms.distribution.domain;

import com.hand.hap.system.dto.BaseDTO;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Koma.Tshu on 2016/10/19.
 */
@Table(name = "hms_app_distribution")
public class HmsAppDistribution extends BaseDTO {
  @Id
  @GeneratedValue(generator = "UUID")
  private String appId;
  private String appName;
  private String appIcon;
  private String appPlatform;
  private String appDescription;
  private String randomCode;
  private String qrCode;
  private String bundleName;
  private String packageName;

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

  public String getAppDescription() {
    return appDescription;
  }

  public void setAppDescription(String appDescription) {
    this.appDescription = appDescription;
  }

  public String getAppPlatform() {
    return appPlatform;
  }

  public void setAppPlatform(String appPlatform) {
    this.appPlatform = appPlatform;
  }

  public String getAppIcon() {
    return appIcon;
  }

  public void setAppIcon(String appIcon) {
    this.appIcon = appIcon;
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
}
