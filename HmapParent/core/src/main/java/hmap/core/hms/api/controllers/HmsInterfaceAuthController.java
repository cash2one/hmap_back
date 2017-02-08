package hmap.core.hms.api.controllers;

import com.codahale.metrics.annotation.Timed;
import com.hand.hap.account.exception.UserException;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hmap.core.hms.appcenter.domain.HmsAppAuth;
import hmap.core.hms.api.domain.HmsInterfaceAuth;
import hmap.core.hms.api.dto.HmsInterfaceAuthDTO;
import hmap.core.hms.appcenter.service.IHmsAppAuthService;
import hmap.core.hms.api.service.IHmsInterfaceAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Created by Koma.Tshu on 2016/8/25.
 */
@Controller
@RequestMapping("/api")
public class HmsInterfaceAuthController extends BaseController {
  private final Logger log = LoggerFactory.getLogger(HmsInterfaceAuthController.class);
  @Autowired
  private IHmsInterfaceAuthService iHmsInterfaceAuthService;
  @Autowired
  private IHmsAppAuthService iHmsAppAuthService;

  /**
   * 根据HeaderId查询对应的授权信息
   *
   * @param request
   * @param hmsInterfaceAuthDTO
   * @return
   * @throws UserException
   */
  @RequestMapping(value = "/interfaceAuth/selectAuthByHeaderId", method = RequestMethod.POST)
  @ResponseBody
  public ResponseData selectAuthByHeaderId(HttpServletRequest request,
      @RequestBody(required = false) HmsInterfaceAuthDTO hmsInterfaceAuthDTO) throws UserException {
    log.info("headerId:" + hmsInterfaceAuthDTO.getInterfaceHeaderId());
    List<HmsInterfaceAuthDTO> list = iHmsInterfaceAuthService
        .selectAuthByInterfaceHeaderId(hmsInterfaceAuthDTO.getInterfaceHeaderId());
    return new ResponseData(list);
  }

  /**
   * 根据lineId查询对应的授权信息
   * 
   * @param request
   * @param hmsInterfaceAuthDTO
   * @return
   * @throws UserException
   */
  @RequestMapping(value = "/interfaceAuth/selectAuthByLineId", method = RequestMethod.POST)
  @ResponseBody
  public ResponseData selectAuthByLineId(HttpServletRequest request,
      @RequestBody(required = false) HmsInterfaceAuthDTO hmsInterfaceAuthDTO) throws UserException {
    log.info("headerId:" + hmsInterfaceAuthDTO.getInterfaceHeaderId());
    List<HmsInterfaceAuthDTO> list = iHmsInterfaceAuthService
        .selectAuthByInterfaceLineId(hmsInterfaceAuthDTO.getInterfaceLineId());
    return new ResponseData(list);
  }

  /**
   * 新增授权
   *
   * @param request
   * @param hmsInterfaceAuth
   * @return
   */
  @RequestMapping(value = "/interfaceAuth/saveInterfaceAuth",
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  @Timed
  public ResponseData createInterfaceAuth(HttpServletRequest request,
      @RequestBody(required = false) HmsInterfaceAuth hmsInterfaceAuth) {
    log.info("hmsInterfaceAuth:" + hmsInterfaceAuth);
    log.info("hmsInterfaceAuth.getHeaderId():" + hmsInterfaceAuth.getInterfaceHeaderId());
    log.info("hmsInterfaceAuth.getAuthId():" + hmsInterfaceAuth.getAuthId());
    IRequest iRequest = createRequestContext(request);
    hmsInterfaceAuth.setInterfaceAuthId(UUID.randomUUID().toString());
    HmsInterfaceAuth e = iHmsInterfaceAuthService.insert(iRequest, hmsInterfaceAuth);
    return new ResponseData(Arrays.asList(e));
  }

  @RequestMapping(value = "/interfaceAuth/deleteInterfaceAuth", method = RequestMethod.POST)
  @ResponseBody
  @Timed
  public ResponseData deleteCode(HttpServletRequest request,
      @RequestBody(required = false) HmsInterfaceAuth hmsInterfaceAuth) {
    IRequest requestContext = createRequestContext(request);
    int e = iHmsInterfaceAuthService.deleteByPrimaryKey(hmsInterfaceAuth);
    return new ResponseData();
  }

  /**
   * 查询所有应用名称
   *
   * @param request
   * @param hmsAppAuth
   * @param page
   * @param pagesize
   * @return
   */
  @RequestMapping(value = "/interfaceAuth/selectAppNames", method = RequestMethod.POST)
  @ResponseBody
  public ResponseData getAppAuth(HttpServletRequest request,
      @RequestBody(required = false) HmsAppAuth hmsAppAuth,
      @RequestParam(defaultValue = DEFAULT_PAGE) int page,
      @RequestParam(defaultValue = "100") int pagesize) {
    IRequest requestContext = createRequestContext(request);
    List<HmsAppAuth> aaa = iHmsAppAuthService.select(requestContext, hmsAppAuth, page, pagesize);
    ResponseData datas = new ResponseData(aaa);
    return datas;
  }
}
