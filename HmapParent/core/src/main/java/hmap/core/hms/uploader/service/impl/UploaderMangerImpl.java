/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.hms.service.impl Date:2016/8/4 0004 Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.uploader.service.impl;

import hmap.core.hms.system.service.IHmsSystemConfigService;
import hmap.core.hms.uploader.service.IUploaderManger;
import hmap.core.hms.uploader.service.IUploaderService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;

public class UploaderMangerImpl implements IUploaderManger {
  @Autowired
  IHmsSystemConfigService systemConfigService;

  private HashMap<String, IUploaderService> uploaderServiceMap = new HashMap();
  private List<IUploaderService> uploads;


  @Override
  public IUploaderService getUploader() {
    String defaultUploader =
        systemConfigService.selectByConfigKey("default.uploader").getConfigValue();
    return uploaderServiceMap.get(defaultUploader);
  }
  public IUploaderService getUploader(String uploader) {
    return uploaderServiceMap.get(uploader);
  }
  public List<IUploaderService> getUploads() {
    return uploads;
  }

  public void setUploads(List<IUploaderService> uploads) {
    this.uploads = uploads;
    for (IUploaderService upload : uploads) {
      uploaderServiceMap.put(upload.getName(), upload);
    }
  }
}
