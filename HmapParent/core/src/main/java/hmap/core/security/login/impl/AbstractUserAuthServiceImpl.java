/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.security.login.impl Date:2016/10/12 0012 Create
 * By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.security.login.impl;

import hmap.core.security.login.ILoginService;

import java.util.Map;

public abstract class AbstractUserAuthServiceImpl implements ILoginService {
  protected String name;

  @Override
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public abstract boolean authenticate(String username, String password,
      Map<String, String> params);



}
