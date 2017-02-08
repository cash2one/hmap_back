package hmap.core.hms.feedback.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by maoyuhuan on 2016/11/4.
 */
@Table(name="hms_feedback_reply")
public class HmsFeedbackReply {


    @Id
    @Column
    @GeneratedValue(generator = "UUID")
    private String replyId;

    private String feedbackId;
    private Long userId;
    private String replyData;
    private String replyDate;
    private Long replyFlag;

    public String getReplyId() {
        return replyId;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId;
    }

    public void setReplyFlag(Long replyFlag) {
        this.replyFlag = replyFlag;
    }
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

    public String getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(String replyDate) {
        this.replyDate = replyDate;
    }

    public Long getReplyFlag() {
        return replyFlag;
    }


}
