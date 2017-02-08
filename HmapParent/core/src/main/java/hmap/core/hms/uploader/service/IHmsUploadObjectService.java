/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved. Project
 * Name:hstaffParent Package Name:hstaff.core.file.service Date:2016/7/7 0007 Create
 * By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.uploader.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.IBaseService;

import hmap.core.hms.uploader.domain.HmsUploadObject;
import hmap.core.hms.uploader.dto.UploadFileDTO;
import hmap.core.hms.uploader.dto.UploadFileWithStreamDTO;
import hmap.core.hms.uploader.exception.HmsUploadObjectInvalidException;


public interface IHmsUploadObjectService extends IBaseService<HmsUploadObject> {

  public UploadFileDTO uploadFile(MultipartFile file, boolean needThumbnail) throws IOException;

  public UploadFileDTO uploadFile(MultipartFile file)
      throws IOException;
  public UploadFileDTO uploadFile(String uploader,MultipartFile file) throws IOException;
  public UploadFileDTO uploadFile(String uploader,MultipartFile file, boolean needThumbnail) throws IOException;
  public UploadFileDTO uploadFile(InputStream in, String fileName)
	      throws IOException;
  public UploadFileWithStreamDTO uploadFileWithStream(MultipartFile file)
        throws IOException;
  public HmsUploadObject saveUploadObject(IRequest request, HmsUploadObject hrmsUploadObject)
      throws HmsUploadObjectInvalidException;

  public HmsUploadObject disableUploadObject(IRequest request, HmsUploadObject hrmsUploadObject);

  public List<HmsUploadObject> selectByFunctionNameAndDataId(String functionName, String dataId);

  public boolean authenticFileExtName(String fileName, String type);
  public UploadFileDTO uploadFileLocal(MultipartFile file, String url)
          throws IOException;
}
