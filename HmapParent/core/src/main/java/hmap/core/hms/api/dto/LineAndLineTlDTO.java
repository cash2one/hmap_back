package hmap.core.hms.api.dto;

import hmap.core.util.PageRequest;

import javax.persistence.Column;

/**
 * Created jiguang.sun@hand-china.com on 2016/7/26.
 * HmsInterfaceLine 和 HmsInterfaceLineTl 的实体的所有字段的DTO
 */
public class LineAndLineTlDTO extends PageRequest {

/*
* ItfInterfaceLine实体的字段
* */
    private String lineId;

    private String headerId;

    private String lineCode;

    private String iftUrl;

    private String enableFlag;

    private String objToArr;

    private String mockLineUrl;
    private String isMockLine;
    /*
    * HmsInterfaceLineTl 实体的字段
    * */
    private String lang;

    private String lineName;

    private String lineDescription;

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getHeaderId() {
        return headerId;
    }

    public void setHeaderId(String headerId) {
        this.headerId = headerId;
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

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getLineDescription() {
        return lineDescription;
    }

    public void setLineDescription(String lineDescription) {
        this.lineDescription = lineDescription;
    }

    public String getObjToArr() {
        return objToArr;
    }

    public void setObjToArr(String objToArr) {
        this.objToArr = objToArr;
    }
    public String getMockLineUrl() {
        return mockLineUrl;
    }

    public void setMockLineUrl(String mockLineUrl) {
        this.mockLineUrl = mockLineUrl;
    }

    public String getIsMockLine() {
        return isMockLine;
    }

    public void setIsMockLine(String isMockLine) {
        this.isMockLine = isMockLine;
    }
}
