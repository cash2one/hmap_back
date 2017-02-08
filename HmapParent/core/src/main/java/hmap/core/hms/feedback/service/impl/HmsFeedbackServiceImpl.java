package hmap.core.hms.feedback.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hmap.core.hms.feedback.domain.HmsFeedback;
import hmap.core.hms.feedback.dto.HmsFeedbackDTO;
import hmap.core.hms.feedback.mapper.HmsFeedbackMapper;
import hmap.core.hms.feedback.service.IHmsFeedbackService;
import hmap.core.search.FeedbackSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by maoyuhuan on 2016/11/4.
 */
@Service
public class HmsFeedbackServiceImpl extends BaseServiceImpl<HmsFeedback> implements IHmsFeedbackService {
  @Autowired
   private HmsFeedbackMapper hmsFeedbackMapper;

    @Autowired
    private FeedbackSearchRepository feedbackSearchRepository;

   @Override
   public List<HmsFeedbackDTO> selectFeedback(IRequest requestContext, HmsFeedbackDTO hmsFeedbackDTO, int page, int pagesize) {
      PageHelper.startPage(page, pagesize);
      if(hmsFeedbackDTO ==null){
         hmsFeedbackDTO = new HmsFeedbackDTO();
      }
      return hmsFeedbackMapper.selectFeedback(hmsFeedbackDTO);
   }


    @Override
    public List<HmsFeedbackDTO> selectNotReply(IRequest requestContext, HmsFeedbackDTO hmsFeedbackDTO, int page, int pagesize){
        PageHelper.startPage(page, pagesize);
        return hmsFeedbackMapper.selectNotReply(hmsFeedbackDTO);
    }

    @Override
    public List<HmsFeedbackDTO> selectNotReply(IRequest requestContext, HmsFeedbackDTO hmsFeedbackDTO){
        return hmsFeedbackMapper.selectNotReply(hmsFeedbackDTO);
    }

    @Override
    public void updateFeedback(){
        hmsFeedbackMapper.updateFeedback();
    }


    @Override
    public List<HmsFeedback> selectFeedbackByKey(IRequest requestContext,String key){
        List<HmsFeedback> feedbacks = feedbackSearchRepository.search(key);
        System.out.println(feedbacks);
        return feedbacks;
    }
    @PostConstruct
    public void init() {
        if (feedbackSearchRepository != null && feedbackSearchRepository.count() < 1) {
            List<HmsFeedback> allFeedback = this.selectAll();
            if (allFeedback != null && allFeedback.size() > 0) {
                feedbackSearchRepository.save(allFeedback);
            }
        }
    }
}
