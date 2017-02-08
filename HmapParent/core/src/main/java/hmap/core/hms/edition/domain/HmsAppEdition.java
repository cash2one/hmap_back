/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved.
 * Project Name:HmapParent
 * Package Name:hmap.core.hms.dto
 * Date:2016/8/3
 * Create By:lei.chen03@hand-china.com
 */
package hmap.core.hms.edition.domain;

import com.hand.hap.system.dto.BaseDTO;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "hms_app_edition")
public class HmsAppEdition extends BaseDTO {
  @Id
  @GeneratedValue(generator = "UUID")
  private String id;
  private String appId;
  private String appEquipment; //平台
  private String enableFlag;
  private String appHomepage;
  private String downloadUrl; //APP应用分享链接

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

    public String getAppEquipment() {
        return appEquipment;
    }

    public void setAppEquipment(String appEquipment) {
        this.appEquipment = appEquipment;
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

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
