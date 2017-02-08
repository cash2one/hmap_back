/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.hms.service.impl Date:2016/11/8 0008 Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.system.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.hand.hap.core.IRequest;

import hmap.core.hms.system.domain.HmsLoginError;
import hmap.core.hms.system.domain.HmsSystemConfig;
import hmap.core.hms.system.service.IHmsLoginErrorService;
import hmap.core.hms.system.service.IHmsSystemConfigService;
import hmap.core.hms.system.service.ILoginAttemptService;
import hmap.core.util.StatelessRequestHelper;
import org.springframework.stereotype.Service;

@Service
public class LoginAttemptServiceImpl implements ILoginAttemptService {
  @Autowired
  IHmsLoginErrorService hmsLoginErrorService;
  @Autowired
  IHmsSystemConfigService hmsSystemConfigService;

  @Override
  public void loginSucceeded(String key) {
    // 如果成功登陆，则解除登陆次数限制
    HmsLoginError error = hmsLoginErrorService.selectByUserIp(key);
    if (error != null) {
      error.setErrTimes(0);
      IRequest request = StatelessRequestHelper.newEmptyRequest();
      hmsLoginErrorService.updateByPrimaryKeySelective(request, error);
    }
  }

  @Override
  public void loginFailed(String key) {
    // 如果登陆失败，就更新登陆失败次数，根据ip查找
    HmsLoginError error = hmsLoginErrorService.selectByUserIp(key);
    HmsSystemConfig config = hmsSystemConfigService.selectByConfigKey("login.errInterval");// 单位分钟
    long interval = Long.parseLong(config.getConfigValue()) * 60 * 1000;// 单位毫秒
    if (error != null) {
      // 如果在一定时限内错误登陆则一直累计，超过时限的话就重新累计
      if ((System.currentTimeMillis() - error.getErrDate().getTime()) > interval) {
        error.setErrTimes(1);
        error.setErrDate(new Date());
        IRequest request = StatelessRequestHelper.newEmptyRequest();
        hmsLoginErrorService.updateByPrimaryKeySelective(request, error);
      } else {
        error.setErrTimes(error.getErrTimes() + 1);
        error.setErrDate(new Date());
        IRequest request = StatelessRequestHelper.newEmptyRequest();
        hmsLoginErrorService.updateByPrimaryKeySelective(request, error);
      }

    } else {
      error = new HmsLoginError();
      error.setUserIp(key);
      error.setErrTimes(1);
      error.setErrDate(new Date());
      IRequest request = StatelessRequestHelper.newEmptyRequest();
      hmsLoginErrorService.insertSelective(request, error);
    }
  }

  @Override
  public boolean isBlocked(String key) {
    boolean result = false;
    HmsLoginError error = hmsLoginErrorService.selectByUserIp(key);
    String config = hmsSystemConfigService.getConfigValue("login.maxErrTimes");
    String errIntervalConfig = hmsSystemConfigService.getConfigValue("login.errInterval");// 单位分钟
    long interval = Long.parseLong(errIntervalConfig) * 60 * 1000;// 单位毫秒
    if (error != null) {
      if ((System.currentTimeMillis() - error.getErrDate().getTime()) > interval) {
        result = false;
      } else if (error.getErrTimes() >= Integer.parseInt(config)) {
        result = true;
      }
    }
    return result;
  }
}
