package hmap.core.hms.domain;

import com.hand.hap.system.dto.BaseDTO;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by machenike on 2016/9/12.
 */
@Table(name = "hms_wx_token")
public class HmsWxToken extends BaseDTO{
    @Id
    @GeneratedValue(generator = "UUID")
    private String id;
    private String appId;
    private String accessToken;


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

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }


}
