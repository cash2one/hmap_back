/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.login.controllers Date:2016/7/3 0003 Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.system.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hand.hap.function.dto.Resource;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.util.WebUtils;

import com.codahale.metrics.annotation.Timed;
import com.hand.hap.account.dto.User;
import com.hand.hap.account.exception.UserException;
import com.hand.hap.account.service.IUserService;
import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.util.TimeZoneUtil;
import com.hand.hap.security.TokenUtils;
import com.hand.hap.security.captcha.ICaptchaManager;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;

import hmap.core.hms.system.service.IHmsAuthorityService;
import hmap.core.security.SecurityUtils;

@Controller
public class AuthenticateController extends BaseController {
  private static final boolean VALIDATE_CAPTCHA = true;
  // 校验码
  private static final String KEY_VERIFICODE = "verifiCode";
  public static final String BEARER_AUTHENTICATION = "Bearer ";

  @Autowired
  private IUserService userService;
  @Autowired
  private IHmsAuthorityService hmsAuthorityService;
  @Autowired
  private ICaptchaManager captchaManager;
  @Autowired
  private TokenStore tokenStore;

  @RequestMapping(value = "/authentication", method = RequestMethod.POST)
  @ResponseBody
  public ResponseData login(HttpServletRequest request, HttpServletResponse response,
      final User user) {
    Locale locale = RequestContextUtils.getLocale(request);
    User loginUser = null;
    try {
      checkCaptcha(request, response);
      loginUser = userService.login(user);
      HttpSession session = request.getSession(true);
      session.setAttribute(User.FIELD_USER_ID, user.getUserId());
      session.setAttribute(User.FIELD_USER_NAME, user.getUserName());
      session.setAttribute(IRequest.FIELD_LOCALE, locale.toString());
      setTimeZoneFromPreference(session, user.getUserId());
      generateSecurityKey(session);
    } catch (UserException e) {
      e.printStackTrace();
    }
    return new ResponseData(true);
  }

  @RequestMapping(value = "/api/account", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @Timed
  public ResponseData getAccount(HttpServletRequest request) {
    IRequest requestContext = this.createRequestContext(request);
    requestContext.setLocale("zh_CN");//先默认登录语言是中文

    String username = SecurityUtils.getCurrentUserLogin();
    User user = userService.selectByUserName(username);
    return new ResponseData(Arrays.asList(user));
  }

  @RequestMapping(value = "/api/authority", method = RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseData validateRoute(HttpServletRequest request,@RequestBody Resource example){
    String username = SecurityUtils.getCurrentUserLogin();
    List<User> user = hmsAuthorityService.selectUserByUrlAndUserName(example.getUrl(),username);
    return new ResponseData(user);
  }
  @RequestMapping(value = "/api/logout", method = RequestMethod.POST)
  public void logout(HttpServletRequest request, HttpServletResponse response) {
    String token = request.getHeader("authorization");
    if (token != null && token.startsWith(BEARER_AUTHENTICATION)) {
      final OAuth2AccessToken oAuth2AccessToken =
          tokenStore.readAccessToken(StringUtils.substringAfter(token, BEARER_AUTHENTICATION));

      if (oAuth2AccessToken != null) {
        tokenStore.removeAccessToken(oAuth2AccessToken);
      }
    }

    response.setStatus(HttpServletResponse.SC_OK);
  }
  // @RequestMapping(value = "/authentication", method = RequestMethod.GET)
  // @ResponseBody
  // public ResponseData login(HttpServletRequest request, HttpServletResponse response) {
  // return new ResponseData(true);
  // }

  /**
   * 校验验证码是否正确.
   *
   * @param request 请求
   * @param response 响应
   * @throws com.hand.hap.account.exception.UserException 异常
   */
  private void checkCaptcha(HttpServletRequest request, HttpServletResponse response)
      throws UserException {
    if (VALIDATE_CAPTCHA) {
      Cookie cookie = WebUtils.getCookie(request, captchaManager.getCaptchaKeyName());
      String captchaCode = request.getParameter(KEY_VERIFICODE);
      if (cookie == null || StringUtils.isEmpty(captchaCode)
          || !captchaManager.checkCaptcha(cookie.getValue(), captchaCode)) {
        // view.addObject("_password", user.getPassword());
        throw new UserException("ERROR", "User Error", null);
      }
    }
  }

  private void setTimeZoneFromPreference(HttpSession session, Long accountId) {
    // SysPreferences para = new SysPreferences();
    // para.setUserId(accountId);
    // para.setPreferencesLevel(10L);
    // para.setPreferences(BaseConstants.TIME_ZONE);
    // SysPreferences pref =
    // preferencesService.querySysPreferencesLine(RequestHelper.newEmptyRequest(),
    // para);
    // String tz = pref == null ? null : pref.getPreferencesValue();
    String tz = "GMT+0800";
    if (StringUtils.isBlank(tz)) {
      tz = TimeZoneUtil.toGMTFormat(TimeZone.getDefault());
    }
    session.setAttribute(BaseConstants.TIME_ZONE, tz);
  }

  private String generateSecurityKey(HttpSession session) {
    return TokenUtils.setSecurityKey(session);
  }
}
