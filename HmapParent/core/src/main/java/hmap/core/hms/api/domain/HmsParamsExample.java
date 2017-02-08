package hmap.core.hms.api.domain;

import com.hand.hap.system.dto.BaseDTO;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved.
 * Project Name:HmapParent
 * Package Name:hmap.core.hms.domain
 * Date:2016/8/23
 * Create By:jiguang.sun@hand-china.com
 */

@Table(name="hms_params_example")
public class HmsParamsExample extends BaseDTO {

    @Id
    @GeneratedValue(generator = "UUID")
    private String exampleId;

    @Column
    private String lineId;
    @Column
    private String inputExample;
    @Column
    private String outputExample;



    public String getExampleId() {
        return exampleId;
    }

    public void setExampleId(String exampleId) {
        this.exampleId = exampleId;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getInputExample() {
        return inputExample;
    }

    public void setInputExample(String inputExample) {
        this.inputExample = inputExample;
    }

    public String getOutputExample() {
        return outputExample;
    }

    public void setOutputExample(String outputExample) {
        this.outputExample = outputExample;
    }
}
