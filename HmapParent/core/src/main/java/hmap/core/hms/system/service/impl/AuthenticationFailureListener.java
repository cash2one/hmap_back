/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.security.login.impl Date:2016/11/7 0007 Create
 * By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.system.service.impl;

import hmap.core.hms.system.service.ILogService;
import hmap.core.hms.system.service.ILoginAttemptService;
import hmap.core.util.StatelessRequestHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationFailureListener
    implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
  @Autowired
  ILoginAttemptService loginAttemptService;
@Autowired
    ILogService iLogService;
  protected final Log logger = LogFactory.getLog(this.getClass());

  @Override
  public void onApplicationEvent(
      AuthenticationFailureBadCredentialsEvent authenticationFailureBadCredentialsEvent) {
    String ip = StatelessRequestHelper.getCurrentRequestRemoteAddr();
    iLogService.serviceLogInfo("AuthenticationFailureListener:" + ip);
    loginAttemptService.loginFailed(ip);
  }
}
