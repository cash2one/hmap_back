/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved.
 * Project Name:HmapParent
 * Package Name:hmap.core.hms.service.impl 
 * Date:2016/9/24 0024
 * Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.uploader.service.impl;

import hmap.core.hms.uploader.dto.UploadFileDTO;
import hmap.core.hms.uploader.service.IUploaderService;

import java.io.IOException;
import java.io.InputStream;

public abstract  class AbstractUploaderServiceImpl implements IUploaderService {
    private String name;
    public abstract UploadFileDTO uploadFile(InputStream in,String fileName,boolean needThumbnail) throws
        IOException;
    public abstract UploadFileDTO uploadFile(String directory, InputStream in, String fileName);
    public abstract String upload(String directory, InputStream in, String fileName);

    public abstract void download(String downloadFile,String dst);

    public String getName() {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
