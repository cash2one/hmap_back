package hmap.core.hms.feedback.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hmap.core.hms.feedback.domain.HmsFeedback;
import hmap.core.hms.feedback.dto.HmsFeedbackDTO;


import java.util.List;

/**
 * Created by maoyuhuan on 2016/11/4.
 */
public interface HmsFeedbackMapper extends Mapper<HmsFeedback> {
    List<HmsFeedbackDTO> selectFeedback(HmsFeedbackDTO hmsFeedbackDTO);
    List<HmsFeedbackDTO> selectNotReply(HmsFeedbackDTO hmsFeedbackDTO);
    void updateFeedback();
}
