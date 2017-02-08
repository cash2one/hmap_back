/**
 * Copyright (c) 2017. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.hms.distribution.controllers Date:2017/1/12 0012 Create
 * By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.distribution.controllers;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.codahale.metrics.annotation.Timed;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;

import hmap.core.hms.distribution.dto.HmsAppDistributionDTO;
import hmap.core.hms.distribution.service.IHmsAppDistributionService;
import hmap.core.hms.uploader.exception.HmsUploadObjectInvalidException;
import hmap.core.hms.system.service.ILogService;

@Controller
public class HmsAppUploaderController extends BaseController {


  @Autowired
  ILogService iLogService;
  @Autowired
  IHmsAppDistributionService iHmsAppDistributionService;

  @ResponseBody
  @RequestMapping(value = "/api/appUpload", method = RequestMethod.POST)
  @Timed
  public ResponseData appUpload(MultipartFile file) throws HmsUploadObjectInvalidException {
    HmsAppDistributionDTO dto = null;
    ResponseData responseData = null;
    try {
      dto = iHmsAppDistributionService.uploadAndAnalyseAppFile(file);
      responseData = new ResponseData(Arrays.asList(dto));
    } catch (IOException e) {
      iLogService.ctrlLogError("文件上传错误:" + e.getMessage());
      e.printStackTrace();
      responseData = new ResponseData(false);
    }
    return responseData;
  }


}
