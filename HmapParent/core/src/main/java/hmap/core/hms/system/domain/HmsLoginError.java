/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.hms.domain Date:2016/11/8 0008 Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.system.domain;

import com.hand.hap.system.dto.BaseDTO;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "hms_login_error")
public class HmsLoginError extends BaseDTO {
  @Id
  @GeneratedValue(generator = "UUID")
  private String id;
  @Column
  private String userIp;
  @Column
  private Integer errTimes;
  @Column
  private Date errDate;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUserIp() {
    return userIp;
  }

  public void setUserIp(String userIp) {
    this.userIp = userIp;
  }

  public Integer getErrTimes() {
    return errTimes;
  }

  public void setErrTimes(Integer errTimes) {
    this.errTimes = errTimes;
  }

  public Date getErrDate() {
    return errDate;
  }

  public void setErrDate(Date errDate) {
    this.errDate = errDate;
  }
}
