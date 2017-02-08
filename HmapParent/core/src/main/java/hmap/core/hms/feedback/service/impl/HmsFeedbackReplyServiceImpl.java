package hmap.core.hms.feedback.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.account.dto.User;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hmap.core.hms.feedback.domain.HmsFeedbackReply;
import hmap.core.hms.feedback.dto.HmsFeedbackReplyDTO;
import hmap.core.hms.feedback.mapper.HmsFeedbackReplyMapper;
import hmap.core.hms.feedback.service.IHmsFeedbackReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by maoyuhuan on 2016/11/7.
 */
@Service
public class HmsFeedbackReplyServiceImpl extends BaseServiceImpl<HmsFeedbackReply> implements IHmsFeedbackReplyService {
    @Autowired
    HmsFeedbackReplyMapper hmsFeedbackReplyMapper;
    @Override
    public List<HmsFeedbackReplyDTO> selectReplyByFeedbackId(IRequest requestContext, String  feedbackId, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        return hmsFeedbackReplyMapper.selectReplyByFeedbackId(feedbackId);
    }

    @Override
    public User selectUserIdByUserName(String userName){
        return hmsFeedbackReplyMapper.selectUserIdByUserName(userName);
    }

    @Override
    public void reply(HmsFeedbackReply hmsFeedbackReply){
        hmsFeedbackReplyMapper.reply(hmsFeedbackReply);
    }
}
