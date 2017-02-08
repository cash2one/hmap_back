package hmap.core.hms.feedback.service;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.IBaseService;
import hmap.core.hms.feedback.domain.HmsFeedback;
import hmap.core.hms.feedback.dto.HmsFeedbackDTO;


import java.util.List;


/**
 * Created by maoyuhuan on 2016/11/4.
 */
public interface IHmsFeedbackService extends IBaseService<HmsFeedback>, ProxySelf<IHmsFeedbackService> {
    List<HmsFeedbackDTO> selectFeedback(IRequest requestContext, HmsFeedbackDTO hmsFeedbackDTO, int page, int pagesize);
    List<HmsFeedbackDTO> selectNotReply(IRequest requestContext, HmsFeedbackDTO hmsFeedbackDTO);
    List<HmsFeedbackDTO> selectNotReply(IRequest requestContext, HmsFeedbackDTO hmsFeedbackDTO, int page, int pagesize);
    void updateFeedback();
    public List<HmsFeedback> selectFeedbackByKey(IRequest requestContext,String key);
}
