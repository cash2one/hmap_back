/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved. Project
 * Name:hstaffParent Package Name:hstaff.core.staff.dto Date:2016/7/21 0021 Create
 * By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.contact.dto;

import com.hand.hap.system.dto.BaseDTO;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "hrms_staff_avatar")
public class HmsStaffAvatarDTO extends BaseDTO {
  @Id
  private String id;
  private String empNo;
  private String originalAvatar;
  private String compressedAvatar;

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

  public String getOriginalAvatar() {
    return originalAvatar;
  }

  public void setOriginalAvatar(String originalAvatar) {
    this.originalAvatar = originalAvatar;
  }

  public String getCompressedAvatar() {
    return compressedAvatar;
  }

  public void setCompressedAvatar(String compressedAvatar) {
    this.compressedAvatar = compressedAvatar;
  }
}
