package hmap.core.hms.api.service.impl;

import com.github.pagehelper.StringUtil;
import com.hand.hap.account.exception.UserException;
import com.hand.hap.cache.Cache;
import com.hand.hap.cache.impl.CacheManagerImpl;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hmap.core.hms.appcenter.domain.HmsAppAuth;
import hmap.core.hms.api.domain.HmsInterfaceAuth;
import hmap.core.hms.api.domain.HmsInterfaceHeader;
import hmap.core.hms.api.domain.HmsInterfaceLine;
import hmap.core.hms.api.dto.HmsInterfaceAuthDTO;
import hmap.core.hms.api.mapper.HmsInterfaceAuthMapper;
import hmap.core.hms.appcenter.service.IHmsAppAuthService;
import hmap.core.hms.api.service.IHmsHeaderService;
import hmap.core.hms.api.service.IHmsInterfaceAuthService;
import hmap.core.hms.api.service.IHmsLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by USER on 2016/8/25.
 */
@Service
public class HmsInterfaceAuthServiceImpl extends BaseServiceImpl<HmsInterfaceAuth>
    implements IHmsInterfaceAuthService {
  private final static String IN_SYSTEM_TYPE = "IN";
  private final static String OUT_SYSTEM_TYPE = "OUT";
  @Autowired
  IHmsHeaderService hmsHeaderService;
  @Autowired
  IHmsLineService hmsLineService;
  @Autowired
  IHmsAppAuthService hmsAppAuthService;
  @Autowired
  private HmsInterfaceAuthMapper hmsInterfaceAuthMapper;
  @Autowired
  private CacheManagerImpl cml;

  public List<HmsInterfaceAuthDTO> selectAuthByInterfaceHeaderId(String interfaceHeaderId)
      throws UserException {
    List<HmsInterfaceAuthDTO> list = hmsInterfaceAuthMapper.selectAuthByHeaderId(interfaceHeaderId);
    return list;
  }

  public List<HmsInterfaceAuthDTO> selectAuthByInterfaceLineId(String interfaceLineId)
      throws UserException {
    List<HmsInterfaceAuthDTO> list = hmsInterfaceAuthMapper.selectAuthByLineId(interfaceLineId);
    return list;
  }

  // public List<HmsInterfaceAuthDTO> selectAuthByDomainUrl(String domainUrl) {
  // List<HmsInterfaceAuthDTO> list = hmsInterfaceAuthMapper.selectAuthByDomainUrl(domainUrl);
  // return list;
  // }
  //
  // public List<HmsInterfaceAuthDTO> selectAuthByIftUrl(String iftUrl) {
  // List<HmsInterfaceAuthDTO> list = hmsInterfaceAuthMapper.selectAuthByIftUrl(iftUrl);
  // return list;
  // }

  public HmsInterfaceAuthDTO getAuthByAppidAndSystemName(String appid, String sysName) {
    // 先找缓存，如果缓存找不到找数据库，并放回到缓存中
    HmsInterfaceAuthDTO auth = this.getCache().getValue(appid.concat(sysName.toUpperCase()));
    if (auth == null) {
      auth = hmsInterfaceAuthMapper.selectHeadAuthByAppidAndSysName(appid, sysName.toUpperCase());
      if (auth != null) {
        this.getCache().setValue(auth.getAppId().concat(auth.getSysName().toUpperCase()), auth);
      }
    }
    return auth;
  }

  public HmsInterfaceAuthDTO getAuthByAppidAndApiName(String appid, String sysName,
      String apiName) {
    // 先找缓存，如果缓存找不到找数据库，并放回到缓存中
    HmsInterfaceAuthDTO auth =
        this.getCache().getValue(appid.concat(sysName.toUpperCase()).concat(apiName.toUpperCase()));
    if (auth == null) {
      auth = hmsInterfaceAuthMapper.selectLineAuthByAppidAndSysNameAndApiName(appid,
          sysName.toUpperCase(), apiName.toUpperCase());
      if (auth != null) {
        this.getCache().setValue(auth.getAppId().concat(auth.getSysName().toUpperCase())
            .concat(auth.getApiName().toUpperCase()), auth);
      }
    }
    return auth;
  }

  public HmsInterfaceAuthDTO getAuthByAppidAndApiUrl(String appid, String sysName, String itfUrl) {
    // 先找缓存，如果缓存找不到找数据库，并放回到缓存中
    HmsInterfaceAuthDTO auth =
        this.getCache().getValue(appid.concat(sysName.toUpperCase()).concat(itfUrl));
    if (auth == null) {
      auth = hmsInterfaceAuthMapper.selectLineAuthByAppidAndSysNameAndUrl(appid,
          sysName.toUpperCase(), itfUrl.toUpperCase());
      if (auth != null) {
        this.getCache().setValue(
            auth.getAppId().concat(auth.getSysName().toUpperCase()).concat(auth.getIftUrl()), auth);
      }
    }
    return auth;
  }

  @Override
  public HmsInterfaceAuth insert(IRequest request, HmsInterfaceAuth record) {
    super.insertSelective(request, record);
    // 首先需要把头授权数据插入到缓存中
    HmsInterfaceHeader interfaceHeader = new HmsInterfaceHeader();
    interfaceHeader.setHeaderId(record.getInterfaceHeaderId());
    interfaceHeader = hmsHeaderService.selectByPrimaryKey(request, interfaceHeader);
    HmsAppAuth appAuth = hmsAppAuthService.selectById(record.getAuthId());
    HmsInterfaceAuthDTO auth = new HmsInterfaceAuthDTO();
    auth.setAppId(appAuth.getAppId());
    auth.setSysName(interfaceHeader.getInterfaceCode());
    auth.setSystemType(interfaceHeader.getSystemType());

    this.getCache().setValue(auth.getAppId().concat(auth.getSysName().toUpperCase()), auth);

    if (StringUtil.isNotEmpty(record.getInterfaceLineId())) {
      // 在把行授权数据插入到缓存中
      HmsInterfaceLine interfaceLine = new HmsInterfaceLine();
      interfaceLine.setLineId(record.getInterfaceLineId());
      interfaceLine = hmsLineService.selectByPrimaryKey(request, interfaceLine);

      if (IN_SYSTEM_TYPE.equalsIgnoreCase(auth.getSystemType())) {
        // 区分系统类型，如果是内部，则使用url
        auth.setIftUrl(interfaceLine.getIftUrl());
        this.getCache().setValue(
            auth.getAppId().concat(auth.getSysName().toUpperCase()).concat(auth.getIftUrl()), auth);
      } else if (OUT_SYSTEM_TYPE.equalsIgnoreCase(auth.getSystemType())) {
        // 如果是外部则使用apiName
        auth.setApiName(interfaceLine.getLineCode());
        this.getCache().setValue(auth.getAppId().concat(auth.getSysName().toUpperCase())
            .concat(auth.getApiName().toUpperCase()), auth);
      }
    }

    return record;
  }

  public Cache<HmsInterfaceAuthDTO> getCache() {
    return cml.getCache("interfaceAuth");
  }
}
