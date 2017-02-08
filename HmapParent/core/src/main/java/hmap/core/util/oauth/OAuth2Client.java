package hmap.core.util.oauth;

import org.springframework.security.oauth2.common.OAuth2AccessToken;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class OAuth2Client {



  public static void main(String[] args) throws Exception {

    // Load the properties file
    // Generate the OAuthDetails bean from the config properties file
    Map<String, String> oauthConfig = new HashMap<String, String>();
    oauthConfig.put("clientId", "clientId");
    oauthConfig.put("clientSecret", "clientSecret");
    oauthConfig.put("grantType", "grantType");
    oauthConfig.put("userName", "userName");
    oauthConfig.put("password", "password");
    oauthConfig.put("authenticationServerUrl", "authenticationServerUrl");

    OAuth2Details oauthDetails = OAuthUtils.createOAuthDetails(oauthConfig);
        // Validate Input
    if (!OAuthUtils.isValidInput(oauthDetails)) {
      System.out.println("Please provide valid config properties to continue.");
      System.exit(0);
    }
    OAuth2AccessToken accessToken = OAuthUtils.getAccessToken(oauthDetails);
    // Determine operation
    if (oauthDetails.isAccessTokenRequest()) {
      // Generate new Access token

      //TODO
    }

    else {
      // Access protected resource from server using OAuth2.0
      // Response from the resource server must be in Json or Urlencoded or xml
      System.out.println("Resource endpoint url: " + oauthDetails.getResourceServerUrl());
      System.out.println("Attempting to retrieve protected resource");
      OAuthUtils.getProtectedResource(oauthDetails);
    }
  }
}
