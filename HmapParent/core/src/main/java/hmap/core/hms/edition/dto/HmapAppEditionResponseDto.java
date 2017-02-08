package hmap.core.hms.edition.dto;

/**
 * Created by enlline on 1/1/17.
 */
public class HmapAppEditionResponseDto {
        private String appName;
        private String appId;
        private String appHomepage;
        private String appEquipment;
        private String downloadUrl;
        private String editionCode;
        private String isMinimumEdition;
        private String isLatestEdition;
        private String updateMessage;
        private String updateType;
        private int orderNum;
        private String isForceUpdate;

    public String getIsForceUpdate() {
        return isForceUpdate;
    }

    public void setIsForceUpdate(String isForceUpdate) {
        this.isForceUpdate = isForceUpdate;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppHomepage() {
        return appHomepage;
    }

    public void setAppHomepage(String appHomepage) {
        this.appHomepage = appHomepage;
    }

    public String getAppEquipment() {
        return appEquipment;
    }

    public void setAppEquipment(String appEquipment) {
        this.appEquipment = appEquipment;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getEditionCode() {
        return editionCode;
    }

    public void setEditionCode(String editionCode) {
        this.editionCode = editionCode;
    }

    public String getIsMinimumEdition() {
        return isMinimumEdition;
    }

    public void setIsMinimumEdition(String isMinimumEdition) {
        this.isMinimumEdition = isMinimumEdition;
    }

    public String getIsLatestEdition() {
        return isLatestEdition;
    }

    public void setIsLatestEdition(String isLatestEdition) {
        this.isLatestEdition = isLatestEdition;
    }

    public String getUpdateMessage() {
        return updateMessage;
    }

    public void setUpdateMessage(String updateMessage) {
        this.updateMessage = updateMessage;
    }

    public String getUpdateType() {
        return updateType;
    }

    public void setUpdateType(String updateType) {
        this.updateType = updateType;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }
}
