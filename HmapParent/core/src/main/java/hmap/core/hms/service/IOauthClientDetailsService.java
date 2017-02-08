package hmap.core.hms.service;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hmap.core.hms.domain.OauthClientDetails;

/**
 * Created by xincai.zhang on 2016/8/15.
 */
public interface IOauthClientDetailsService extends
        IBaseService<OauthClientDetails>, ProxySelf<IOauthClientDetailsService> {
}
