/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.hms.controllers Date:2016/7/31 0031 Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.system.controllers;

import com.codahale.metrics.annotation.Timed;
import com.hand.hap.account.exception.UserException;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hmap.core.hms.system.domain.HmsThirdpartyApp;
import hmap.core.hms.api.exception.HmsApiException;
import hmap.core.hms.system.service.IHmsThirdpartyAppService;
import hmap.core.util.PageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/api")
public class HmsThirdpartyAppController extends BaseController {
  private final Logger log = LoggerFactory.getLogger(HmsThirdpartyAppController.class);
  @Autowired
  IHmsThirdpartyAppService thirdpartyAppService;

  @RequestMapping(value = "/thirdpartyApp", method = RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  @Timed
  public ResponseData createThirdpartyApp(HttpServletRequest request, @RequestBody(required = false) HmsThirdpartyApp thirdpartyApp) {
    IRequest iRequest = createRequestContext(request);
    HmsThirdpartyApp e = thirdpartyAppService.insert(iRequest, thirdpartyApp);
    return new ResponseData(Arrays.asList(e));
  }

  @RequestMapping(value = "/thirdpartyApp/{id}", method = RequestMethod.GET)
  @ResponseBody
  public ResponseData getThirdpartyApp(HttpServletRequest request, @PathVariable String id)
      throws UserException {
    IRequest iRequest = createRequestContext(request);
    HmsThirdpartyApp e = thirdpartyAppService.selectById(id);
    return new ResponseData(Arrays.asList(e));
  }

  @RequestMapping(value = "/thirdpartyApp", method = RequestMethod.GET)
  @ResponseBody
  @Timed
  public ResponseData getAllThirdpartyApp(HttpServletRequest request, PageRequest pr)
      throws HmsApiException {
    IRequest iRequest = createRequestContext(request);
    List<HmsThirdpartyApp> list =
        thirdpartyAppService.select(iRequest, null, pr.getPage(), pr.getPagesize());
    return new ResponseData(list);
  }

  @RequestMapping(value = "/thirdpartyApp", method = RequestMethod.PUT,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  @Timed
  public ResponseData updateThirdpartyApp(HttpServletRequest request,
      @RequestBody(required = false) HmsThirdpartyApp thirdpartyApp) {
    IRequest iRequest = createRequestContext(request);
    HmsThirdpartyApp e = thirdpartyAppService.updateByPrimaryKey(iRequest, thirdpartyApp);
    return new ResponseData(Arrays.asList(e));
  }

}
