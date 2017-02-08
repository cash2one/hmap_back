/*
 * #{copyright}#
 */

package hmap.core.hms.system.controllers;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import hmap.core.util.StatelessRequestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.exception.BaseException;
import com.hand.hap.function.dto.Resource;
import com.hand.hap.function.dto.ResourceItem;
import com.hand.hap.function.service.IResourceItemService;
import com.hand.hap.function.service.IResourceService;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;

/**
 * 对资源的操作.
 * 
 * @author xiawang.liu@hand-china.com
 */

@Controller
@RequestMapping("/api")
public class HmsResourceController extends BaseController {

  @Autowired
  private IResourceService resourceService;

  @Autowired
  private IResourceItemService resourceItemService;

  /**
   * 查询资源权限项.
   * 
   * @param request HttpServletRequest
   * @param resource Resource
   * 
   * @return ResponseData
   */
  @RequestMapping(value = "/resourceItem/query")
  @ResponseBody
  public ResponseData queryResourceItems(HttpServletRequest request, Resource resource) {
    return new ResponseData(
        resourceItemService.selectResourceItems(createRequestContext(request), resource));
  }



  /**
   * 保存资源权限项.
   * 
   * @param request
   * @param resourceItems
   * @param result
   * @return ResponseData
   * @throws com.hand.hap.core.exception.BaseException
   */
  @RequestMapping(value = "/resourceItem/submit")
  @ResponseBody
  public ResponseData submitResourceItem(HttpServletRequest request,
      @RequestBody List<ResourceItem> resourceItems, BindingResult result) throws BaseException {
    getValidator().validate(resourceItems, result);
    if (result.hasErrors()) {
      ResponseData responseData = new ResponseData(false);
      responseData.setMessage(getErrorMessage(result, request));
      return responseData;
    }
    return new ResponseData(
        resourceItemService.batchUpdate(createRequestContext(request), resourceItems));
  }


  /**
   * 删除资源权限项.
   *
   * @param request
   * @param resourceItems
   * @return ResponseData
   * @throws com.hand.hap.core.exception.BaseException
   */
  @RequestMapping(value = "/resourceItem/remove", method = RequestMethod.POST)
  @ResponseBody
  public ResponseData deleteResourceItem(HttpServletRequest request,
      @RequestBody List<ResourceItem> resourceItems) throws BaseException {
    IRequest requestContext = createRequestContext(request);
    resourceItemService.batchDelete(requestContext, resourceItems);
    return new ResponseData();
  }


  /**
   * 查询资源.
   *
   * @param request HttpServletRequest
   * @param example Resource
   * @param page page
   * @param pagesize pagesize
   * @return ResponseData
   */
  @RequestMapping(value = "/resource/query", method = RequestMethod.GET)
  @ResponseBody
  public ResponseData getResource(HttpServletRequest request, Resource example,
      @RequestParam(defaultValue = DEFAULT_PAGE) int page,
      @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
    IRequest requestContext = StatelessRequestHelper.createServiceRequest(request);
    return new ResponseData(resourceService.select(requestContext, example, page, pagesize));
  }

  // @RequestMapping(value = "/resource/save", method = RequestMethod.POST)
  // @ResponseBody
  // public ResponseData createResource(HttpServletRequest request, @RequestBody Resource example) {
  // IRequest requestContext = StatelessRequestHelper.createServiceRequest(request);
  // Resource resource = resourceService.insert(requestContext, example);
  // return new ResponseData(Arrays.asList(resource));
  // }
  @RequestMapping(value = "/resource/save", method = RequestMethod.POST)
  @ResponseBody
  public ResponseData updateResource(HttpServletRequest request, @RequestBody Resource example) {
    IRequest requestContext = StatelessRequestHelper.createServiceRequest(request);
    Resource resource = null;
    if (example.getResourceId() != null) {
      resource = resourceService.updateByPrimaryKeySelective(requestContext, example);
    } else {
      resource = resourceService.insertSelective(requestContext, example);
    }
    return new ResponseData(Arrays.asList(resource));
  }

  /**
   * 保存资源.
   *
   * @param request HttpServletRequest
   * @param resources Resources
   * @param result BindingResult
   * @return ResponseData ResponseData
   * @throws com.hand.hap.core.exception.BaseException BaseException
   */
  @RequestMapping(value = "/resource/submit")
  @ResponseBody
  public ResponseData submitResource(HttpServletRequest request,
      @RequestBody List<Resource> resources, BindingResult result) throws BaseException {
    getValidator().validate(resources, result);
    if (result.hasErrors()) {
      ResponseData responseData = new ResponseData(false);
      responseData.setMessage(getErrorMessage(result, request));
      return responseData;
    }
    IRequest requestContext = createRequestContext(request);
    return new ResponseData(resourceService.batchUpdate(requestContext, resources));
  }

  /**
   * 删除资源.
   *
   * @param request HttpServletRequest
   * @param resources resources
   * @return ResponseData ResponseData
   * @throws com.hand.hap.core.exception.BaseException BaseException
   */
  @RequestMapping(value = "/resource/remove", method = RequestMethod.POST)
  @ResponseBody
  public ResponseData deleteResource(HttpServletRequest request,
      @RequestBody List<Resource> resources) throws BaseException {
    IRequest requestContext = createRequestContext(request);
    resourceService.batchDelete(requestContext, resources);
    return new ResponseData();
  }

}
