package hmap.core.hms.edition.domain;

import com.hand.hap.system.dto.BaseDTO;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by enlline on 12/30/16.
 */
@Table(name = "hms_app_edition_line")
public class HmsAppEditionLine extends BaseDTO {
    @Id
    @GeneratedValue(generator = "UUID")
    private String id;
    private String appEditionId;
    private int orderNum;
    private String updateType;
    private String editionCode;
    private String downloadUrl;
    private String isMinimumEdition;
    private String isLatestEdition;
    private String updateMessage;
    private String enableFlag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppEditionId() {
        return appEditionId;
    }

    public void setAppEditionId(String appEditionId) {
        this.appEditionId = appEditionId;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public String getUpdateType() {
        return updateType;
    }

    public void setUpdateType(String updateType) {
        this.updateType = updateType;
    }

    public String getEditionCode() {
        return editionCode;
    }

    public void setEditionCode(String editionCode) {
        this.editionCode = editionCode;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
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

    public String getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(String enableFlag) {
        this.enableFlag = enableFlag;
    }


}
