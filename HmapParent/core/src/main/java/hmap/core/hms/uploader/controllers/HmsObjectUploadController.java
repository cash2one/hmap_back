/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved. Project
 * Name:hstaffParent Package Name:hstaff.core.file.controllers Date:2016/7/12 0012 Create
 * By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.uploader.controllers;

import com.codahale.metrics.annotation.Timed;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hmap.core.hms.uploader.dto.UploadFileDTO;
import hmap.core.hms.uploader.exception.HmsUploadObjectInvalidException;
import hmap.core.hms.system.service.IHmsSystemConfigService;
import hmap.core.hms.uploader.service.IHmsUploadObjectService;
import hmap.core.util.ResponseFormData;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;

@Controller
public class HmsObjectUploadController extends BaseController {
  @Autowired
  IHmsUploadObjectService hmsUploadObjectService;
  @Autowired
  IHmsSystemConfigService hmsSystemConfigService;

  private org.slf4j.Logger logger = LoggerFactory.getLogger(HmsObjectUploadController.class);

  @ResponseBody
  @RequestMapping(value = "/api/{uploader}/objectUpload", method = RequestMethod.POST)
  @Timed
  public ResponseData objectUpload(@PathVariable String uploader,MultipartFile file) {
    UploadFileDTO upd = null;
    ResponseData responseData = null;
    try {
      upd = hmsUploadObjectService.uploadFile(uploader,file,false);
      responseData = new ResponseData(Arrays.asList(upd));
    } catch (IOException e) {
      logger.error("文件上传错误:" + e.getMessage());
      e.printStackTrace();
      responseData = new ResponseData(false);
    }
    return responseData;
  }
  @ResponseBody
  @RequestMapping(value = "/api/{uploader}/imageUpload", method = RequestMethod.POST)
  @Timed
  public ResponseData imageUpload(@PathVariable String uploader,MultipartFile file)
      throws HmsUploadObjectInvalidException {
    UploadFileDTO upd = null;
    ResponseData responseData = null;
    this.authenticImgFileType(file.getOriginalFilename());
    try {
      upd = hmsUploadObjectService.uploadFile(uploader,file);
      responseData = new ResponseData(Arrays.asList(upd));
    } catch (IOException e) {
      logger.error("文件上传错误:" + e.getMessage());
      e.printStackTrace();
      responseData = new ResponseData(false);
    }
    return responseData;
  }
  @ResponseBody
  @RequestMapping(value = "/api/{uploader}/imageUploadNoThumbnail", method = RequestMethod.POST)
  @Timed
  public ResponseData imgUploadWithoutThumbnail(@PathVariable String uploader,MultipartFile file) {
    UploadFileDTO upd = null;
    ResponseData responseData = null;
    try {
      upd = hmsUploadObjectService.uploadFile(uploader,file, false);
      responseData = new ResponseData(Arrays.asList(upd));
    } catch (IOException e) {
      logger.error("文件上传错误:" + e.getMessage());
      e.printStackTrace();
      responseData = new ResponseData(false);
    }
    return responseData;
  }

  @ResponseBody
  @RequestMapping(value = "/api/objectUpload", method = RequestMethod.POST)
  @Timed
  public ResponseData objectUploadDefault(MultipartFile file) {
    UploadFileDTO upd = null;
    ResponseData responseData = null;
    try {
      upd = hmsUploadObjectService.uploadFile(file,false);
      responseData = new ResponseData(Arrays.asList(upd));
    } catch (IOException e) {
      logger.error("文件上传错误:" + e.getMessage());
      e.printStackTrace();
      responseData = new ResponseData(false);
    }
    return responseData;
  }
  @ResponseBody
  @RequestMapping(value = "/i/api/objectUpload", method = RequestMethod.POST)
  @Timed
  public ResponseFormData iObjectUploadDefault(MultipartFile file) {
    UploadFileDTO upd = null;
    ResponseFormData responseFormData = null;
    try {
      upd = hmsUploadObjectService.uploadFile(file);
      responseFormData = new ResponseFormData(upd);
    } catch (IOException e) {
      logger.error("文件上传错误:" + e.getMessage());
      e.printStackTrace();
      responseFormData = new ResponseFormData(false);
    }
    return responseFormData;
  }

  @ResponseBody
  @RequestMapping(value = "/api/imageUpload", method = RequestMethod.POST)
  @Timed
  public ResponseData imageUploadDefault(MultipartFile file)
      throws HmsUploadObjectInvalidException {
    UploadFileDTO upd = null;
    ResponseData responseData = null;
    this.authenticImgFileType(file.getOriginalFilename());
    try {
      upd = hmsUploadObjectService.uploadFile(file);
      responseData = new ResponseData(Arrays.asList(upd));
    } catch (IOException e) {
      logger.error("文件上传错误:" + e.getMessage());
      e.printStackTrace();
      responseData = new ResponseData(false);
    }
    return responseData;
  }
  @ResponseBody
  @RequestMapping(value = "/api/imageUploadNoThumbnail", method = RequestMethod.POST)
  @Timed
  public ResponseData imgUploadWithoutThumbnailDefault(MultipartFile file) {
    UploadFileDTO upd = null;
    ResponseData responseData = null;
    try {
      upd = hmsUploadObjectService.uploadFile(file, false);
      responseData = new ResponseData(Arrays.asList(upd));
    } catch (IOException e) {
      logger.error("文件上传错误:" + e.getMessage());
      e.printStackTrace();
      responseData = new ResponseData(false);
    }
    return responseData;
  }
  @ResponseBody
  @RequestMapping(value = "/api/objectUploadLocal", method = RequestMethod.POST)
  @Timed
  public ResponseData objectUploadLocal(MultipartFile file,HttpServletRequest request) {
    UploadFileDTO upd = null;
    ResponseData responseFormData = null;
    try {
      upd = hmsUploadObjectService.uploadFileLocal(file,request.getParameter("url"));
      responseFormData = new ResponseData(Arrays.asList(upd));
    } catch (IOException e) {
      logger.error("文件上传错误:" + e.getMessage());
      e.printStackTrace();
      responseFormData = new ResponseData(false);
    }
    return responseFormData;
  }
  @ResponseBody
  @RequestMapping(value = "/i/api/objectUploadLocal", method = RequestMethod.POST)
  @Timed
  public ResponseData iObjectUploadLocal(MultipartFile file,HttpServletRequest request) {
    UploadFileDTO upd = null;
    ResponseData responseFormData = null;
    try {
      upd = hmsUploadObjectService.uploadFileLocal(file,request.getParameter("url"));
      responseFormData = new ResponseData(Arrays.asList(upd));
    } catch (IOException e) {
      logger.error("文件上传错误:" + e.getMessage());
      e.printStackTrace();
      responseFormData = new ResponseData(false);
    }
    return responseFormData;
  }
  private void  authenticImgFileType(String fileName) throws HmsUploadObjectInvalidException {
    boolean result=hmsUploadObjectService.authenticFileExtName(fileName,"img");
    if(!result){
      Object[] parameters=new String[]{hmsSystemConfigService.getConfigValue("upload.imgFile")};
      throw new HmsUploadObjectInvalidException(HmsUploadObjectInvalidException.EXCEPTION_CODE,HmsUploadObjectInvalidException.INVALIDA_FILE,parameters);
    }

  }

  private void  authenticOtherFileType(String fileName) throws HmsUploadObjectInvalidException {
    boolean result= hmsUploadObjectService.authenticFileExtName(fileName,"other");
    if(!result){
      Object[] parameters=new String[]{hmsSystemConfigService.getConfigValue("upload.otherFile")};
      throw new HmsUploadObjectInvalidException(HmsUploadObjectInvalidException.EXCEPTION_CODE,HmsUploadObjectInvalidException.INVALIDA_FILE,parameters);
    }
  }

  private void  authenticTemplateFileType(String fileName) throws HmsUploadObjectInvalidException {
    boolean result= hmsUploadObjectService.authenticFileExtName(fileName,"template");
    if(!result){
      Object[] parameters=new String[]{hmsSystemConfigService.getConfigValue("upload.otherFile")};
      throw new HmsUploadObjectInvalidException(HmsUploadObjectInvalidException.EXCEPTION_CODE,HmsUploadObjectInvalidException.INVALIDA_FILE,parameters);
    }
  }

  private void  authenticStaticHtmlFileType(String fileName) throws HmsUploadObjectInvalidException {
    boolean result= hmsUploadObjectService.authenticFileExtName(fileName,"staticHtmlDir");
    if(!result){
      Object[] parameters=new String[]{hmsSystemConfigService.getConfigValue("upload.otherFile")};
      throw new HmsUploadObjectInvalidException(HmsUploadObjectInvalidException.EXCEPTION_CODE,HmsUploadObjectInvalidException.INVALIDA_FILE,parameters);
    }
  }

}
