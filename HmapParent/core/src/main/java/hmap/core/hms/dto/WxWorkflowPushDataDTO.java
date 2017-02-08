package hmap.core.hms.dto;

import java.util.Date;

/**
 * Created by machenike on 2016/9/21.
 */
public class WxWorkflowPushDataDTO {
    private Integer id;
    private Date dateCreated;
    private Date lastUpdated;
    private Long createdBy;
    private Long updatedBy;

    private String sourceInstanceId;
    private String sourceNodeId;
    private String sourceRecordId;
    private String sourceWorkflowId;
    private String statusWx;
    private String statusApp;
    private String sourceCode;
    private String wxErrMsg;
    private String appErrMsg;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getSourceInstanceId() {
        return sourceInstanceId;
    }

    public void setSourceInstanceId(String sourceInstanceId) {
        this.sourceInstanceId = sourceInstanceId;
    }

    public String getSourceNodeId() {
        return sourceNodeId;
    }

    public void setSourceNodeId(String sourceNodeId) {
        this.sourceNodeId = sourceNodeId;
    }

    public String getSourceRecordId() {
        return sourceRecordId;
    }

    public void setSourceRecordId(String sourceRecordId) {
        this.sourceRecordId = sourceRecordId;
    }

    public String getSourceWorkflowId() {
        return sourceWorkflowId;
    }

    public void setSourceWorkflowId(String sourceWorkflowId) {
        this.sourceWorkflowId = sourceWorkflowId;
    }

    public String getStatusWx() {
        return statusWx;
    }

    public void setStatusWx(String statusWx) {
        this.statusWx = statusWx;
    }

    public String getStatusApp() {
        return statusApp;
    }

    public void setStatusApp(String statusApp) {
        this.statusApp = statusApp;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public String getWxErrMsg() {
        return wxErrMsg;
    }

    public void setWxErrMsg(String wxErrMsg) {
        this.wxErrMsg = wxErrMsg;
    }

    public String getAppErrMsg() {
        return appErrMsg;
    }

    public void setAppErrMsg(String appErrMsg) {
        this.appErrMsg = appErrMsg;
    }
}
