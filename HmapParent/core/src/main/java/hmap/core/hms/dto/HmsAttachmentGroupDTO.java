package hmap.core.hms.dto;

/**
 * Created by shanhd on 2016/10/20.
 */
public class HmsAttachmentGroupDTO {

    String id;
    String groupName;
    int attachmentCount;

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

    public int getAttachmentCount() {
        return attachmentCount;
    }

    public void setAttachmentCount(int attachmentCount) {
        this.attachmentCount = attachmentCount;
    }
}
