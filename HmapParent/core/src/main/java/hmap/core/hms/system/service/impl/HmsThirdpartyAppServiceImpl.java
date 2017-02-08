/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.hms.service.impl Date:2016/7/31 0031 Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.system.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;

import hmap.core.hms.system.domain.HmsThirdpartyApp;
import hmap.core.hms.system.mapper.HmsThirdpartyAppMapper;
import hmap.core.hms.system.service.IHmsThirdpartyAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HmsThirdpartyAppServiceImpl extends BaseServiceImpl<HmsThirdpartyApp>
    implements IHmsThirdpartyAppService {
  @Autowired
  private HmsThirdpartyAppMapper thirdpartyAppMapper;

  public HmsThirdpartyApp selectById(String id) {
//    if(true){
//      throw new UserException(UserException.ERROR_USER_PASSWORD,null);
//    }
    return thirdpartyAppMapper.selectById(id);
  }

  @Override public HmsThirdpartyApp selectByAppKey(String appKey) {
    return  thirdpartyAppMapper.selectByAppKey(appKey);
  }
}
