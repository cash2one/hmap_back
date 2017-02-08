/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.hms.service.impl Date:2016/9/6 0006 Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.system.service.impl;

import com.hand.hap.account.dto.User;
import hmap.core.hms.system.mapper.HmsAuthorityMapper;
import hmap.core.hms.system.service.IHmsAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HmsAuthorityServiceImpl implements IHmsAuthorityService {
  @Autowired
  HmsAuthorityMapper hmsAuthorityMapper;

  @Override
  public List<User> selectUserByUrlAndUserName(String url, String userName) {

    List<User> users = hmsAuthorityMapper.selectUserByUrlAndUserName(url, userName);
    return users;
  }
}
