package hmap.core.hms.contact.domain;

import com.hand.hap.system.dto.BaseDTO;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "hms_staff")
@Document(indexName = "hms_staff", type = "hms_staff", shards = 1, replicas = 0, refreshInterval = "-1", indexStoreType = "fs")
public class HmsStaff extends BaseDTO {
  //private Long id;

  @Id
  @Field(type = FieldType.String,index = FieldIndex.not_analyzed)
  private String userId;
  @Field(type = FieldType.String,index = FieldIndex.not_analyzed)
  private String userName;
  @org.springframework.data.annotation.Id
  @Field(type = FieldType.String,index = FieldIndex.not_analyzed)
  private String accountNumber;
  @Field(type = FieldType.String,index = FieldIndex.not_analyzed)
  private String position;
  @Field(type = FieldType.String,index = FieldIndex.not_analyzed)
  private Long genderId;
  @Field(type = FieldType.Long,index = FieldIndex.not_analyzed)
  private Long departmentId;
  @Field(type = FieldType.String,index = FieldIndex.not_analyzed)
  private String mobile;
  @Field(type = FieldType.String,index = FieldIndex.not_analyzed)
  private String email;
  @Field(type = FieldType.String,index = FieldIndex.not_analyzed)
  private String avatar;
  @Field(type = FieldType.String,index = FieldIndex.not_analyzed)
  private String wxNumber;
  @Field(type = FieldType.String,index = FieldIndex.not_analyzed)
  private String enableFlag;
  @Field(type = FieldType.String,index = FieldIndex.not_analyzed)
  private Long objectVersionNumber;
  @Field(type = FieldType.String,index = FieldIndex.not_analyzed)
  private String homeTown;
  @Field(type = FieldType.String,index = FieldIndex.not_analyzed)
  private String baseName;
  @Field(type = FieldType.String,index = FieldIndex.not_analyzed)
  private String empStatus;
  @Field(type = FieldType.String,index = FieldIndex.not_analyzed)
  private String unitName;
  @Field(type = FieldType.String,index = FieldIndex.not_analyzed)
  private String rootUnitName;
  @Field(type = FieldType.String,index = FieldIndex.not_analyzed)
  private String namePinyin;
  @Field(type = FieldType.String,index = FieldIndex.not_analyzed)
  private String namePinyinCapital;
  @Field(type = FieldType.String,index = FieldIndex.not_analyzed)
  private String attribute1;
  @Field(type = FieldType.String,index = FieldIndex.not_analyzed)
  private String attribute2;
  @Field(type = FieldType.String,index = FieldIndex.not_analyzed)
  private String attribute3;
  @Field(type = FieldType.String,index = FieldIndex.not_analyzed)
  private String attribute4;
  @Field(type = FieldType.String,index = FieldIndex.not_analyzed)
  private String attribute5;
  @Field(type = FieldType.String,index = FieldIndex.not_analyzed)
  private String attribute6;
  @Field(type = FieldType.String,index = FieldIndex.not_analyzed)
  private String attribute7;
  @Field(type = FieldType.String,index = FieldIndex.not_analyzed)
  private String attribute8;
  @Field(type = FieldType.String,index = FieldIndex.not_analyzed)
  private String attribute9;

  @Override
  public String getAttribute1() {
    return attribute1;
  }

  @Override
  public void setAttribute1(String attribute1) {
    this.attribute1 = attribute1;
  }

  @Override
  public String getAttribute2() {
    return attribute2;
  }

  @Override
  public void setAttribute2(String attribute2) {
    this.attribute2 = attribute2;
  }

  @Override
  public String getAttribute3() {
    return attribute3;
  }

  @Override
  public void setAttribute3(String attribute3) {
    this.attribute3 = attribute3;
  }

  @Override
  public String getAttribute4() {
    return attribute4;
  }

  @Override
  public void setAttribute4(String attribute4) {
    this.attribute4 = attribute4;
  }

  @Override
  public String getAttribute5() {
    return attribute5;
  }

  @Override
  public void setAttribute5(String attribute5) {
    this.attribute5 = attribute5;
  }

  @Override
  public String getAttribute6() {
    return attribute6;
  }

  @Override
  public void setAttribute6(String attribute6) {
    this.attribute6 = attribute6;
  }

  @Override
  public String getAttribute7() {
    return attribute7;
  }

  @Override
  public void setAttribute7(String attribute7) {
    this.attribute7 = attribute7;
  }

  @Override
  public String getAttribute8() {
    return attribute8;
  }

  @Override
  public void setAttribute8(String attribute8) {
    this.attribute8 = attribute8;
  }

  @Override
  public String getAttribute9() {
    return attribute9;
  }

  @Override
  public void setAttribute9(String attribute9) {
    this.attribute9 = attribute9;
  }
//  public Long getId() {
//    return id;
//  }
//
//  public void setId(Long id) {
//    this.id = id;
//  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  public String getPosition() {
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
  }

  public Long getGenderId() {
    return genderId;
  }

  public void setGenderId(Long genderId) {
    this.genderId = genderId;
  }

  public Long getDepartmentId() {
    return departmentId;
  }

  public void setDepartmentId(Long departmentId) {
    this.departmentId = departmentId;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public String getWxNumber() {
    return wxNumber;
  }

  public void setWxNumber(String wxNumber) {
    this.wxNumber = wxNumber;
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

  public String getHomeTown() {
    return homeTown;
  }

  public void setHomeTown(String homeTown) {
    this.homeTown = homeTown;
  }

  public String getBaseName() {
    return baseName;
  }

  public void setBaseName(String baseName) {
    this.baseName = baseName;
  }

  public String getEmpStatus() {
    return empStatus;
  }

  public void setEmpStatus(String empStatus) {
    this.empStatus = empStatus;
  }

  public String getUnitName() {
    return unitName;
  }

  public void setUnitName(String unitName) {
    this.unitName = unitName;
  }

  public String getRootUnitName() {
    return rootUnitName;
  }

  public void setRootUnitName(String rootUnitName) {
    this.rootUnitName = rootUnitName;
  }

public String getNamePinyin() {
	return namePinyin;
}

public void setNamePinyin(String namePinyin) {
	this.namePinyin = namePinyin;
}

public String getNamePinyinCapital() {
	return namePinyinCapital;
}

public void setNamePinyinCapital(String namePinyinCapital) {
	this.namePinyinCapital = namePinyinCapital;
}


  @Override
  public String toString() {
    return "HmsStaff{" +
            "userId='" + userId + '\'' +
            ", userName='" + userName + '\'' +
            ", accountNumber='" + accountNumber + '\'' +
            ", position='" + position + '\'' +
            ", genderId=" + genderId +
            ", departmentId=" + departmentId +
            ", mobile='" + mobile + '\'' +
            ", email='" + email + '\'' +
            ", avatar='" + avatar + '\'' +
            ", wxNumber='" + wxNumber + '\'' +
            ", enableFlag='" + enableFlag + '\'' +
            ", objectVersionNumber=" + objectVersionNumber +
            ", homeTown='" + homeTown + '\'' +
            ", baseName='" + baseName + '\'' +
            ", empStatus='" + empStatus + '\'' +
            ", unitName='" + unitName + '\'' +
            ", rootUnitName='" + rootUnitName + '\'' +
            ", namePinyin='" + namePinyin + '\'' +
            ", namePinyinCapital='" + namePinyinCapital + '\'' +
            ", attribute1='" + attribute1 + '\'' +
            ", attribute2='" + attribute2 + '\'' +
            ", attribute3='" + attribute3 + '\'' +
            ", attribute4='" + attribute4 + '\'' +
            ", attribute5='" + attribute5 + '\'' +
            ", attribute6='" + attribute6 + '\'' +
            ", attribute7='" + attribute7 + '\'' +
            ", attribute8='" + attribute8 + '\'' +
            ", attribute9='" + attribute9 + '\'' +
            '}';
  }
}
