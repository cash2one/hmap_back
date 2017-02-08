package hmap.core.hms.contact.domain;

import com.hand.hap.system.dto.BaseDTO;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "hms_dept")
@Document(indexName = "hms_dept", type = "hms_dept", shards = 1, replicas = 0,
    refreshInterval = "-1", indexStoreType = "fs")
public class HmsDept extends BaseDTO {
  @Id
  @org.springframework.data.annotation.Id
  @Field(type = FieldType.String, store = true, index = FieldIndex.not_analyzed)
  private String deptId;
  @Field(type = FieldType.String, store = true, index = FieldIndex.not_analyzed)
  private String deptName;
  @Field(type = FieldType.Long, store = true, index = FieldIndex.not_analyzed)
  private Long deptNumber;
  private Long parentDeptNumber;
  private String sourceCode;
  private String sourceLineId;
  private String sourceReference;
  private String enableFlag;
  private Long objectVersionNumber;
  private Long totalStaffNumber;
  @Field(type = FieldType.String, index = FieldIndex.not_analyzed, store = true)
  private String deptPinyin;
  @Field(type = FieldType.String, index = FieldIndex.not_analyzed, store = true)
  private String deptPinyinCapital;

  public Long getTotalStaffNumber() {
    return totalStaffNumber;
  }

  public void setTotalStaffNumber(Long totalStaffNumber) {
    this.totalStaffNumber = totalStaffNumber;
  }

  public String getDeptId() {
    return deptId;
  }

  public void setDeptId(String deptId) {
    this.deptId = deptId;
  }

  public String getDeptName() {
    return deptName;
  }

  public void setDeptName(String deptName) {
    this.deptName = deptName;
  }

  public Long getDeptNumber() {
    return deptNumber;
  }

  public void setDeptNumber(Long deptNumber) {
    this.deptNumber = deptNumber;
  }

  public Long getParentDeptNumber() {
    return parentDeptNumber;
  }

  public void setParentDeptNumber(Long parentDeptNumber) {
    this.parentDeptNumber = parentDeptNumber;
  }

  public String getSourceCode() {
    return sourceCode;
  }

  public void setSourceCode(String sourceCode) {
    this.sourceCode = sourceCode;
  }

  public String getSourceLineId() {
    return sourceLineId;
  }

  public void setSourceLineId(String sourceLineId) {
    this.sourceLineId = sourceLineId;
  }

  public String getSourceReference() {
    return sourceReference;
  }

  public void setSourceReference(String sourceReference) {
    this.sourceReference = sourceReference;
  }

  public String getEnableFlag() {
    return enableFlag;
  }

  public void setEnableFlag(String enableFlag) {
    this.enableFlag = enableFlag;
  }

  public Long getObjectVersionNumber() {
    return objectVersionNumber;
  }

  public void setObjectVersionNumber(Long objectVersionNumber) {
    this.objectVersionNumber = objectVersionNumber;
  }

  public String getDeptPinyin() {
    return deptPinyin;
  }

  public void setDeptPinyin(String deptPinyin) {
    this.deptPinyin = deptPinyin;
  }

  public String getDeptPinyinCapital() {
    return deptPinyinCapital;
  }

  public void setDeptPinyinCapital(String deptPinyinCapital) {
    this.deptPinyinCapital = deptPinyinCapital;
  }
}
