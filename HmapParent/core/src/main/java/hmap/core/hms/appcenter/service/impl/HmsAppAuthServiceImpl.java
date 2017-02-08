package hmap.core.hms.appcenter.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hmap.core.hms.appcenter.domain.HmsAppAuth;
import hmap.core.hms.domain.OauthClientDetails;
import hmap.core.hms.appcenter.mapper.HmsAppAuthMapper;
import hmap.core.hms.appcenter.service.IHmsAppAuthService;
import hmap.core.hms.service.IOauthClientDetailsService;
import hmap.core.security.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Created by xincai.zhang on 2016/8/14.
 */
@Service
@Transactional
public class HmsAppAuthServiceImpl extends BaseServiceImpl<HmsAppAuth>
    implements IHmsAppAuthService {
  private final Logger log = LoggerFactory.getLogger(this.getClass());
  @Autowired
  private HmsAppAuthMapper hmsAppAuthMapper;
  @Autowired
  IOauthClientDetailsService oauthClientDetailsService;

  @Override
  public HmsAppAuth selectById(String id) {
    return hmsAppAuthMapper.selectById(id);
  }

  public OauthClientDetails saveToOauth(IRequest iRequest, HmsAppAuth hmsAppAuth) {

    OauthClientDetails oauthClientDetails = new OauthClientDetails();
    oauthClientDetails.setClientId(hmsAppAuth.getAppId());
    oauthClientDetails.setResourceIds("api-resource,iapi-resource");
    oauthClientDetails.setClientSecret(hmsAppAuth.getAppSecret());
    oauthClientDetails.setScope("read,write");
    oauthClientDetails.setAuthorities("ROLE_USER");
    oauthClientDetails.setAuthorizedGrantTypes(hmsAppAuth.getAppAuzMode().toLowerCase());
    oauthClientDetails.setAutoapprove("true");

    OauthClientDetails oauthClientDetail =
        oauthClientDetailsService.selectByPrimaryKey(iRequest, oauthClientDetails);

    if (oauthClientDetail == null) {
      oauthClientDetail = oauthClientDetailsService.insert(iRequest, oauthClientDetails);
    } else {
      oauthClientDetail =
          oauthClientDetailsService.updateByPrimaryKey(iRequest, oauthClientDetails);
    }

    return oauthClientDetail;
  }

  @Transactional(propagation = Propagation.SUPPORTS)
  public HmsAppAuth insertOrUpdate(IRequest iRequest, HmsAppAuth hmsAppAuth) {
    HmsAppAuth app = null;
    if (StringUtils.isNoneEmpty(hmsAppAuth.getId())) {
      app = self().updateByPrimaryKeySelective(iRequest, hmsAppAuth);

    } else {
      String appAuthId = UUID.randomUUID().toString();
      String appId = UUID.randomUUID().toString();
      String appSecret = UUID.randomUUID().toString();
      hmsAppAuth.setId(appAuthId);
      hmsAppAuth.setAppId(appId);
      hmsAppAuth.setAppSecret(appSecret);
      hmsAppAuth.setUserName(SecurityUtils.getCurrentUserLogin());
      app = self().insertSelective(iRequest, hmsAppAuth);
    }
    this.saveToOauth(iRequest, hmsAppAuth);
    return app;
  }
}
