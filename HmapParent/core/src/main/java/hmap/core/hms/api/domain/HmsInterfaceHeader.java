package hmap.core.hms.api.domain;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.system.dto.BaseDTO;
import com.sun.istack.NotNull;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author jiguang.sun@hand-china.com
 * @version 2016/7/21
 */
@Table(name = "hms_interface_header_b")
@MultiLanguage
public class HmsInterfaceHeader extends BaseDTO {

  @Id
  @Column
  private String headerId;

  // private HmsInterfaceLine line;

  @Column
  @NotNull
  private String interfaceCode;

  // 接口类型
  @Column
  @NotNull
  private String interfaceType;

  // SOAP报文前缀
  @Column
  private String bodyHeader;
  // SOAP报文后缀
  @Column
  private String bodyTail;

  // SOAP命名空间
  @Column
  private String namespace;

  // 系统地址
  @Column
  @NotNull
  private String domainUrl;
  // 请求方法
  @Column
  @NotNull
  private String requestMethod;

  // 请求形式
  @Column
  @NotNull
  private String requestFormat;
  // 请求报文格式
  @Column
  private String requestContenttype;

  // 请求接收类型
  @Column
  private String requestAccept;
  // 校验用户名
  @Column
  private String authUsername;
  // 校验密码
  @Column
  private String authPassword;
  // 是否有效
  @Column
  @NotNull
  private String enableFlag;

  // 包装类
  @Column
  private String mapperClass;
  // 多语言
  @Column
  @MultiLanguageField
  private String name;
  @Column
  @MultiLanguageField
  private String description;

  @Column
  private String systemType;
  @Column
  private String authType;
  @Column
  private String grantType;
  @Column
  private String accessTokenUrl;
  @Column
  private String thirdpartyId;
  @Column
  private String mockUrl;
  @Column
  private String isMock;

  public String getHeaderId() {
    return headerId;
  }

  public void setHeaderId(String headerId) {
    this.headerId = headerId;
  }

  // public HmsInterfaceLine getLine() {
  // return line;
  // }
  //
  // public void setLine(HmsInterfaceLine line) {
  // this.line = line;
  // }

  public String getInterfaceCode() {
    return interfaceCode;
  }

  public void setInterfaceCode(String interfaceCode) {
    this.interfaceCode = interfaceCode;
  }

  public String getInterfaceType() {
    return interfaceType;
  }

  public void setInterfaceType(String interfaceType) {
    this.interfaceType = interfaceType;
  }

  public String getBodyHeader() {
    return bodyHeader;
  }

  public void setBodyHeader(String bodyHeader) {
    this.bodyHeader = bodyHeader;
  }

  public String getBodyTail() {
    return bodyTail;
  }

  public void setBodyTail(String bodyTail) {
    this.bodyTail = bodyTail;
  }

  public String getNamespace() {
    return namespace;
  }

  public void setNamespace(String namespace) {
    this.namespace = namespace;
  }

  public String getDomainUrl() {
    return domainUrl;
  }

  public void setDomainUrl(String domainUrl) {
    this.domainUrl = domainUrl;
  }

  public String getRequestMethod() {
    return requestMethod;
  }

  public void setRequestMethod(String requestMethod) {
    this.requestMethod = requestMethod;
  }

  public String getRequestFormat() {
    return requestFormat;
  }

  public void setRequestFormat(String requestFormat) {
    this.requestFormat = requestFormat;
  }

  public String getRequestContenttype() {
    return requestContenttype;
  }

  public void setRequestContenttype(String requestContenttype) {
    this.requestContenttype = requestContenttype;
  }

  public String getRequestAccept() {
    return requestAccept;
  }

  public void setRequestAccept(String requestAccept) {
    this.requestAccept = requestAccept;
  }

  public String getAuthUsername() {
    return authUsername;
  }

  public void setAuthUsername(String authUsername) {
    this.authUsername = authUsername;
  }

  public String getAuthPassword() {
    return authPassword;
  }

  public void setAuthPassword(String authPassword) {
    this.authPassword = authPassword;
  }

  public String getEnableFlag() {
    return enableFlag;
  }

  public void setEnableFlag(String enableFlag) {
    this.enableFlag = enableFlag;
  }

  public String getMapperClass() {
    return mapperClass;
  }

  public void setMapperClass(String mapperClass) {
    this.mapperClass = mapperClass;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getSystemType() {
    return systemType;
  }

  public void setSystemType(String systemType) {
    this.systemType = systemType;
  }

  public String getAuthType() {
    return authType;
  }

  public void setAuthType(String authType) {
    this.authType = authType;
  }

  public String getGrantType() {
    return grantType;
  }

  public void setGrantType(String grantType) {
    this.grantType = grantType;
  }

  public String getAccessTokenUrl() {
    return accessTokenUrl;
  }

  public void setAccessTokenUrl(String accessTokenUrl) {
    this.accessTokenUrl = accessTokenUrl;
  }

  public String getThirdpartyId() {
    return thirdpartyId;
  }

  public void setThirdpartyId(String thirdpartyId) {
    this.thirdpartyId = thirdpartyId;
  }

  public String getMockUrl() {
    return mockUrl;
  }

  public void setMockUrl(String mockUrl) {
    this.mockUrl = mockUrl;
  }

  public String getIsMock() {
    return isMock;
  }

  public void setIsMock(String isMock) {
    this.isMock = isMock;
  }
}
