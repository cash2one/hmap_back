/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved. Project
 * Name:hstaffParent Package Name:hstaff.core.file.dto Date:2016/7/13 0013 Create
 * By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.uploader.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hand.hap.system.dto.BaseDTO;

@Table(name = "hms_upload_object")
public class HmsUploadObject extends BaseDTO {
  @Id
  @GeneratedValue(generator = "UUID")
  private String id;
  private String empNo;
  private String objectName;
  private String objectType;
  private String objectUrl;
  private String functionName;
  private String dataId;
  private String enable;


public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getEmpNo() {
    return empNo;
  }

  public void setEmpNo(String empNo) {
    this.empNo = empNo;
  }

  public String getObjectName() {
    return objectName;
  }

  public void setObjectName(String objectName) {
    this.objectName = objectName;
  }

  public String getObjectType() {
    return objectType;
  }

  public void setObjectType(String objectType) {
    this.objectType = objectType;
  }

  public String getFunctionName() {
    return functionName;
  }

  public void setFunctionName(String functionName) {
    this.functionName = functionName;
  }

  public String getDataId() {
    return dataId;
  }

  public void setDataId(String dataId) {
    this.dataId = dataId;
  }

  public String getObjectUrl() {
    return objectUrl;
  }

  public void setObjectUrl(String objectUrl) {
    this.objectUrl = objectUrl;
  }

  public String getEnable() {
    return enable;
  }

  public void setEnable(String enable) {
    this.enable = enable;
  }
}
