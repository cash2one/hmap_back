package hmap.core.security.login.impl;

import com.hand.hap.security.CustomUserDetails;
import com.hand.hap.security.PasswordManager;
import hmap.core.hms.api.domain.HmsOauthToken;
import hmap.core.hms.system.domain.HmsSystemConfig;
import hmap.core.hms.system.domain.HmsThirdpartyApp;
import hmap.core.hms.api.dto.HeaderAndHeaderTlDTO;
import hmap.core.hms.api.service.IHmsHeaderService;
import hmap.core.hms.api.service.IHmsOauthTokenService;
import hmap.core.hms.system.service.IHmsSystemConfigService;
import hmap.core.hms.system.service.IHmsThirdpartyAppService;
import hmap.core.security.login.IClientDetailService;
import hmap.core.security.login.ILoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class LoginUserDetailServiceImpl implements IClientDetailService {
  private Logger logger = LoggerFactory.getLogger(LoginUserDetailServiceImpl.class);
  private ILoginService loginService;
  @Autowired
  private IHmsHeaderService hmsHeaderService;
  @Autowired
  private IHmsSystemConfigService hmsSystemConfigService;
  @Autowired
  private IHmsOauthTokenService hmsOauthTokenService;
  @Autowired
  private IHmsThirdpartyAppService hmsThirdpartyAppService;
  @Resource(name = "passwordManager")
  PasswordManager passwordManager;

  @Override
  public UserDetails loadUserByUsername(String userName, String pwd)
      throws UsernameNotFoundException {
    logger.info("loadUserByUsername..........");
    if (!loginService.authenticate(userName, pwd, null)) {
      return null;
    }
    Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
    authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

    /*
     * 透传接口中如果存在oauth2类型并且是密码模式的，需要在登录时就把token获得，并持久化
     */
    HmsSystemConfig config = hmsSystemConfigService.selectByConfigKey("login.saveInterfaceToken");
    if (config.getConfigValue().equals("Y")) {
      List<HeaderAndHeaderTlDTO> headList = hmsHeaderService.getAllPasswordOauthHeader();
      if (headList != null && headList.size() > 0) {
        for (HeaderAndHeaderTlDTO head : headList) {
          HmsThirdpartyApp thirdpartyApp =
              hmsThirdpartyAppService.selectById(head.getThirdpartyId());
          Map<String, String> oauthConfig = new HashMap<String, String>();
          oauthConfig.put("clientId", thirdpartyApp.getAppKey());
          oauthConfig.put("clientSecret", thirdpartyApp.getAppSecret());
          oauthConfig.put("grantType", head.getGrantType());
          oauthConfig.put("userName", userName);
          oauthConfig.put("password", pwd);
          oauthConfig.put("authenticationServerUrl",
              head.getDomainUrl().concat(head.getAccessTokenUrl()));
          // 此处会尝试获取透传系统的token，如果获取不到就会跳过，当需要透传执行需要token的接口时将会无法执行，
          // 需要重新登录才能获取到token
          HmsOauthToken hmsOauthToken = hmsOauthTokenService.getAccessToken(oauthConfig,"Y");
        }
      }
    }

    /*
     * 客户化登录组件，在创建userdetail对象时将组件的名称附带上，这样就能够支持多种登录模式并存
     */
    //在token中不保存明文密码，使用密文
    UserDetails userDetails = new CustomUserDetails(1L, userName, pwd, true,
        true, true, true, authorities);
    // ip解除限制
    logger.info("userDetails:" + userDetails.getAuthorities().size());
    return userDetails;
  }

  public ILoginService getLoginService() {
    return loginService;
  }

  public void setLoginService(ILoginService loginService) {
    this.loginService = loginService;
  }
}
