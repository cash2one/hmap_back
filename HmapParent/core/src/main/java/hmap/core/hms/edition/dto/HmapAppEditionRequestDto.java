package hmap.core.hms.edition.dto;

/**
 * Created by enlline on 1/1/17.
 */
public class HmapAppEditionRequestDto {
    private String appId;
    private String appEquipment;
    private String appEditionCode;

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

    public String getAppEditionCode() {
        return appEditionCode;
    }

    public void setAppEditionCode(String appEditionCode) {
        this.appEditionCode = appEditionCode;
    }
}
