/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.hms.service.impl Date:2016/10/27 0027 Create
 * By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.api.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import hmap.core.hms.api.domain.HmsOauthToken;
import hmap.core.hms.api.mapper.HmsOauthTokenMapper;
import hmap.core.hms.api.service.IHmsOauthTokenService;
import hmap.core.util.oauth.OAuth2Details;
import hmap.core.util.oauth.OAuthUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class HmsOauthTokenServiceImpl extends BaseServiceImpl<HmsOauthToken>
    implements IHmsOauthTokenService {
  private Logger logger = LoggerFactory.getLogger(HmsOauthTokenServiceImpl.class);
  @Autowired
  HmsOauthTokenMapper hmsOauthTokenMapper;

  private HmsOauthToken selectByAppId(String appId) {
    HmsOauthToken token = hmsOauthTokenMapper.selectByAppId(appId);
    return token;
  }

  private HmsOauthToken selectByAppIdAndUserName(String appId, String username) {
    HmsOauthToken token = hmsOauthTokenMapper.selectByAppIdAndUserName(appId, username);
    return token;
  }

  private HmsOauthToken saveAccessToken(OAuth2AccessToken accessToken, String clientId,
      String userName, HmsOauthToken token, boolean needInsert) {
    token.setAppId(clientId);
    token.setAccessToken(accessToken.getValue());
    token.setExpireDate(accessToken.getExpiration());
    if (StringUtils.isNotEmpty(userName)) {
      token.setUserName(userName);
    }
    if (accessToken.getRefreshToken() != null) {
      token.setRefreshToken(accessToken.getRefreshToken().getValue());
    }
    if (needInsert) {
      token = self().insertSelective(null, token);
    } else {
      token = self().updateByPrimaryKeySelective(null, token);
    }
    return token;
  }

  @Override
  @Transactional(propagation = Propagation.SUPPORTS)
  public HmsOauthToken getAccessToken(Map<String, String> oauthConfig,String isLogin) {
    OAuth2Details oauthDetails = OAuthUtils.createOAuthDetails(oauthConfig);
    String clientId = oauthDetails.getClientId();
    HmsOauthToken token = null;


    //如果是登陆进来，直接获取一次第三方token
    if(isLogin.toUpperCase().equals("Y")){
      OAuth2AccessToken accessToken = OAuthUtils.getAccessToken(oauthDetails);
      if (accessToken != null) {
        token = new HmsOauthToken();
        token = this.saveAccessToken(accessToken, clientId, oauthDetails.getUsername(), token, true);
      }else{
        logger.error("获取token失败，客户端id：" + clientId);
      }
      return token;
    }


    // 现在本地数据库中查询，看是否已经有token
    if (oauthDetails.getGrantType().equalsIgnoreCase("password")
        && StringUtils.isNotEmpty(oauthDetails.getUsername())) {
      token = this.selectByAppIdAndUserName(clientId, oauthDetails.getUsername());
    } else {
      token = this.selectByAppId(clientId);
    }

    // 如果还没有获取token，那么就获取一次
    if (token == null) {
      OAuth2AccessToken accessToken = OAuthUtils.getAccessToken(oauthDetails);
      if(accessToken !=null) {
        token = new HmsOauthToken();
        token = this.saveAccessToken(accessToken, clientId, oauthDetails.getUsername(), token, true);
      }else{
        logger.error("获取token失败，客户端id：" + clientId);
      }
    } else {
      // 已经失效了重新获取一次新的
      logger.debug("token失效时间戳:{},当前系统时间戳:{}", token.getExpireDate().getTime(),
          System.currentTimeMillis());
      if (token.getExpireDate().getTime() < (System.currentTimeMillis()+200 * 1000L)) {
        // 不存在refresh token
        if (StringUtils.isEmpty(token.getRefreshToken())) {
          OAuth2AccessToken accessToken = OAuthUtils.getAccessToken(oauthDetails);
          token =
              this.saveAccessToken(accessToken, clientId, oauthDetails.getUsername(), token, false);
        } else {
          // 使用refresh token 刷新
          Map<String, String> refreshConfig = new HashMap<String, String>();
          refreshConfig.put("clientId", oauthDetails.getClientId());
          refreshConfig.put("clientSecret", oauthDetails.getClientSecret());
          refreshConfig.put("grantType", "refresh_token");
          refreshConfig.put("refreshToken",token.getRefreshToken());
          refreshConfig.put("userName", oauthDetails.getUsername());
          refreshConfig.put("authenticationServerUrl", oauthDetails.getAuthenticationServerUrl());
          OAuth2Details oauthRefreshDetails = OAuthUtils.createOAuthDetails(refreshConfig);
          OAuth2AccessToken refreshedToken = OAuthUtils.getAccessToken(oauthRefreshDetails);
          token = this.saveAccessToken(refreshedToken, clientId, oauthRefreshDetails.getUsername(),
              token, false);
        }
      }
    }
    return token;
  }
}
