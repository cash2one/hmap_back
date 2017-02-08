package hmap.core.hms.api.dto;

import com.hand.hap.system.dto.BaseDTO;

import java.util.Date;

/**
 * Created by Koma.Tshu on 2016/8/24.
 */
public class HmsInterfaceAuthDTO extends BaseDTO {
    // HmsInterfaceAuth
    private String interfaceAuthId;
    private String interfaceHeaderId;
    private String interfaceLineId;
    private String authId;
    private Date creationDate;

    //HmsInterfaceHeader
    private String sysName;
    private String systemType;

    //HmsInterfaceLine
    private String apiName;
    private String iftUrl;

    //HmsAppAuth
    private String appName;
    private String appId;

    public String getInterfaceAuthId() {
        return interfaceAuthId;
    }

    public void setInterfaceAuthId(String interfaceAuthId) {
        this.interfaceAuthId = interfaceAuthId;
    }

    public String getInterfaceHeaderId() {
        return interfaceHeaderId;
    }

    public void setInterfaceHeaderId(String interfaceHeaderId) {
        this.interfaceHeaderId = interfaceHeaderId;
    }

    public String getInterfaceLineId() {
        return interfaceLineId;
    }

    public void setInterfaceLineId(String interfaceLineId) {
        this.interfaceLineId = interfaceLineId;
    }

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getIftUrl() {
        return iftUrl;
    }

    public void setIftUrl(String iftUrl) {
        this.iftUrl = iftUrl;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSystemType() {
        return systemType;
    }

    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }

}
