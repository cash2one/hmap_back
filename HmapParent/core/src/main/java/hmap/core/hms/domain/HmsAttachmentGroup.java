package hmap.core.hms.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by shanhd on 2016/10/20.
 */
@Table(name = "hms_attachment_group")
public class HmsAttachmentGroup {

    @Id
    @GeneratedValue(
            generator = "UUID"
    )
    String id;
    String groupName;
    String enableFlag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(String enableFlag) {
        this.enableFlag = enableFlag;
    }
}
