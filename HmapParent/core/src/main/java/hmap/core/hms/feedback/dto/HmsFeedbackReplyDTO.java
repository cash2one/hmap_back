package hmap.core.hms.feedback.dto;

import java.util.Date;
;

/**
 * Created by maoyuhuan on 2016/11/8.
 */
public class HmsFeedbackReplyDTO {

    private String feedbackId;
    private Long userId;
    private String replyData;
    private String replyDate;
    private Long replyFlag;
    private String userName;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    private String accessToken;

    public String getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(String feedbackId) {
        this.feedbackId = feedbackId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getReplyData() {
        return replyData;
    }

    public void setReplyData(String replyData) {
        this.replyData = replyData;
    }

    public String getReplyDate()
    {
        return replyDate;
    }

    public void setReplyDate(String replyDate) {
        this.replyDate = replyDate;
    }

    public Long getReplyFlag() {
        return replyFlag;
    }

    public void setReplyFlag(Long replyFlag) {
        this.replyFlag = replyFlag;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


}
