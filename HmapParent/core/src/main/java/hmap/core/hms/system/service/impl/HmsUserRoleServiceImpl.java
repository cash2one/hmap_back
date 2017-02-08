/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.hms.service.impl Date:2016/12/29 0029 Create
 * By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.hand.hap.account.dto.UserRole;
import com.hand.hap.system.service.impl.BaseServiceImpl;

import hmap.core.hms.system.mapper.HmsUserRoleMapper;
import hmap.core.hms.system.service.IHmsUserRoleService;
import org.springframework.stereotype.Service;

@Service
public class HmsUserRoleServiceImpl extends BaseServiceImpl<UserRole>
    implements IHmsUserRoleService {
  @Autowired
  HmsUserRoleMapper hmsUserRoleMapper;

  @Override
  public UserRole selectUserRoleByUserIdAndRoleId(long userId, long roleId) {
    return hmsUserRoleMapper.selectUserRoleByUserIdAndRoleId(userId, roleId);
  }
}
