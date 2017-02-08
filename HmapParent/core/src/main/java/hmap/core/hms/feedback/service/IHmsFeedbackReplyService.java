package hmap.core.hms.feedback.service;

import com.hand.hap.account.dto.User;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hmap.core.hms.feedback.domain.HmsFeedbackReply;
import hmap.core.hms.feedback.dto.HmsFeedbackReplyDTO;

import java.util.List;

/**
 * Created by maoyuhuan on 2016/11/7.
 */
public interface IHmsFeedbackReplyService extends IBaseService<HmsFeedbackReply>, ProxySelf<IHmsFeedbackReplyService> {
    List<HmsFeedbackReplyDTO> selectReplyByFeedbackId(IRequest requestContext, String feedbackId, int page, int pagesize);
    User selectUserIdByUserName(String userName);
    void reply(HmsFeedbackReply hmsFeedbackReply);
}
