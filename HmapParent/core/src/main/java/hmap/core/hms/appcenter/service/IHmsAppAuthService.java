package hmap.core.hms.appcenter.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hmap.core.hms.appcenter.domain.HmsAppAuth;

/**
 * Created by xincai.zhang on 2016/8/14.
 */
public interface IHmsAppAuthService
    extends IBaseService<HmsAppAuth>, ProxySelf<IHmsAppAuthService> {
  public HmsAppAuth selectById(String id);

  public HmsAppAuth insertOrUpdate(IRequest iRequest, HmsAppAuth hmsAppAuth);
}
