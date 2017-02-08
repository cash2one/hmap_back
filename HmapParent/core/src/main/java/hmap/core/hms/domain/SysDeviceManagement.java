package hmap.core.hms.domain;

import com.hand.hap.system.dto.BaseDTO;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by liangting on 2016/10/21 0021.
 */
@Table(name = "sys_device_management")
public class SysDeviceManagement extends BaseDTO {

    @Id
    @GeneratedValue(generator = "UUID")
    private String deviceId;
    private String deviceBrand;
    private String deviceType;
    private String openingSystem;
    private String deviceUser;
    private Date creationDate;
    private String deviceFlag;
    private String ime;



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

    public String getOpeningSystem() {
        return openingSystem;
    }

    public void setOpeningSystem(String openingSystem) {
        this.openingSystem = openingSystem;
    }

    public String getDeviceUser() {
        return deviceUser;
    }

    public void setDeviceUser(String deviceUser) {
        this.deviceUser = deviceUser;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getDeviceFlag() {
        return deviceFlag;
    }

    public void setDeviceFlag(String deviceFlag) {
        this.deviceFlag = deviceFlag;
    }
}
