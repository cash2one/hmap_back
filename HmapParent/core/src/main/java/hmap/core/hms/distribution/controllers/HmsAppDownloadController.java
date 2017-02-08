package hmap.core.hms.distribution.controllers;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import hmap.core.hms.distribution.domain.HmsAppVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.codahale.metrics.annotation.Timed;
import com.hand.hap.account.exception.UserException;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;

import hmap.core.hms.distribution.dto.HmsAppDistributionDTO;
import hmap.core.hms.distribution.service.IHmsAppDistributionService;
import hmap.core.hms.distribution.service.IHmsAppVersionService;
import hmap.core.hms.system.service.ILogService;

/**
 * Created by USER on 2016/10/23.
 */
@Controller
@RequestMapping("/appDownload")
public class HmsAppDownloadController extends BaseController {

  @Autowired
  private IHmsAppDistributionService iHmsAppDistributionService;
  @Autowired
  private IHmsAppVersionService iHmsAppVersionService;
  @Autowired
  private ILogService iLogService;

  @RequestMapping(value = "/queryAppByRandomName")
  @ResponseBody
  public ResponseData selectAppByRandomNameAndPlatform(HttpServletRequest request,
      @RequestBody(required = false) HmsAppDistributionDTO hmsAppDistributionDTO ) throws UserException {
    iLogService.ctrlLogInfo("randomCode:" + hmsAppDistributionDTO.getRandomCode() + ",platform:" + hmsAppDistributionDTO.getAppPlatform());
    List<HmsAppDistributionDTO> list =
        iHmsAppDistributionService.selectAppByRandomNameAndPlatform(hmsAppDistributionDTO.getRandomCode(), hmsAppDistributionDTO.getAppPlatform());
    return new ResponseData(list);
  }

  @RequestMapping(value = "/editDownloadNum", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  @Timed
  public ResponseData editDownloadNum(HttpServletRequest request,
      @RequestBody(required = false) HmsAppVersion hmsAppVersion) {

    IRequest iRequest = createRequestContext(request);
    HmsAppVersion edn = iHmsAppVersionService.selectByPrimaryKey(iRequest, hmsAppVersion);
    if (String.valueOf(edn.getDownloadTimes()) != null
        && !"".equals(String.valueOf(edn.getDownloadTimes()))) {
      edn.setDownloadTimes(edn.getDownloadTimes() + 1);
    } else {
      edn.setDownloadTimes(1);
    }
    HmsAppVersion e = iHmsAppVersionService.updateByPrimaryKey(iRequest, edn);
    return new ResponseData(Arrays.asList(e));
  }
}
