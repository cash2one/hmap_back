/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved.
 * Project Name:HmapParent
 * Package Name:hmap.core.hms.service 
 * Date:2016/11/8 0008
 * Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.system.service;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;

import hmap.core.hms.system.domain.HmsLoginError;

public interface IHmsLoginErrorService extends
    IBaseService<HmsLoginError>,ProxySelf<IHmsLoginErrorService> {
    HmsLoginError selectByUserIp(String userIp);
}
