package hmap.core.hms.feedback.dto;

import com.hand.hap.system.dto.BaseDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by maoyuhuan on 2016/11/7.
 */
public class HmsFeedbackDTO extends BaseDTO{
    private String startDate;
    private String endDate;
    private Long replyFlag;
    private String feedbackType;
    private String userName;
    private String userType;
    private String feedbackId;
    private Long userId;
    private String feedbackData;
    private String feedbackDate;
    private int count;
    private String accessToken;
    private String replyData;
    private List<HmsFeedbackReplyDTO> hmsFeedbackReplyDTOs;
    private int page=1;
    private int pageSize=10;
    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }


    public String getReplyData() {
        return replyData;
    }

    public void setReplyData(String replyData) {
        this.replyData = replyData;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }



    public List<HmsFeedbackReplyDTO> getHmsFeedbackReplyDTOs() {
        return hmsFeedbackReplyDTOs;
    }

    public void setHmsFeedbackReplyDTOs(List<HmsFeedbackReplyDTO> hmsFeedbackReplyDTOs) {
        this.hmsFeedbackReplyDTOs = hmsFeedbackReplyDTOs;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }



    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        if(startDate != null && startDate !="") {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                String date = startDate;
                date = date.replace("Z", " UTC");
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
                Date d = format.parse(date);
                String sdate =sdf.format(d);
                this.startDate = sdate;
            } catch (ParseException e) {

            }
        }else {
            this.startDate=startDate;
        }
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        if(endDate != null&&endDate != ""){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            String date = endDate;
            date = date.replace("Z", " UTC");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
            Date d = format.parse(date);
            String sdate = sdf.format(d);
            this.endDate = sdate;
        } catch (ParseException e){

        }
        }else {
            this.endDate = endDate;
        }


    }





}
