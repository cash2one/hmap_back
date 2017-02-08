package hmap.core.hms.feedback.domain;


import com.hand.hap.system.dto.BaseDTO;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by maoyuhuan on 2016/11/4.
 */
@Table(name="hms_feedback")
@Document(indexName = "hms_feedback", type = "hms_feedback", shards = 1, replicas = 0, refreshInterval = "-1")
public class HmsFeedback{
    @Id
    @GeneratedValue(generator = "UUID")
    @org.springframework.data.annotation.Id
//    @Field(type = FieldType.String, store = true,index = FieldIndex.not_analyzed)
    private String feedbackId;
//    @Field(type = FieldType.Long, store = true, index = FieldIndex.not_analyzed)
    private Long userId;
    @Field(type = FieldType.String, store = true, analyzer = "standard")
    private String feedbackData;
//    @Field(type = FieldType.String, store = true, index = FieldIndex.not_analyzed)
    private String feedbackDate;
//    @Field(type = FieldType.Long, store = true, index = FieldIndex.not_analyzed)
    private Long replyFlag;
//    @Field(type = FieldType.String, store = true, index = FieldIndex.not_analyzed)
    private String feedbackType;
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

    public String getFeedbackData() {
        return feedbackData;
    }

    public void setFeedbackData(String feedbackData) {
        this.feedbackData = feedbackData;
    }

    public String getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(String feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    public Long getReplyFlag() {
        return replyFlag;
    }

    public void setReplyFlag(Long replyFlag) {
        this.replyFlag = replyFlag;
    }

    public String getFeedbackType() {
        return feedbackType;
    }

    public void setFeedbackType(String feedbackType) {
        this.feedbackType = feedbackType;
    }


}
