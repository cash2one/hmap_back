/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved. Project Name:hmap
 * Package Name:hmap.core.hms.service.impl Date:2016/7/7 0007 Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.uploader.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.StringUtil;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;

import hmap.core.hms.uploader.domain.HmsUploadObject;
import hmap.core.hms.uploader.dto.UploadFileDTO;
import hmap.core.hms.uploader.dto.UploadFileWithStreamDTO;
import hmap.core.hms.uploader.exception.HmsUploadObjectInvalidException;
import hmap.core.hms.uploader.mapper.HmsUploadObjectMapper;
import hmap.core.hms.system.service.IHmsSystemConfigService;
import hmap.core.hms.uploader.service.IHmsUploadObjectService;
import hmap.core.hms.uploader.service.IUploaderManger;

@Service
public class HmsUploadObjectServiceImpl extends BaseServiceImpl<HmsUploadObject>
    implements IHmsUploadObjectService {

  @Autowired
  IUploaderManger uploaderManger;
  @Autowired
  HmsUploadObjectMapper hmsUploadObjectMapper;

  @Autowired
  IHmsSystemConfigService hmsSystemConfigService;

  @Override
  public UploadFileDTO uploadFile(MultipartFile file, boolean needThumbnail) throws IOException {
    String fileName = file.getOriginalFilename();
    InputStream inputStream = file.getInputStream();
    UploadFileDTO upd =
        uploaderManger.getUploader().uploadFile(inputStream, fileName, needThumbnail);
    return upd;
  }

  @Override
  public UploadFileDTO uploadFile(String uploader, MultipartFile file, boolean needThumbnail)
      throws IOException {
    String fileName = file.getOriginalFilename();
    InputStream inputStream = file.getInputStream();
    UploadFileDTO upd =
        uploaderManger.getUploader(uploader).uploadFile(inputStream, fileName, needThumbnail);
    return upd;
  }

  @Override
  public UploadFileDTO uploadFile(String uploader, MultipartFile file) throws IOException {
    UploadFileDTO upd = this.uploadFile(uploader, file, true);
    return upd;
  }

  @Override
  public UploadFileDTO uploadFile(MultipartFile file) throws IOException {
    String fileName = file.getOriginalFilename();
    InputStream inputStream = file.getInputStream();
    UploadFileDTO upd = uploaderManger.getUploader().uploadFile(inputStream, fileName, true);
    return upd;
  }

  @Override
  public UploadFileDTO uploadFile(InputStream in, String fileName) throws IOException {
    return uploaderManger.getUploader().uploadFile(in, fileName, false);
  }

  @Override public UploadFileWithStreamDTO uploadFileWithStream(MultipartFile file)
      throws IOException {
    String fileName = file.getOriginalFilename();
    InputStream inputStream = file.getInputStream();
    return uploaderManger.getUploader().uploadFileWithStream(inputStream,fileName);
  }

  public HmsUploadObject saveUploadObject(IRequest request, HmsUploadObject hmsUploadObject)
      throws HmsUploadObjectInvalidException {
    if (StringUtil.isEmpty(hmsUploadObject.getObjectName())) {
      throw new HmsUploadObjectInvalidException("Error", "无效的对象名称");
    }
    hmsUploadObject.setId(UUID.randomUUID().toString());
    int index = hmsUploadObject.getObjectName().lastIndexOf(".");
    if (index >= 0) {
      String extName = hmsUploadObject.getObjectName().substring(index + 1);
      hmsUploadObject.setObjectType(extName);
    }
    // hmsUploadObject.setObjectUrl(hmsUploadObject.getObjectName());
    hmsUploadObject.setEnable("Y");
    this.insertSelective(request, hmsUploadObject);
    return hmsUploadObject;
  }

  public HmsUploadObject disableUploadObject(IRequest request, HmsUploadObject hmsUploadObject) {
    hmsUploadObject.setEnable("N");
    hmsUploadObject = this.updateByPrimaryKey(request, hmsUploadObject);
    return hmsUploadObject;
  }

  public List<HmsUploadObject> selectByFunctionNameAndDataId(String functionName, String dataId) {
    List<HmsUploadObject> hmsUploadObjectList =
        hmsUploadObjectMapper.selectByFunctionNameAndDataId(functionName, dataId);
    return hmsUploadObjectList;
  }

  public boolean authenticFileExtName(String fileName, String type) {
    boolean result=true;
    int index = fileName.lastIndexOf(".");
    String extName = fileName.substring(index + 1);
    // 上传图片类型
    //TODO 需要重构
    if (type.equalsIgnoreCase("img")) {
      String config = hmsSystemConfigService.selectByConfigKey("upload.imgFile").getConfigValue();
      if (StringUtils.isNotEmpty(config)&&config.indexOf(extName)==-1) {
        result=false;
      }
    }
    else if(type.equalsIgnoreCase("other")){
      String config = hmsSystemConfigService.selectByConfigKey("upload.otherFile").getConfigValue();
      if (StringUtils.isNotEmpty(config)&&config.indexOf(extName)==-1) {
        result=false;
      }
    }
    else if(type.equalsIgnoreCase("app")){
      String config = hmsSystemConfigService.selectByConfigKey("upload.appFile").getConfigValue();
      if (StringUtils.isNotEmpty(config)&&config.indexOf(extName)==-1) {
        result=false;
      }
    }
    return result;
  }

  @Override
  public UploadFileDTO uploadFileLocal(MultipartFile file, String url)
          throws IOException {
    String basePath = hmsSystemConfigService.getConfigValue("upload.basePath");
    String downBasePath = hmsSystemConfigService.getConfigValue("download.basePath");
    UploadFileDTO upd = new UploadFileDTO();
    String uploadUrl = "";
    if(url==null||url.trim().equals("")){
      uploadUrl = basePath+"hotUpdate";
    }else{
      uploadUrl = basePath+url;
    }
    File f = new File(uploadUrl);
    if(!f.isDirectory()){
      f.mkdirs();
    }
    String fileName = file.getOriginalFilename();
    InputStream is = file.getInputStream();
    FileOutputStream fos = new FileOutputStream(uploadUrl+File.separator+fileName);
    byte buffer[] = new byte[1024];
    int length = 0;
    while((length = is.read(buffer))>0){
      fos.write(buffer, 0, length);
    }
    is.close();
    fos.close();
    if(url==null||url.trim().equals("")){
      upd.setObjectUrl(downBasePath+fileName);
    }else {
      upd.setObjectUrl(downBasePath +url+ "/" + fileName);
    }
    return upd;
  }
}
