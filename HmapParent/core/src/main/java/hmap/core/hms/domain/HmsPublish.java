package hmap.core.hms.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by hand on 2016/12/12.
 */
@Table(name="hms_publish")
public class HmsPublish {
    @Id
    @GeneratedValue(generator = "UUID")
    private String id;
    private String contentId;
    private String publishDate;
    private String publishUrl;
    private String publishTitle;
    private String publishCover;
    private String publishFile;
    private String publishSubject;
    private int maximumPerson;
    private String broadcastStatus;
    private String creationDate;
    private String createdBy;
    private String lastUpdatedBy;
    private String lastUpdateDate;
    private int version;
    private int viewCnt;
    private int replyCnt;
    private int likeCnt;
    private String enrollEndDate;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getPublishUrl() {
        return publishUrl;
    }

    public void setPublishUrl(String publishUrl) {
        this.publishUrl = publishUrl;
    }

    public String getPublishTitle() {
        return publishTitle;
    }

    public void setPublishTitle(String publishTitle) {
        this.publishTitle = publishTitle;
    }

    public String getPublishCover() {
        return publishCover;
    }

    public void setPublishCover(String publishCover) {
        this.publishCover = publishCover;
    }

    public String getPublishFile() {
        return publishFile;
    }

    public void setPublishFile(String publishFile) {
        this.publishFile = publishFile;
    }

    public String getPublishSubject() {
        return publishSubject;
    }

    public void setPublishSubject(String publishSubject) {
        this.publishSubject = publishSubject;
    }

    public int getMaximumPerson() {
        return maximumPerson;
    }

    public void setMaximumPerson(int maximumPerson) {
        this.maximumPerson = maximumPerson;
    }

    public String getBroadcastStatus() {
        return broadcastStatus;
    }

    public void setBroadcastStatus(String broadcastStatus) {
        this.broadcastStatus = broadcastStatus;
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

    public int getViewCnt() {
        return viewCnt;
    }

    public void setViewCnt(int viewCnt) {
        this.viewCnt = viewCnt;
    }

    public int getReplyCnt() {
        return replyCnt;
    }

    public void setReplyCnt(int replyCnt) {
        this.replyCnt = replyCnt;
    }

    public int getLikeCnt() {
        return likeCnt;
    }

    public void setLikeCnt(int likeCnt) {
        this.likeCnt = likeCnt;
    }

    public String getEnrollEndDate() {
        return enrollEndDate;
    }

    public void setEnrollEndDate(String enrollEndDate) {
        this.enrollEndDate = enrollEndDate;
    }


}
