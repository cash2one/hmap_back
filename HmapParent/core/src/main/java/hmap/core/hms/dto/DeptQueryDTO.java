/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.hms.dto Date:2016/11/10 0010 Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.dto;

import hmap.core.util.PageRequest;

public class DeptQueryDTO extends PageRequest {
  private Long deptNumber;

  public Long getDeptNumber() {
    return deptNumber;
  }

  public void setDeptNumber(Long deptNumber) {
    this.deptNumber = deptNumber;
  }
}
