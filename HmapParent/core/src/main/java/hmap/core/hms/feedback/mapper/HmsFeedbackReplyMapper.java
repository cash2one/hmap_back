package hmap.core.hms.feedback.mapper;

import com.hand.hap.account.dto.User;
import com.hand.hap.mybatis.common.Mapper;
import hmap.core.hms.feedback.domain.HmsFeedbackReply;
import hmap.core.hms.feedback.dto.HmsFeedbackReplyDTO;

import java.util.List;

/**
 * Created by maoyuhuan on 2016/11/8.
 */
public interface HmsFeedbackReplyMapper extends Mapper<HmsFeedbackReply>{
    List<HmsFeedbackReplyDTO> selectReplyByFeedbackId(String feedbackId);
    User selectUserIdByUserName(String userName);
    void reply(HmsFeedbackReply hmsFeedbackReply);
}
