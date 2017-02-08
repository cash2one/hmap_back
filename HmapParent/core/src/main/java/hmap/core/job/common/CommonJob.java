/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.job.common Date:2016/12/13 0013 Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.job.common;

import com.hand.hap.job.AbstractJob;
import hmap.core.hms.system.service.ILogService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;

import java.util.ArrayList;
import java.util.List;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class CommonJob extends AbstractJob {
  @Autowired
  ILogService logService;

  public void safeExecute(JobExecutionContext context) throws Exception {
    try {
      StringBuffer logBuffer = new StringBuffer();
      JobDataMap dataMap = context.getJobDetail().getJobDataMap();
      String requestUrl = dataMap.getString("requestUrl");
      String accessTokenUri = dataMap.getString("accessTokenUri");
      String clientId = dataMap.getString("clientId");
      String clientSecret = dataMap.getString("clientSecret");
      String param = dataMap.getString("param");

      logBuffer.append("requestUrl:").append(requestUrl).append(",accessToken:").append(requestUrl)
          .append(",param:").append(param);
      logService.jobLogInfo(logBuffer.toString());

        ClientCredentialsResourceDetails resourceDetails =
          new ClientCredentialsResourceDetails();
      List scopes = new ArrayList<String>(2);
      scopes.add("write");
      scopes.add("read");
      resourceDetails.setAccessTokenUri(accessTokenUri);
      resourceDetails.setClientId(clientId);
      resourceDetails.setClientSecret(clientSecret);
      resourceDetails.setGrantType("client_credentials");
      resourceDetails.setScope(scopes);
      AccessTokenRequest atr = new DefaultAccessTokenRequest();

      DefaultOAuth2ClientContext clientContext = new DefaultOAuth2ClientContext(atr);
      OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resourceDetails, clientContext);
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
      HttpEntity<String> entity = new HttpEntity<String>(param, headers);
      String response = restTemplate.postForObject(requestUrl, entity, String.class);

      logService.jobLogInfo(",jobresponse:"+response);

      // restTemplate.getAccessToken();
      // HttpConnect.httpPostMain(requestUrl, accessToken, param);
      // simpleRestTemplate.
    } catch (Exception e) {
        logService.jobLogError(e.getMessage(),e);
    }

  }

  public boolean isRefireImmediatelyWhenException() {
    return true;
  }
}
