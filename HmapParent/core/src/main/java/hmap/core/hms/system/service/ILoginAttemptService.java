/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.hms.service Date:2016/11/8 0008 Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.system.service;

public interface ILoginAttemptService {
  void loginSucceeded(String key);

  void loginFailed(String key);

  boolean isBlocked(String key);
}
