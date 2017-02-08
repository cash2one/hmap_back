package hmap.core.hms.dto;

/**
 * Created by enlline on 12/30/16.
 */
public class HmsAppInfoDto {
    private String id;
    private String appId;
    private String appEquipment; //平台
    private String enableFlag;
    private String appHomepage;
    private String appName;
    private String downloadUrl;

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

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
