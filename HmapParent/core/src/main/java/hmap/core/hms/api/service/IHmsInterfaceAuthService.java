package hmap.core.hms.api.service;

import com.hand.hap.account.exception.UserException;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hmap.core.hms.api.domain.HmsInterfaceAuth;
import hmap.core.hms.api.dto.HmsInterfaceAuthDTO;

import java.util.List;

/**
 * Created by USER on 2016/8/25.
 */
public interface IHmsInterfaceAuthService extends
        IBaseService<HmsInterfaceAuth>,ProxySelf<IHmsInterfaceAuthService> {
    public List<HmsInterfaceAuthDTO> selectAuthByInterfaceHeaderId(String interfaceHeaderId) throws UserException;
    public List<HmsInterfaceAuthDTO> selectAuthByInterfaceLineId(String interfaceLineId) throws UserException;
//    public List<HmsInterfaceAuthDTO> selectAuthByDomainUrl(String domainUrl);
//    public List<HmsInterfaceAuthDTO> selectAuthByIftUrl(String iftUrl);
    public HmsInterfaceAuthDTO getAuthByAppidAndSystemName(String appid,String sysName);
    public HmsInterfaceAuthDTO getAuthByAppidAndApiName(String appid,String sysName,String apiName);
    public HmsInterfaceAuthDTO getAuthByAppidAndApiUrl(String appid,String sysName,String itfUrl);
}
