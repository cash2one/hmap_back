package hmap.core.hms.domain;

import com.hand.hap.system.dto.BaseDTO;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by machenike on 2016/9/23.
 */
@Table(name = "hms_wx_syn")
public class HmsWxSyn extends BaseDTO{
    @Id
    private String id;
    private String type;
    private String operation;
    private String sourceId;
    private Long tryTime;
    private String status;
    private String errorMessage;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public Long getTryTime() {
        return tryTime;
    }

    public void setTryTime(Long tryTime) {
        this.tryTime = tryTime;
    }
}
