package hmap.core.hms.appcenter.controllers;

import com.codahale.metrics.annotation.Timed;
import com.hand.hap.account.exception.UserException;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.exception.BaseException;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hmap.core.hms.appcenter.domain.HmsAppAuth;
import hmap.core.hms.appcenter.service.IHmsAppAuthService;
import hmap.core.hms.service.IOauthClientDetailsService;
import hmap.core.util.StatelessRequestHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * Created by xincai.zhang on 2016/8/14.
 */
@Controller
@RequestMapping("/api/appauth")
public class HmsAppAuthController extends BaseController {
  private final Logger log = LoggerFactory.getLogger(HmsAppAuthController.class);
  @Autowired
  IHmsAppAuthService iHmsAppAuthService;
  @Autowired
  IOauthClientDetailsService oauthClientDetailsService;

  /***
   * app配置查询
   *
   * @param request
   * @param hmsAppAuth
   * @param page
   * @param pagesize
   * @return
   */
  @RequestMapping(value = "/query", method = RequestMethod.POST)
  @ResponseBody
  @Timed
  public ResponseData getAppAuth(HttpServletRequest request,
      @RequestBody(required = false) HmsAppAuth hmsAppAuth,
      @RequestParam(defaultValue = DEFAULT_PAGE) int page,
      @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
    IRequest requestContext = createRequestContext(request);
    List<HmsAppAuth> appAuths =
        iHmsAppAuthService.select(requestContext, hmsAppAuth, page, pagesize);
    ResponseData datas = new ResponseData(appAuths);
    return datas;
  }

  /***
   * 根据Id查询app配置
   *
   * @param request
   * @param id
   * @return
   * @throws com.hand.hap.account.exception.UserException
   * @throws UserException
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  @ResponseBody
  public ResponseData getHmsAppAuthById(HttpServletRequest request, @PathVariable String id)
      throws UserException {
    IRequest iRequest = StatelessRequestHelper.createServiceRequest(request);
    HmsAppAuth appAuth = new HmsAppAuth();
    appAuth.setId(id);
    HmsAppAuth e = iHmsAppAuthService.selectByPrimaryKey(iRequest, appAuth);
    return new ResponseData(Arrays.asList(e));
  }

  /*
   * 新增/修改
   */
  @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
  @ResponseBody
  @Timed
  public ResponseData insertHmsApp(HttpServletRequest request,
      @RequestBody(required = false) HmsAppAuth hmsAppAuth) {
    IRequest iRequest = StatelessRequestHelper.createServiceRequest(request);
    HmsAppAuth result = iHmsAppAuthService.insertOrUpdate(iRequest, hmsAppAuth);
    boolean flag = false;
    if (result != null) {
      flag = true;
    }
    return new ResponseData(flag);
  }


  /***
   * 根据条件删除数据
   *
   * @param hmsAppAuth
   * @return
   * @throws com.hand.hap.core.exception.BaseException
   * @throws BaseException
   * @author xincai.zhang
   */
  @RequestMapping(value = "/delete", method = RequestMethod.POST)
  public ResponseData deleteHmsAppAuth(@RequestBody HmsAppAuth hmsAppAuth) throws BaseException {
    int result = iHmsAppAuthService.deleteByPrimaryKey(hmsAppAuth);
    boolean flag = false;
    if (result > 0) {
      flag = true;
    }
    return new ResponseData(flag);
  }
}
