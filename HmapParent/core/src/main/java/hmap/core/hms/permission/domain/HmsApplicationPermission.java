package hmap.core.hms.permission.domain;

import com.hand.hap.system.dto.BaseDTO;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author yahang.liu@hand-china.com
 * @version 2016/11/24
 */
@Table(name = "hms_application_permission")
public class HmsApplicationPermission extends BaseDTO {
  @Id
  @GeneratedValue(generator = "UUID")
  private String id;
  private String dataId;
  private String visibilityType;
  private String dataType;
  private String dataValue;
  private String dataKey;
  private String valueId;
  private String valueIcon;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getDataId() {
    return dataId;
  }

  public void setDataId(String dataId) {
    this.dataId = dataId;
  }

  public String getVisibilityType() {
    return visibilityType;
  }

  public void setVisibilityType(String visibilityType) {
    this.visibilityType = visibilityType;
  }

  public String getDataType() {
    return dataType;
  }

  public void setDataType(String dataType) {
    this.dataType = dataType;
  }

  public String getDataValue() {
    return dataValue;
  }

  public void setDataValue(String dataValue) {
    this.dataValue = dataValue;
  }

  public String getDataKey() {
    return dataKey;
  }

  public void setDataKey(String dataKey) {
    this.dataKey = dataKey;
  }

  public String getValueId() {
    return valueId;
  }

  public void setValueId(String valueId) {
    this.valueId = valueId;
  }

  public String getValueIcon() {
    return valueIcon;
  }

  public void setValueIcon(String valueIcon) {
    this.valueIcon = valueIcon;
  }

  @Override
  public String toString() {
    return "HmsApplicationPermission{" +
            "id='" + id + '\'' +
            ", dataId='" + dataId + '\'' +
            ", visibilityType='" + visibilityType + '\'' +
            ", dataType='" + dataType + '\'' +
            ", dataValue='" + dataValue + '\'' +
            ", dataKey='" + dataKey + '\'' +
            ", valueId='" + valueId + '\'' +
            ", valueIcon='" + valueIcon + '\'' +
            '}';
  }
}
