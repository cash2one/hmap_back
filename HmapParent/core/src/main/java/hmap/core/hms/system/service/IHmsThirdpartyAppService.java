/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved.
 * Project Name:HmapParent
 * Package Name:hmap.core.hms.service 
 * Date:2016/7/31 0031
 * Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.system.service;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hmap.core.hms.system.domain.HmsThirdpartyApp;

public interface IHmsThirdpartyAppService extends
    IBaseService<HmsThirdpartyApp>,ProxySelf<IHmsThirdpartyAppService> {
    public HmsThirdpartyApp selectById(String id);
    public HmsThirdpartyApp selectByAppKey(String appKey);
}
