package hmap.core.util.oauth;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class OAuthUtils {
  private static Logger logger = LoggerFactory.getLogger(OAuthUtils.class);

  public static OAuth2Details createOAuthDetails(Map<String,String> oauthConfig) {

    OAuth2Details oauthDetails = new OAuth2Details();
    oauthDetails.setRefreshToken(oauthConfig.get("refreshToken"));
    oauthDetails.setGrantType(oauthConfig.get("grantType"));
    oauthDetails.setClientId(oauthConfig.get("clientId"));
    oauthDetails.setClientSecret(oauthConfig.get("clientSecret"));
    oauthDetails.setUsername(oauthConfig.get("userName"));
    oauthDetails.setPassword(oauthConfig.get("password"));
		oauthDetails.setAuthenticationServerUrl(oauthConfig.get("authenticationServerUrl"));
    return oauthDetails;
  }

  public static void getProtectedResource(OAuth2Details oauthDetails) {
    String resourceURL = oauthDetails.getResourceServerUrl();

    HttpGet get = new HttpGet(resourceURL);
    get.addHeader(OAuthConstants.AUTHORIZATION,
        getAuthorizationHeaderForAccessToken(oauthDetails.getAccessToken()));
    DefaultHttpClient client = new DefaultHttpClient();
    HttpResponse response = null;
    int code = -1;
    try {
      response = client.execute(get);
      code = response.getStatusLine().getStatusCode();
      if (code == 401 || code == 403) {
        // Access token is invalid or expired.Regenerate the access
        // token
        System.out.println("Access token is invalid or expired. Regenerating access token....");
        String accessToken = getAccessToken(oauthDetails).getValue();
        if (isValid(accessToken)) {
          // update the access token
          // System.out.println("New access token: " + accessToken);
          oauthDetails.setAccessToken(accessToken);
          get.removeHeaders(OAuthConstants.AUTHORIZATION);
          get.addHeader(OAuthConstants.AUTHORIZATION,
              getAuthorizationHeaderForAccessToken(oauthDetails.getAccessToken()));
          get.releaseConnection();
          response = client.execute(get);
          code = response.getStatusLine().getStatusCode();
          if (code >= 400) {
            throw new RuntimeException(
                "Could not access protected resource. Server returned http code: " + code);

          }

        } else {
          throw new RuntimeException("Could not regenerate access token");
        }
      }
      handleResponse(response);

    } catch (ClientProtocolException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } finally {
      get.releaseConnection();
    }

  }

  public static OAuth2AccessToken getAccessToken(OAuth2Details oauthDetails) {
    DefaultOAuth2AccessToken token=null;
    HttpPost post = new HttpPost(oauthDetails.getAuthenticationServerUrl());
    String clientId = oauthDetails.getClientId();
    String clientSecret = oauthDetails.getClientSecret();
    String userName=oauthDetails.getUsername();
    String password=oauthDetails.getPassword();
    String refreshToken=oauthDetails.getRefreshToken();
    String scope = oauthDetails.getScope();

    List<BasicNameValuePair> parametersBody = new ArrayList<BasicNameValuePair>();
			parametersBody.add(new BasicNameValuePair(OAuthConstants.GRANT_TYPE, oauthDetails.getGrantType().toLowerCase()));

    parametersBody.add(new BasicNameValuePair(OAuthConstants.CLIENT_ID, clientId));

    parametersBody.add(new BasicNameValuePair(OAuthConstants.CLIENT_SECRET, clientSecret));

    if(StringUtils.isNotEmpty(userName)){
      parametersBody.add(new BasicNameValuePair(OAuthConstants.USERNAME, userName));
    }

    if(StringUtils.isNotEmpty(password)){
      parametersBody.add(new BasicNameValuePair(OAuthConstants.PASSWORD, password));
    }
    if(StringUtils.isNotEmpty(refreshToken)){
      parametersBody.add(new BasicNameValuePair(OAuthConstants.REFRESH_TOKEN, refreshToken));
    }

    if (isValid(scope)) {
      parametersBody.add(new BasicNameValuePair(OAuthConstants.SCOPE, scope));
    }

    DefaultHttpClient client = new DefaultHttpClient();
    HttpResponse response = null;
    String accessToken = null;
    try {
      // 请求超时
      client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 20000);
      // 读取超时
      client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 20000);

      post.setEntity(new UrlEncodedFormEntity(parametersBody, HTTP.UTF_8));
      post.setHeader("Content-Type", OAuthConstants.URL_ENCODED_CONTENT);

      response = client.execute(post);
      int code = response.getStatusLine().getStatusCode();

      if(code!=OAuthConstants.HTTP_OK){
        return null;
      }

      Map<String, Object> map = handleResponse(response);
      accessToken = map.get(OAuthConstants.ACCESS_TOKEN).toString();
      String expireIn=map.get(OAuth2AccessToken.EXPIRES_IN).toString();
      String tokenType=map.get(OAuth2AccessToken.TOKEN_TYPE).toString();
      Object refreshTokenObj=map.get(OAuth2AccessToken.REFRESH_TOKEN);
      token=new DefaultOAuth2AccessToken(accessToken);
      Calendar c = Calendar.getInstance();
      c.add(Calendar.SECOND, Integer.parseInt(expireIn)); // 目前時間加expireIn
      token.setExpiration(c.getTime());
      token.setTokenType(tokenType);
      if(refreshTokenObj!=null){
        DefaultOAuth2RefreshToken oAuth2RefreshToken=new DefaultOAuth2RefreshToken(refreshTokenObj.toString());
        token.setRefreshToken(oAuth2RefreshToken);
      }
//      token.setRefreshToken();
    } catch (ClientProtocolException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    finally {
      post.releaseConnection();
    }

    return token;
  }

  public static Map handleResponse(HttpResponse response) {
    String contentType = OAuthConstants.JSON_CONTENT;
    if (response.getEntity().getContentType() != null) {
      contentType = response.getEntity().getContentType().getValue();
    }
    if (contentType.contains(OAuthConstants.JSON_CONTENT)) {
      return handleJsonResponse(response);
    } else if (contentType.contains(OAuthConstants.URL_ENCODED_CONTENT)) {
      return handleURLEncodedResponse(response);
    } else if (contentType.contains(OAuthConstants.XML_CONTENT)) {
      return handleXMLResponse(response);
    } else {
      // Unsupported Content type
      throw new RuntimeException("Cannot handle " + contentType
          + " content type. Supported content types include JSON, XML and URLEncoded");
    }

  }

  public static Map handleJsonResponse(HttpResponse response) {
    Map<String, Object> oauthLoginResponse = null;
    String contentType = response.getEntity().getContentType().getValue();
    try {
      oauthLoginResponse = (Map<String, Object>) new JSONParser().parse(EntityUtils.toString(response.getEntity()));
    } catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      throw new RuntimeException();
    } catch (org.json.simple.parser.ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      throw new RuntimeException();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      throw new RuntimeException();
    } catch (RuntimeException e) {
      System.out.println("Could not parse JSON response");
      throw e;
    }

    for (Map.Entry<String, Object> entry : oauthLoginResponse.entrySet()) {
      System.out.println(String.format("  %s = %s", entry.getKey(), entry.getValue()));
    }
    return oauthLoginResponse;
  }

  public static Map handleURLEncodedResponse(HttpResponse response) {
    Map<String, Charset> map = Charset.availableCharsets();
    Map<String, String> oauthResponse = new HashMap<String, String>();
    Set<Map.Entry<String, Charset>> set = map.entrySet();
    Charset charset = null;
    HttpEntity entity = response.getEntity();

    System.out.println();
    System.out.println("********** Response Received **********");

    for (Map.Entry<String, Charset> entry : set) {
      System.out.println(String.format("  %s = %s", entry.getKey(), entry.getValue()));
      if (entry.getKey().equalsIgnoreCase(HTTP.UTF_8)) {
        charset = entry.getValue();
      }
    }

    try {
      List<NameValuePair> list =
          URLEncodedUtils.parse(EntityUtils.toString(entity), Charset.forName(HTTP.UTF_8));
      for (NameValuePair pair : list) {
        System.out.println(String.format("  %s = %s", pair.getName(), pair.getValue()));
        oauthResponse.put(pair.getName(), pair.getValue());
      }

    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      throw new RuntimeException("Could not parse URLEncoded Response");
    }

    return oauthResponse;
  }

  public static Map handleXMLResponse(HttpResponse response) {
    Map<String, String> oauthResponse = new HashMap<String, String>();
    try {

      String xmlString = EntityUtils.toString(response.getEntity());
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = factory.newDocumentBuilder();
      InputSource inStream = new InputSource();
      inStream.setCharacterStream(new StringReader(xmlString));
      Document doc = db.parse(inStream);

      System.out.println("********** Response Receieved **********");
      parseXMLDoc(null, doc, oauthResponse);
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("Exception occurred while parsing XML response");
    }
    return oauthResponse;
  }

  public static void parseXMLDoc(Element element, Document doc, Map<String, String> oauthResponse) {
    NodeList child = null;
    if (element == null) {
      child = doc.getChildNodes();

    } else {
      child = element.getChildNodes();
    }
    for (int j = 0; j < child.getLength(); j++) {
      if (child.item(j).getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
        Element childElement = (Element) child.item(j);
        if (childElement.hasChildNodes()) {
          System.out.println(childElement.getTagName() + " : " + childElement.getTextContent());
          oauthResponse.put(childElement.getTagName(), childElement.getTextContent());
          parseXMLDoc(childElement, null, oauthResponse);
        }

      }
    }
  }

  public static String getAuthorizationHeaderForAccessToken(String accessToken) {
    return OAuthConstants.BEARER + " " + accessToken;
  }

  public static String getBasicAuthorizationHeader(String username, String password) {
    return OAuthConstants.BASIC + " " + encodeCredentials(username, password);
  }

  public static String encodeCredentials(String username, String password) {
    String cred = username + ":" + password;
    String encodedValue = null;
    byte[] encodedBytes = Base64.encodeBase64(cred.getBytes());
    encodedValue = new String(encodedBytes);
    System.out.println("encodedBytes " + new String(encodedBytes));

    byte[] decodedBytes = Base64.decodeBase64(encodedBytes);
    System.out.println("decodedBytes " + new String(decodedBytes));

    return encodedValue;

  }

  public static boolean isValidInput(OAuth2Details input) {



    if (input == null) {
      return false;
    }

    String grantType = input.getGrantType();

    if (!isValid(grantType)) {
      System.out.println("Please provide valid value for grant_type");
      return false;
    }

    if (!isValid(input.getAuthenticationServerUrl())) {
      System.out.println("Please provide valid value for authentication server url");
      return false;
    }

    if (grantType.equals(OAuthConstants.GRANT_TYPE_PASSWORD)) {
      if (!isValid(input.getUsername()) || !isValid(input.getPassword())) {
        System.out.println("Please provide valid username and password for password grant_type");
        return false;
      }
    }

    if (grantType.equals(OAuthConstants.GRANT_TYPE_CLIENT_CREDENTIALS)) {
      if (!isValid(input.getClientId()) || !isValid(input.getClientSecret())) {
        System.out.println(
            "Please provide valid client_id and client_secret for client_credentials grant_type");
        return false;
      }
    }

    System.out.println("Validated Input");
    return true;

  }

  public static boolean isValid(String str) {
    return (str != null && str.trim().length() > 0);
  }

}
