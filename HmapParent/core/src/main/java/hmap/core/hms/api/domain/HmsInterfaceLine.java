package hmap.core.hms.api.domain;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.system.dto.BaseDTO;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by user on 2016/7/25.
 */
@MultiLanguage
@Table(name = "hms_interface_line_b")
public class HmsInterfaceLine extends BaseDTO {

    @Id
    @Column
    private String lineId;
    @Column
    private String headerId;
    @Column
    private String lineCode;
    @Column
    private String iftUrl;
    @Column
    private String enableFlag;
    @Column
    private String mockLineUrl;
    @Column
    private String isMockLine;
    @Column
    private  String headerPara1;
    @Column
    private  String headerPara2;
    @Column
    private  String headerPara3;
    @Column
    private  String headerPara4;
    @Column
    private  String headerPara5;
    @Column
    private  String headerPara6;
    @Column
    private  String headerPara7;
    @Column
    private  String headerPara8;
    @Column
    private  String headerPara9;
    @Column
    private  String headerPara10;
    @Column
    private  String headerPara11;
    @Column
    private  String headerPara12;
    @Column
    private  String headerPara13;
    @Column
    private  String headerPara14;
    @Column
    private  String headerPara15;
    @Column
    private String para1;
    @Column
    private String para2;
    @Column
    private String para3;
    @Column
    private String para4;
    @Column
    private String para5;
    @Column
    private String para6;
    @Column
    private String para7;
    @Column
    private String para8;
    @Column
    private String para9;
    @Column
    private String para10;
    @Column
    private String para11;
    @Column
    private String para12;
    @Column
    private String para13;
    @Column
    private String para14;
    @Column
    private String para15;
    @Column
    private String para16;
    @Column
    private String para17;
    @Column
    private String para18;
    @Column
    private String para19;
    @Column
    private String para20;
    @Column
    private String para21;
    @Column
    private String para22;
    @Column
    private String para23;
    @Column
    private String para24;
    @Column
    private String para25;
    @Column
    private String objToArr;

    @Column
    @MultiLanguageField
    private String lineName;

    @Column
    @MultiLanguageField
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

    public String getHeaderPara1() {
        return headerPara1;
    }

    public void setHeaderPara1(String headerPara1) {
        this.headerPara1 = headerPara1;
    }

    public String getHeaderPara2() {
        return headerPara2;
    }

    public void setHeaderPara2(String headerPara2) {
        this.headerPara2 = headerPara2;
    }

    public String getHeaderPara3() {
        return headerPara3;
    }

    public void setHeaderPara3(String headerPara3) {
        this.headerPara3 = headerPara3;
    }

    public String getHeaderPara4() {
        return headerPara4;
    }

    public void setHeaderPara4(String headerPara4) {
        this.headerPara4 = headerPara4;
    }

    public String getHeaderPara5() {
        return headerPara5;
    }

    public void setHeaderPara5(String headerPara5) {
        this.headerPara5 = headerPara5;
    }

    public String getHeaderPara6() {
        return headerPara6;
    }

    public void setHeaderPara6(String headerPara6) {
        this.headerPara6 = headerPara6;
    }

    public String getHeaderPara7() {
        return headerPara7;
    }

    public void setHeaderPara7(String headerPara7) {
        this.headerPara7 = headerPara7;
    }

    public String getHeaderPara8() {
        return headerPara8;
    }

    public void setHeaderPara8(String headerPara8) {
        this.headerPara8 = headerPara8;
    }

    public String getHeaderPara9() {
        return headerPara9;
    }

    public void setHeaderPara9(String headerPara9) {
        this.headerPara9 = headerPara9;
    }

    public String getHeaderPara10() {
        return headerPara10;
    }

    public void setHeaderPara10(String headerPara10) {
        this.headerPara10 = headerPara10;
    }

    public String getHeaderPara11() {
        return headerPara11;
    }

    public void setHeaderPara11(String headerPara11) {
        this.headerPara11 = headerPara11;
    }

    public String getHeaderPara12() {
        return headerPara12;
    }

    public void setHeaderPara12(String headerPara12) {
        this.headerPara12 = headerPara12;
    }

    public String getHeaderPara13() {
        return headerPara13;
    }

    public void setHeaderPara13(String headerPara13) {
        this.headerPara13 = headerPara13;
    }

    public String getHeaderPara14() {
        return headerPara14;
    }

    public void setHeaderPara14(String headerPara14) {
        this.headerPara14 = headerPara14;
    }

    public String getHeaderPara15() {
        return headerPara15;
    }

    public void setHeaderPara15(String headerPara15) {
        this.headerPara15 = headerPara15;
    }

    public String getPara1() {
        return para1;
    }

    public void setPara1(String para1) {
        this.para1 = para1;
    }

    public String getPara2() {
        return para2;
    }

    public void setPara2(String para2) {
        this.para2 = para2;
    }

    public String getPara3() {
        return para3;
    }

    public void setPara3(String para3) {
        this.para3 = para3;
    }

    public String getPara4() {
        return para4;
    }

    public void setPara4(String para4) {
        this.para4 = para4;
    }

    public String getPara5() {
        return para5;
    }

    public void setPara5(String para5) {
        this.para5 = para5;
    }

    public String getPara6() {
        return para6;
    }

    public void setPara6(String para6) {
        this.para6 = para6;
    }

    public String getPara7() {
        return para7;
    }

    public void setPara7(String para7) {
        this.para7 = para7;
    }

    public String getPara8() {
        return para8;
    }

    public void setPara8(String para8) {
        this.para8 = para8;
    }

    public String getPara9() {
        return para9;
    }

    public void setPara9(String para9) {
        this.para9 = para9;
    }

    public String getPara10() {
        return para10;
    }

    public void setPara10(String para10) {
        this.para10 = para10;
    }

    public String getPara11() {
        return para11;
    }

    public void setPara11(String para11) {
        this.para11 = para11;
    }

    public String getPara12() {
        return para12;
    }

    public void setPara12(String para12) {
        this.para12 = para12;
    }

    public String getPara13() {
        return para13;
    }

    public void setPara13(String para13) {
        this.para13 = para13;
    }

    public String getPara14() {
        return para14;
    }

    public void setPara14(String para14) {
        this.para14 = para14;
    }

    public String getPara15() {
        return para15;
    }

    public void setPara15(String para15) {
        this.para15 = para15;
    }

    public String getPara16() {
        return para16;
    }

    public void setPara16(String para16) {
        this.para16 = para16;
    }

    public String getPara17() {
        return para17;
    }

    public void setPara17(String para17) {
        this.para17 = para17;
    }

    public String getPara18() {
        return para18;
    }

    public void setPara18(String para18) {
        this.para18 = para18;
    }

    public String getPara19() {
        return para19;
    }

    public void setPara19(String para19) {
        this.para19 = para19;
    }

    public String getPara20() {
        return para20;
    }

    public void setPara20(String para20) {
        this.para20 = para20;
    }

    public String getPara21() {
        return para21;
    }

    public void setPara21(String para21) {
        this.para21 = para21;
    }

    public String getPara22() {
        return para22;
    }

    public void setPara22(String para22) {
        this.para22 = para22;
    }

    public String getPara23() {
        return para23;
    }

    public void setPara23(String para23) {
        this.para23 = para23;
    }

    public String getPara24() {
        return para24;
    }

    public void setPara24(String para24) {
        this.para24 = para24;
    }

    public String getPara25() {
        return para25;
    }

    public void setPara25(String para25) {
        this.para25 = para25;
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

    public String getIsMockLine() {
        return isMockLine;
    }

    public void setIsMockLine(String isMockLine) {
        this.isMockLine = isMockLine;
    }

    public String getMockLineUrl() {
        return mockLineUrl;
    }

    public void setMockLineUrl(String mockLineUrl) {
        this.mockLineUrl = mockLineUrl;
    }
}
