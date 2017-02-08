/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.hms.controllers Date:2016/9/23 0023 Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.system.controllers;

import com.codahale.metrics.annotation.Timed;
import com.github.pagehelper.StringUtil;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hmap.core.hms.system.domain.HmsSystemConfig;
import hmap.core.hms.system.exception.HmsSystemConfigException;
import hmap.core.hms.system.service.IHmsSystemConfigService;
import hmap.core.util.PageRequest;
import hmap.core.util.StatelessRequestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/api/sysConfig")
public class HmsSystemConfigController extends BaseController {
  @Autowired
  IHmsSystemConfigService hmsSystemConfigService;

  @RequestMapping(value = "/query", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  @Timed
  public ResponseData query(HttpServletRequest request, HmsSystemConfig hsc, PageRequest pr) {
    IRequest iRequest = StatelessRequestHelper.createServiceRequest(request);
    List<HmsSystemConfig> list =
        hmsSystemConfigService.select(iRequest, hsc, pr.getPage(), pr.getPagesize());
    return new ResponseData(list);
  }

  @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  @Timed
  public ResponseData saveOrUpdate(HttpServletRequest request,@RequestBody HmsSystemConfig hsc,BindingResult result) throws HmsSystemConfigException {
    getValidator().validate(hsc, result);
    if (result.hasErrors()) {
      ResponseData responseData = new ResponseData(false);
      responseData.setMessage(getErrorMessage(result, request));
      return responseData;
    }
    IRequest iRequest = StatelessRequestHelper.createServiceRequest(request);
    HmsSystemConfig config = null;
    if (StringUtil.isNotEmpty(hsc.getId())) {
      // config_level =SYSTEM时,用户不能修改config_key
      HmsSystemConfig current = hmsSystemConfigService.selectByPrimaryKey(iRequest, hsc);
      if (!current.getConfigKey().equalsIgnoreCase(hsc.getConfigKey())
          && "SYSTEM".equals(current.getConfigLevel())) {
        throw new HmsSystemConfigException(HmsSystemConfigException.EXCEPTION_CODE,
            HmsSystemConfigException.EXCEPTION_CODE);
      } else {
        config = hmsSystemConfigService.updateByPrimaryKeySelective(iRequest, hsc);
      }

    } else {
      //需要做唯一性检查
      hmsSystemConfigService.validateConfigKey(hsc);
      config = hmsSystemConfigService.insertSelective(iRequest, hsc);
    }
    return new ResponseData(Arrays.asList(config));
  }

}
