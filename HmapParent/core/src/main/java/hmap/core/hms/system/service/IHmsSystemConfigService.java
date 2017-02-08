/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved.
 * Project Name:HmapParent
 * Package Name:hmap.core.hms.service 
 * Date:2016/9/22 0022
 * Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.system.service;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;

import hmap.core.hms.system.domain.HmsSystemConfig;
import hmap.core.hms.system.exception.HmsSystemConfigException;

public interface IHmsSystemConfigService extends IBaseService<HmsSystemConfig>,
    ProxySelf<IHmsSystemConfigService> {
    public  HmsSystemConfig selectByConfigKey(String configKey);
    public void validateConfigKey(HmsSystemConfig config) throws HmsSystemConfigException;
    String getConfigValue(String configKey);
}
