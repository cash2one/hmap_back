/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.hms.domain Date:2016/9/22 0022 Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.system.domain;

import com.hand.hap.mybatis.annotation.Condition;
import com.hand.hap.system.dto.BaseDTO;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Table(name = "hms_system_config")
@UniqueConstraint(columnNames={"configKey"})
public class HmsSystemConfig extends BaseDTO {
  @Id
  @Column
  @GeneratedValue(generator = "UUID")
  private String id;
  @Column
  @NotNull

  @Condition(operator = LIKE)
  private String configKey;
  @Column
  @NotNull
  private String configValue;

  @Column
  @NotEmpty
  @Condition(operator = LIKE)
  private String configDesc;
  @Column
  @NotEmpty
  private String configLevel;
  @Column
  @NotEmpty
  private String enable;

  public String getConfigValue() {
    return configValue;
  }

  public void setConfigValue(String configValue) {
    this.configValue = configValue;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getConfigKey() {
    return configKey;
  }

  public void setConfigKey(String configKey) {
    this.configKey = configKey;
  }

  public String getConfigDesc() {
    return configDesc;
  }

  public void setConfigDesc(String configDesc) {
    this.configDesc = configDesc;
  }

  public String getEnable() {
    return enable;
  }

  public void setEnable(String enable) {
    this.enable = enable;
  }

  public String getConfigLevel() {
    return configLevel;
  }

  public void setConfigLevel(String configLevel) {
    this.configLevel = configLevel;
  }
}
