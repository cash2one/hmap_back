package hmap.core.hms.distribution.service;

import java.util.List;

import com.hand.hap.account.exception.UserException;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hmap.core.hms.distribution.domain.HmsAppVersion;



/**
 * Created by Koma.Tshu on 2016/10/19.
 */
public interface IHmsAppVersionService extends
        IBaseService<HmsAppVersion>,ProxySelf<IHmsAppVersionService> {
    public HmsAppVersion selectCurrentVersion(String appId);
    public List<HmsAppVersion> selectAppVersionByAppId(String appId,int page,int pageSize);
}
