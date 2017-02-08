package hmap.core.hms.dto;

/**
 * Created by machenike on 2016/9/21.
 */
public class WxWorkflowDetailDTO {
    private String approver;
    private String reciever;
    private String applier;
    private String title;
    private String description;
    private String apply_time;

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public String getReciever() {
        return reciever;
    }

    public void setReciever(String reciever) {
        this.reciever = reciever;
    }

    public String getApplier() {
        return applier;
    }

    public void setApplier(String applier) {
        this.applier = applier;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getApply_time() {
        return apply_time;
    }

    public void setApply_time(String apply_time) {
        this.apply_time = apply_time;
    }

    @Override
    public String toString() {
        return "WxWorkflowDetailDTO{" +
                "approver='" + approver + '\'' +
                ", reciever='" + reciever + '\'' +
                ", applier='" + applier + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", apply_time='" + apply_time + '\'' +
                '}';
    }
}
