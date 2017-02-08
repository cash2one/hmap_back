package hmap.core.hms.api.dto;

import com.hand.hap.system.dto.BaseDTO;

import javax.persistence.Column;

/**
 * Created by user on 2016/7/27.
 */
public class HeaderAndLineDTO extends BaseDTO {

    // HmsInterfaceHeader
    private String headerId;

    private String interfaceCode;

    // 接口类型
    private String interfaceType;

    // SOAP报文前缀
    private String bodyHeader;
    // SOAP报文后缀
    private String bodyTail;

    // SOAP命名空间
    private String namespace;

    // 系统地址
    private String domainUrl;
    // 请求方法
    private String requestMethod;

    // 请求形式
    private String requestFormat;
    // 请求报文格式
    private String requestContenttype;

    // 请求接收类型
    private String requestAccept;

    private String authType;
    private String grantType;
    private String accessTokenUrl;
    private String thirdpartyId;
    private String mockUrl;
    private String isMock;
    // 校验用户名
    private String authUsername;
    // 校验密码
    private String authPassword;
    private String mapperClass;

    // HmsInterfaceLine
    private String lineId;

    private String lineCode;
    private String iftUrl;
    private String mockLineUrl;
    private String isMockLine;
    private String enableFlag;

    //对象转换数组
    private String objToArr;


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

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getLineCode() {
        return lineCode;
    }

    public void setLineCode(String lineCode) {
        this.lineCode = lineCode;
    }

    public String getIftUrl() {
        return iftUrl;
    }

    public void setIftUrl(String iftUrl) {
        this.iftUrl = iftUrl;
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

    public String getObjToArr() {
        return objToArr;
    }

    public void setObjToArr(String objToArr) {
        this.objToArr = objToArr;
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

    public void setMockUrl(String mockUrl) {
        this.mockUrl = mockUrl;
    }

    public void setIsMock(String isMock) {
        this.isMock = isMock;
    }

    public void setMockLineUrl(String mockLineUrl) {
        this.mockLineUrl = mockLineUrl;
    }

    public void setIsMockLine(String isMockLine) {
        this.isMockLine = isMockLine;
    }

    public String getMockUrl() {
        return mockUrl;
    }

    public String getIsMock() {
        return isMock;
    }

    public String getMockLineUrl() {
        return mockLineUrl;
    }

    public String getIsMockLine() {
        return isMockLine;
    }
}
