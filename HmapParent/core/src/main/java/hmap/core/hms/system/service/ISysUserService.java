/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.hms.service.impl Date:2016/8/12 0012 Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.system.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hmap.core.hms.system.domain.SysUser;

import java.util.List;

public interface ISysUserService extends IBaseService<SysUser>,
        ProxySelf<ISysUserService> {

    public List<SysUser> getUserByUserName(IRequest request, String user);

}
