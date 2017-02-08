/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.hms.controllers Date:2016/11/11 0011 Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.contact.controllers;

import com.codahale.metrics.annotation.Timed;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hmap.core.hms.contact.domain.HmsContactTag;
import hmap.core.hms.contact.exception.HmsContactTagException;
import hmap.core.hms.contact.service.IHmsContactTagService;
import hmap.core.security.SecurityUtils;
import hmap.core.util.StatelessRequestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/api/contactTag")
public class HmsContactTagController extends BaseController {
  @Autowired
  IHmsContactTagService hmsContactTagService;

  @RequestMapping(value = "/createOrUpdate", method = RequestMethod.POST)
  @ResponseBody
  @Timed
  public ResponseData createOrUpdateTag(HttpServletRequest request,
      @RequestBody(required = false) HmsContactTag hmsContactTag) throws HmsContactTagException {
    IRequest iRequest = StatelessRequestHelper.createServiceRequest(request);
    HmsContactTag tag = hmsContactTagService.insertOrUpdate(iRequest, hmsContactTag);
    return new ResponseData(Arrays.asList(tag));
  }

  @RequestMapping(value = "/unlock", method = RequestMethod.POST)
  @ResponseBody
  @Timed
  public ResponseData unlockTag(HttpServletRequest request,
      @RequestBody(required = false) HmsContactTag hmsContactTag) {
    IRequest iRequest = StatelessRequestHelper.createServiceRequest(request);
    hmsContactTag.setTagStatus("unlock");
    HmsContactTag tag = hmsContactTagService.updateByPrimaryKeySelective(iRequest, hmsContactTag);
    return new ResponseData(Arrays.asList(tag));
  }

  @RequestMapping(value = "/delete", method = RequestMethod.POST)
  @ResponseBody
  @Timed
  public ResponseData deleteTag(HttpServletRequest request,
      @RequestBody(required = false) HmsContactTag hmsContactTag) {
    IRequest iRequest = StatelessRequestHelper.createServiceRequest(request);
    hmsContactTag.setTagStatus("delete");
    HmsContactTag tag = hmsContactTagService.updateByPrimaryKeySelective(iRequest, hmsContactTag);
    return new ResponseData(Arrays.asList(tag));
  }

  @RequestMapping(value = "/getAllTags", method = RequestMethod.GET)
  @ResponseBody
  @Timed
  public ResponseData getAllTags(HttpServletRequest request) {
    IRequest iRequest = StatelessRequestHelper.createServiceRequest(request);
    String userName = SecurityUtils.getCurrentUserLogin();
    List<HmsContactTag> tags = hmsContactTagService.selectAllTags(userName);
    return new ResponseData(tags);
  }
}
