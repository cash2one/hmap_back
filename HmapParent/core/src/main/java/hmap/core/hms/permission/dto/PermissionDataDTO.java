/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved.
 * Project Name:HmapParent
 * Package Name:hmap.core.hms.dto 
 * Date:2016/11/19 0019
 * Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.permission.dto;

public class PermissionDataDTO {
    private String dataType;
    private String dataValue;
    private String dataId;
    private String dataIcon;

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

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getDataIcon() {
        return dataIcon;
    }

    public void setDataIcon(String dataIcon) {
        this.dataIcon = dataIcon;
    }
}
