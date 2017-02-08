package hmap.core.hms.distribution.controllers;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codahale.metrics.annotation.Timed;
import com.hand.hap.account.exception.UserException;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;

import hmap.core.hms.distribution.domain.HmsAppDistribution;
import hmap.core.hms.distribution.domain.HmsAppVersion;
import hmap.core.hms.distribution.dto.HmsAppDistributionDTO;
import hmap.core.hms.distribution.service.IHmsAppDistributionService;
import hmap.core.hms.distribution.service.IHmsAppVersionService;
import hmap.core.hms.system.service.ILogService;
import hmap.core.security.SecurityUtils;
import hmap.core.util.StatelessRequestHelper;

/**
 * Created by Koma.Tshu on 2016/10/11.
 */
@Controller
@RequestMapping("/api/appDistribution")
public class HmsAppDistributionController extends BaseController {
  @Autowired
  ILogService logService;
  @Autowired
  private IHmsAppDistributionService iHmsAppDistributionService;
  @Autowired
  private IHmsAppVersionService iHmsAppVersionService;

  @RequestMapping(value = "/queryApps")
  @ResponseBody
  public ResponseData getAllApp(HttpServletRequest request,
      @RequestBody(required = false) HmsAppDistributionDTO hmsAppDistributionDTO,
      @RequestParam(defaultValue = DEFAULT_PAGE) int page,
      @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) throws UserException {

    List<HmsAppDistributionDTO> list =
        iHmsAppDistributionService.selectAllApp(hmsAppDistributionDTO, page, pageSize);

    return new ResponseData(list);
  }

  @RequestMapping(value = "/queryAppByAppId")
  @ResponseBody
  public ResponseData selectAppByAppId(HttpServletRequest request,
      @RequestBody(required = false) HmsAppDistributionDTO hmsAppDistributionDTO)
      throws UserException {
    HmsAppDistributionDTO dto =
        iHmsAppDistributionService.selectAppByAppId(hmsAppDistributionDTO.getAppId());

    return new ResponseData(Arrays.asList(dto));
  }

  @RequestMapping(value = "/queryAppVersionByAppId")
  @ResponseBody
  public ResponseData selectAppVersionByAppId(HttpServletRequest request,
      @RequestBody(required = false) HmsAppDistributionDTO hmsAppDistributionDTO,
      @RequestParam(defaultValue = DEFAULT_PAGE) int page,
      @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) throws UserException {
    List<HmsAppVersion> list =
        iHmsAppVersionService.selectAppVersionByAppId(hmsAppDistributionDTO.getAppId(),page,pageSize);
    return new ResponseData(list);
  }

  @RequestMapping(value = "/queryAppByRandomName")
  @ResponseBody
  public ResponseData selectAppByRandomName(HttpServletRequest request,
      @RequestBody(required = false) HmsAppDistributionDTO hmsAppDistributionDTO)
      throws UserException {
    List<HmsAppDistributionDTO> list = iHmsAppDistributionService.selectAppByRandomNameAndPlatform(
        hmsAppDistributionDTO.getRandomCode(), hmsAppDistributionDTO.getAppPlatform());

    return new ResponseData(list);
  }


  @RequestMapping(value = "/saveApp", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  @Timed
  public ResponseData saveApp(HttpServletRequest request,
      @RequestBody(required = false) HmsAppDistributionDTO hmsAppDistributionDTO)
      throws UserException, IOException {
    IRequest iRequest = StatelessRequestHelper.createServiceRequest(request);
    logService.serviceLogInfo("上传的app名称是" + hmsAppDistributionDTO.getAppName());
    iHmsAppDistributionService.saveApp(iRequest, hmsAppDistributionDTO);
    return new ResponseData(true);
  }

  @RequestMapping(value = "/updateApp", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  @Timed
  public ResponseData updateApp(HttpServletRequest request,
      @RequestBody(required = false) HmsAppDistribution hmsAppDistribution) {
    logService.serviceLogInfo("update appHand:" + hmsAppDistribution);
    IRequest iRequest = createRequestContext(request);
    // 保存当前登录人，作为筛选条件
    String userName = SecurityUtils.getCurrentUserLogin();
    // hmsAppDistribution.setCreateUser(userName);
    HmsAppDistribution e =
        iHmsAppDistributionService.updateByPrimaryKey(iRequest, hmsAppDistribution);
    return new ResponseData(Arrays.asList(e));
  }

  @RequestMapping(value = "/saveAppVersion", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  @Timed
  public ResponseData saveAppVersion(HttpServletRequest request,
      @RequestBody(required = false) HmsAppDistributionDTO hmsAppDistributionDTO)
      throws UserException, IOException {
    IRequest iRequest = StatelessRequestHelper.createServiceRequest(request);
    iHmsAppDistributionService.saveApp(iRequest, hmsAppDistributionDTO);
    return new ResponseData(true);
  }
}
