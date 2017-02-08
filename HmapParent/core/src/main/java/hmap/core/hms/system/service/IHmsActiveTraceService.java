package hmap.core.hms.system.service;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hmap.core.hms.system.dto.HmsActiveTraceDTO;

/**
 * Created by machenike on 2016/10/21.
 */
public interface IHmsActiveTraceService extends IBaseService<HmsActiveTraceDTO>, ProxySelf<IHmsActiveTraceService> {

    public void countRequest(String userName);
}
