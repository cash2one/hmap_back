/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.hms.service.impl Date:2016/8/4 0004 Create By:zongyun.zhou@hand-china.com
 */
package hmap.core.hms.system.service.impl;


import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hmap.core.hms.system.domain.SysUser;
import hmap.core.hms.system.mapper.SysUserMapper;
import hmap.core.hms.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUser>
        implements ISysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public List<SysUser> getUserByUserName(IRequest request, String user) {

        List<SysUser> e = sysUserMapper.selectByUserName(user);
        return  e;
    }
}
