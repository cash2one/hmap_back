package hmap.core.hms.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by hand on 2016/12/07.
 */
@Table(name = "hms_content")
public class HmsContent {
    @Id
    @Column
    @GeneratedValue(generator = "UUID")
    private String id;
    @Column
    private String contentTitle;
    @Column
    private String contentType;
    @Column
    private String contentAuthor;
    @Column
    private String contentBody;
    @Column
    private String contentCover;
    @Column
    private String contentSubject;
    @Column
    private String contentStatus;
    @Column
    private String templateId;
    @Column
    private int maximumPerson;
    @Column
    private String creationDate;
    @Column
    private String createdBy;
    @Column
    private String lastUpdatedBy;
    @Column
    private String lastUpdateDate;
    @Column
    private String replyFlag;
    @Column
    private String securityFlag;
    @Column
    private String sharedFlag;
    @Column
    private int version;
    @Column
    private String enrollFlag;
    @Column
    private String enrollEndDate;

    @Column
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContentTitle() {
        return contentTitle;
    }

    public void setContentTitle(String contentTitle) {
        this.contentTitle = contentTitle;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentAuthor() {
        return contentAuthor;
    }

    public void setContentAuthor(String contentAuthor) {
        this.contentAuthor = contentAuthor;
    }

    public String getContentBody() {
        return contentBody;
    }

    public void setContentBody(String contentBody) {
        this.contentBody = contentBody;
    }

    public String getContentCover() {
        return contentCover;
    }

    public void setContentCover(String contentCover) {
        this.contentCover = contentCover;
    }

    public String getContentSubject() {
        return contentSubject;
    }

    public void setContentSubject(String contentSubject) {
        this.contentSubject = contentSubject;
    }

    public String getContentStatus() {
        return contentStatus;
    }

    public void setContentStatus(String contentStatus) {
        this.contentStatus = contentStatus;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public int getMaximumPerson() {
        return maximumPerson;
    }

    public void setMaximumPerson(int maximumPerson) {
        this.maximumPerson = maximumPerson;
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

    public String getReplyFlag() {
        return replyFlag;
    }

    public void setReplyFlag(String replyFlag) {
        this.replyFlag = replyFlag;
    }

    public String getSecurityFlag() {
        return securityFlag;
    }

    public void setSecurityFlag(String securityFlag) {
        this.securityFlag = securityFlag;
    }

    public String getSharedFlag() {
        return sharedFlag;
    }

    public void setSharedFlag(String sharedFlag) {
        this.sharedFlag = sharedFlag;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getEnrollFlag() {
        return enrollFlag;
    }

    public void setEnrollFlag(String enrollFlag) {
        this.enrollFlag = enrollFlag;
    }

    public String getEnrollEndDate() {
        return enrollEndDate;
    }

    public void setEnrollEndDate(String enrollEndDate) {
        this.enrollEndDate = enrollEndDate;
    }


}
