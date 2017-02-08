package hmap.core.hms.message.dto;

import com.hand.hap.system.dto.BaseDTO;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by machenike on 2016/10/27.
 */
@Table(name = "hms_message_type")
public class HmsMessageTypeDTO extends BaseDTO {
    @Id
    private String id;
    private String role;
    private String messageType;
    private String messageDesc;

    public String getMessageDesc() {
        return messageDesc;
    }

    public void setMessageDesc(String messageDesc) {
        this.messageDesc = messageDesc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    @Override
    public String toString() {
        return "HmsMessageTypeDTO{" +
                "id='" + id + '\'' +
                ", role='" + role + '\'' +
                ", messageType='" + messageType + '\'' +
                '}';
    }
}
