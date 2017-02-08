package hmap.core.hms.api.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.codahale.metrics.annotation.Timed;
import hmap.core.hms.api.exception.HmsApiException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hand.hap.system.controllers.BaseController;

import hmap.core.hms.api.dto.HeaderAndLineDTO;
import hmap.core.hms.api.service.IApiService;
import hmap.core.hms.api.service.IHmsHeaderService;
import net.sf.json.JSONObject;


/**
 * Created by user on 2016/7/27.
 */
@Controller
public class HmsApiController extends BaseController {

  private final Logger logger = LoggerFactory.getLogger(HmsApiController.class);

  @Autowired
  IHmsHeaderService headerService;

  @Resource(name = "plsqlBean")
  IApiService plsqlService;

  @Resource(name = "restBean")
  IApiService restService;

  @Resource(name = "soapBean")
  IApiService soapService;
  @Resource(name = "mockBean")
  IApiService mockService;

  @RequestMapping(value = "/r/api", method = RequestMethod.POST)
  @ResponseBody
  @Timed
  public JSONObject sentRequest(HttpServletRequest request,
      @RequestBody(required = false) JSONObject params) throws HmsApiException {
    boolean isMock = false;
    String sysName = request.getParameter("sysName");
    String apiName = request.getParameter("apiName");

    logger.info("sysName:{}  apiName:{} ", sysName, apiName);
    logger.info("requestBody:{}", params);

    HeaderAndLineDTO headerAndLineDTO = headerService.getHeaderAndLine(sysName, apiName);
    logger.info("return HmsInterfaceHeader:{}", headerAndLineDTO);

    Map map = new HashMap<String, Object>();
    if (headerAndLineDTO == null) {
      throw new HmsApiException(HmsApiException.ERROR_NOT_FOUND, "根据sysName和apiName没有找到数据");
    }
    if (!headerAndLineDTO.getRequestFormat().equals("raw")) {
      throw new HmsApiException(HmsApiException.ERROR_REQUEST_FORMAT, "不支持的请求形式");
    }
    JSONObject json = null;
    /*
     * Mock接口设置：行设置优先级高于头设置，如果行无设置以头设置为准，如果行设置以行设置优先
     */
    if (StringUtils.isNotEmpty(headerAndLineDTO.getIsMock())) {
      if (headerAndLineDTO.getIsMock().equals("Y")
          && StringUtils.isEmpty(headerAndLineDTO.getIsMockLine())) {
        isMock = true;
      } else if (headerAndLineDTO.getIsMockLine().equals("Y")) {
        isMock = true;
      } else if (headerAndLineDTO.getIsMock().equals("N")
          || StringUtils.isEmpty(headerAndLineDTO.getIsMock())) {
        isMock = false;
      } else if (headerAndLineDTO.getIsMockLine().equals("N")) {
        isMock = false;
      }
    }
    if (isMock) {
      // 启用Mock
      json = mockService.invoke(headerAndLineDTO, params);
    } else {
      if (headerAndLineDTO.getInterfaceType().equals("REST")) {
        json = restService.invoke(headerAndLineDTO, params);
      } else if (headerAndLineDTO.getInterfaceType().equals("SOAP")) {
        json = soapService.invoke(headerAndLineDTO, params);
      } else if (headerAndLineDTO.getInterfaceType().equals("PLSQL")) {
        json = plsqlService.invoke(headerAndLineDTO, params);
      } else {
        throw new HmsApiException(HmsApiException.ERROR_INTERFACE_TYPE, "不支持的接口类型");
      }
    }


    return json;

  }


}
