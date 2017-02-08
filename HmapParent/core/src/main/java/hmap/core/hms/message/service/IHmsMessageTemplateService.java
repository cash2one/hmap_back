package hmap.core.hms.message.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hmap.core.hms.message.domain.HmsMessageTemplate;

import java.util.List;

/**
 * Created by shanhd on 2016/11/29.
 */
public interface IHmsMessageTemplateService extends IBaseService<HmsMessageTemplate>,ProxySelf<IHmsMessageTemplateService> {

    List<HmsMessageTemplate> query(int page,int pageSize,IRequest iRequest);

}
