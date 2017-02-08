package hmap.core.hms.device.domain;

import com.hand.hap.system.dto.BaseDTO;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.crypto.Data;
import java.util.Date;

/**
 * Created by liangting on 2016/10/21 0021.
 */
@Table(name = "hms_device")
public class HmsDevice extends BaseDTO {

    @Id
    @GeneratedValue(generator = "UUID")
    private String deviceId;
    private String deviceBrand;
    private String deviceType;
    private String operationSystem;
    private String operationSystemVersion;
    private String clientVersion;
    private String deviceUser;
    private String deviceFlag;
    private String ime;
    private String width;
    private String height;
    private String pixelRatio;
    private Date creationDate;


    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceBrand() {
        return deviceBrand;
    }

    public void setDeviceBrand(String deviceBrand) {
        this.deviceBrand = deviceBrand;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getOperationSystem() {
        return operationSystem;
    }

    public void setOperationSystem(String operationSystem) {
        this.operationSystem = operationSystem;
    }

    public String getOperationSystemVersion() {
        return operationSystemVersion;
    }

    public void setOperationSystemVersion(String operationSystemVersion) {
        this.operationSystemVersion = operationSystemVersion;
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }

    public String getDeviceUser() {
        return deviceUser;
    }

    public void setDeviceUser(String deviceUser) {
        this.deviceUser = deviceUser;
    }

    public String getDeviceFlag() {
        return deviceFlag;
    }

    public void setDeviceFlag(String deviceFlag) {
        this.deviceFlag = deviceFlag;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getPixelRatio() {
        return pixelRatio;
    }

    public void setPixelRatio(String pixelRatio) {
        this.pixelRatio = pixelRatio;
    }

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "HmsDevice{" +
                "deviceId='" + deviceId + '\'' +
                ", deviceBrand='" + deviceBrand + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", operationSystem='" + operationSystem + '\'' +
                ", operationSystemVersion='" + operationSystemVersion + '\'' +
                ", clientVersion='" + clientVersion + '\'' +
                ", deviceUser='" + deviceUser + '\'' +
                ", deviceFlag='" + deviceFlag + '\'' +
                ", ime='" + ime + '\'' +
                ", width='" + width + '\'' +
                ", height='" + height + '\'' +
                ", pixelRatio='" + pixelRatio + '\'' +
                ", creationDate='" + creationDate + '\'' +
                '}';
    }
}
