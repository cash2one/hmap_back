/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved.
 * Project Name:HmapParent
 * Package Name:hmap.core.hms.service 
 * Date:2016/12/29 0029
 * Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.system.service;

import com.hand.hap.account.dto.UserRole;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;

public interface IHmsUserRoleService extends IBaseService<UserRole>,
    ProxySelf<IHmsUserRoleService> {
    UserRole selectUserRoleByUserIdAndRoleId(long userId,long roleId);
}
