/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved.
 * Project Name:HmapParent
 * Package Name:hmap.core.hms.service 
 * Date:2016/8/4 0004
 * Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.uploader.service;

import java.io.IOException;
import java.io.InputStream;

import hmap.core.hms.uploader.dto.UploadFileDTO;
import hmap.core.hms.uploader.dto.UploadFileWithStreamDTO;

public interface IUploaderService {
    public String getName();
    public void setName(String name);
    public UploadFileDTO uploadFile(InputStream in,String fileName,boolean needThumbnail) throws IOException;
    public UploadFileWithStreamDTO uploadFileWithStream(InputStream in,String fileName) throws IOException;
    public UploadFileDTO uploadFile(String directory, InputStream in, String fileName);
    public String upload(String directory, InputStream in, String fileName);
    public void download(String downloadFile,String dst);
}
