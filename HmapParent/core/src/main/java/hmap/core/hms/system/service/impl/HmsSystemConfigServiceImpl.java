/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.hms.service.impl Date:2016/9/22 0022 Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.system.service.impl;

import hmap.core.hms.system.exception.HmsSystemConfigException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.hand.hap.cache.CacheDelete;
import com.hand.hap.cache.CacheSet;
import com.hand.hap.cache.impl.HashStringRedisCache;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;

import hmap.core.hms.system.domain.HmsSystemConfig;
import hmap.core.hms.system.mapper.HmsSystemConfigMapper;
import hmap.core.hms.system.service.IHmsSystemConfigService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HmsSystemConfigServiceImpl extends BaseServiceImpl<HmsSystemConfig>
    implements IHmsSystemConfigService {
  @Autowired
  private HmsSystemConfigMapper hmsSystemConfigMapper;
  @Autowired
  @Qualifier("systemConfigCache")
  private HashStringRedisCache<HmsSystemConfig> systemConfigCache;

  @Override
  @CacheSet(cache = "systemConfig")
  public HmsSystemConfig insertSelective(IRequest request, HmsSystemConfig config) {
    super.insertSelective(request, config);

    return config;
  }

  @Override
  @CacheDelete(cache = "systemConfig")
  public int deleteByPrimaryKey(HmsSystemConfig config) {
    return super.deleteByPrimaryKey(config);
  }

  @Override
  @CacheSet(cache = "systemConfig")
  public HmsSystemConfig updateByPrimaryKeySelective(IRequest request, HmsSystemConfig config) {
    super.updateByPrimaryKeySelective(request, config);
//    systemConfigCache.setValue(config.getConfigKey(), config);
    return config;
  }

  @Override
  public HmsSystemConfig selectByConfigKey(String configKey) {
    HmsSystemConfig config = systemConfigCache.getValue(configKey);
    List<HmsSystemConfig> result=hmsSystemConfigMapper.selectByConfigKey(configKey);
    if (config == null&&result!=null&&result.size()>0) {
      config = result.get(0);
      if (config != null) {
        systemConfigCache.setValue(configKey, config);
      }
    }
    return config;
  }

  public void validateConfigKey(HmsSystemConfig config) throws HmsSystemConfigException {
    List<HmsSystemConfig> current = hmsSystemConfigMapper.selectByConfigKey(config.getConfigKey());
    if (current.size() >= 1) {
      throw new HmsSystemConfigException(HmsSystemConfigException.EXCEPTION_CODE,
          HmsSystemConfigException.CONFIGKEY_UNIQUE);
    }
  }

  @Override public String getConfigValue(String configKey) {
    HmsSystemConfig config=this.selectByConfigKey(configKey);
    if(config==null){
      return null;
    }
    else{
      return config.getConfigValue();
    }

  }
}
