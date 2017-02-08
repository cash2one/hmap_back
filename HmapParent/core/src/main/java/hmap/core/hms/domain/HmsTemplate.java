package hmap.core.hms.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by hand on 2016/12/12.
 */
@Table(name = "hms_template")
public class HmsTemplate {
    @Id
    @GeneratedValue(generator = "UUID")
    private String id;
    private String templateName;
    private String templateComment;
    private String templatePath;
    private String contentId;
    private Date startDate;
    private Date endDate;
    private String enableFlag;
    private String scheduleJobId;
    private String jobFlag;
    private int scheduleFrequency;
    private Date scheduleEndDate;
    private Date scheduleStartDate;
    private String creationDate;
    private String createdBy;
    private String lastUpdatedBy;
    private String lastUpdateDate;
    private int version;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateComment() {
        return templateComment;
    }

    public void setTemplateComment(String templateComment) {
        this.templateComment = templateComment;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(String enableFlag) {
        this.enableFlag = enableFlag;
    }

    public String getScheduleJobId() {
        return scheduleJobId;
    }

    public void setScheduleJobId(String scheduleJobId) {
        this.scheduleJobId = scheduleJobId;
    }

    public String getJobFlag() {
        return jobFlag;
    }

    public void setJobFlag(String jobFlag) {
        this.jobFlag = jobFlag;
    }

    public int getScheduleFrequency() {
        return scheduleFrequency;
    }

    public void setScheduleFrequency(int scheduleFrequency) {
        this.scheduleFrequency = scheduleFrequency;
    }

    public Date getScheduleEndDate() {
        return scheduleEndDate;
    }

    public void setScheduleEndDate(Date scheduleEndDate) {
        this.scheduleEndDate = scheduleEndDate;
    }

    public Date getScheduleStartDate() {
        return scheduleStartDate;
    }

    public void setScheduleStartDate(Date scheduleStartDate) {
        this.scheduleStartDate = scheduleStartDate;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }


}
