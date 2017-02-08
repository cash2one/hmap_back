package hmap.core.hms.appcenter.domain;

import com.hand.hap.system.dto.BaseDTO;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by xincai.zhang on 2016/8/14.
 */
@Table(name="hms_app_auth")
public class HmsAppAuth extends BaseDTO {
    @Id
    @GeneratedValue(generator = "UUID")
    private String id;
    private String userName;
    private String appName;
    private String appType;
    private String appId;
    private String appSecret;
    private String appIcon;
    private String appAuzMode;
//    private String updateShowPrompt;


//    public String getUpdateShowPrompt() {
//        return updateShowPrompt;
//    }
//
//    public void setUpdateShowPrompt(String updateShowPrompt) {
//        this.updateShowPrompt = updateShowPrompt;
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(String appIcon) {
        this.appIcon = appIcon;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAppAuzMode() {
        return appAuzMode;
    }

    public void setAppAuzMode(String appAuzMode) {
        this.appAuzMode = appAuzMode;
    }
}
