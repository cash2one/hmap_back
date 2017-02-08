package hmap.core.hms.api.domain;

import com.hand.hap.system.dto.BaseDTO;
import com.sun.istack.NotNull;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Koma.Tshu on 2016/8/25.
 */

@Table(name = "hms_interface_auth")
public class HmsInterfaceAuth extends BaseDTO{
    @Id
    @Column
    private String interfaceAuthId;

    @Column
    @NotNull
    private String authId;

    @Column
    private String interfaceHeaderId;

    @Column
    private String interfaceLineId;
    @Column
    private String enableFlag;

    public String getInterfaceAuthId() {
        return interfaceAuthId;
    }

    public void setInterfaceAuthId(String interfaceAuthId) {
        this.interfaceAuthId = interfaceAuthId;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
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

    public String getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(String enableFlag) {
        this.enableFlag = enableFlag;
    }
}
