/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved.
 * Project Name:HmapParent
 * Package Name:hmap.core.hms.service.impl 
 * Date:2016/8/4 0004
 * Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.uploader.service;

public interface IUploaderManger {
    IUploaderService getUploader();
    IUploaderService getUploader(String uploader);
}
