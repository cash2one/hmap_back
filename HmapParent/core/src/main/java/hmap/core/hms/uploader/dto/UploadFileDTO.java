/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved.
 * Project Name:hstaffParent
 * Package Name:hstaff.core.file.dto 
 * Date:2016/7/12 0012
 * Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.uploader.dto;

public class UploadFileDTO {
    private String objectUrl;
    private String imgThumbnailUrl;
    private String objectName;
    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getObjectUrl() {
        return objectUrl;
    }

    public void setObjectUrl(String fileUrl) {
        this.objectUrl = fileUrl;
    }

    public String getImgThumbnailUrl() {
        return imgThumbnailUrl;
    }

    public void setImgThumbnailUrl(String imgThumbnailUrl) {
        this.imgThumbnailUrl = imgThumbnailUrl;
    }
}
