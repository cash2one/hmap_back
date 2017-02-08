package hmap.core.job.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;

import hmap.core.hms.system.service.ILogService;
import net.sf.json.JSONObject;

/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.job.controllers Date:2016/11/29 Create By:chuanqi.cao@hand-china.com
 */
@Controller
@RequestMapping({"/api/job"})
public class JobSampleController extends BaseController {

  @Autowired
  ILogService iLogService;

  @RequestMapping(value = {"/sample"}, method = {RequestMethod.POST})
  @ResponseBody
  public ResponseData sample(HttpServletRequest request,
      @RequestBody(required = false) JSONObject params) {
    iLogService.jobLogInfo("executing sample log");
    ResponseData responseData = new ResponseData(true);
    iLogService.jobLogInfo("executed sample log");
    return responseData;
  }
}
