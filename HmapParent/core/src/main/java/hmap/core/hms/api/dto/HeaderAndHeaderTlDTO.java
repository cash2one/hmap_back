package hmap.core.hms.api.dto;

import hmap.core.util.PageRequest;

import java.util.List;

/**
 * Created by user on 2016/7/25.
 */
public class HeaderAndHeaderTlDTO extends PageRequest {

    private  String headerId;

//    @Children
//    @Transient
    private List<LineAndLineTlDTO> lineList;

    private  String      interfaceCode;

    //接口类型

    private  String      interfaceType;

    //SOAP报文前缀
    private  String      bodyHeader;
    //SOAP报文后缀
    private  String      bodyTail;

    //SOAP命名空间
    private  String      namespace;

    //系统地址
    private  String      domainUrl;
    //请求方法
    private  String      requestMethod;

    //请求形式
    private  String      requestFormat;
    //请求报文格式
    private  String      requestContenttype;

    //请求接收类型
    private  String      requestAccept;
    //校验用户名
    private  String      authUsername;
    //校验密码
    private  String      authPassword;
    //是否有效
    private  String      enableFlag;
    // 包装类
    private String       mapperClass;
    private String authType;
    private String grantType;
    private String accessTokenUrl;
    private String thirdpartyId;
    private String mockUrl;
    private String isMock;

//    语言
    private  String      lang;

    //描述
    private  String      name;
    private  String    description;

    //系统类型
    private String systemType;



    public List<LineAndLineTlDTO> getLineList() {
        return lineList;
    }

    public void setLineList(List<LineAndLineTlDTO> lineList) {
        this.lineList = lineList;
    }

    public String getHeaderId() {
        return headerId;
    }

    public void setHeaderId(String headerId) {
        this.headerId = headerId;
    }

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

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
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

    public String getMapperClass() {
        return mapperClass;
    }

    public void setMapperClass(String mapperClass) {
        this.mapperClass = mapperClass;
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
