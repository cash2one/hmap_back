/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.hms.service.impl Date:2016/8/12 0012 Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.permission.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hmap.core.hms.permission.domain.HmsApplicationPermission;

import java.util.List;

public interface IHmsApplicationPermissionService extends IBaseService<HmsApplicationPermission>,
        ProxySelf<IHmsApplicationPermissionService> {
    boolean checkPermission(String visibilityType, String accountNumber);
    List<HmsApplicationPermission> foungData(String dataId);
    boolean saveAll(IRequest iRequest,List<HmsApplicationPermission> ApplicationPermissions,String dataId);
}
