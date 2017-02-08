package hmap.core.hms.message.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hmap.core.hms.message.domain.HmsMessageTemplate;
import hmap.core.hms.mapper.HmsMessageTemplateMapper;
import hmap.core.hms.message.service.IHmsMessageTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by shanhd on 2016/11/29.
 */
@Service
@Transactional
public class HmsMessageTemplateServiceImpl extends BaseServiceImpl<HmsMessageTemplate> implements IHmsMessageTemplateService {

    @Autowired
    private HmsMessageTemplateMapper hmsMessageTemplateMapper;

    @Override
    public List<HmsMessageTemplate> query(int page, int pageSize,IRequest iRequest) {

        PageHelper.startPage(page, pageSize);

        return self().selectAll(iRequest);
    }
}
