package hmap.core.hms.system.controllers;

import com.codahale.metrics.annotation.Timed;
import com.hand.hap.account.dto.Role;
import com.hand.hap.account.service.IRoleService;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.exception.BaseException;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hmap.core.util.StatelessRequestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by xincai.zhang on 2016/8/12.
 */
@Controller
@RequestMapping("/api/role")
public class HmsRoleController extends BaseController {
    @Autowired
    private IRoleService roleService;

    /**
     * 角色查询.
     *
     * @param role     角色对象
     * @param page     起始页
     * @param pagesize 分页大小
     * @param request  HttpServletRequest
     * @return ResponseData
     */
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData getRoles(HttpServletRequest request,Role role, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                 @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest requestContext = StatelessRequestHelper.createServiceRequest(request);
        List<Role> roles = roleService.select(requestContext, role, page, pagesize);
        ResponseData datas = new ResponseData(roles);
        return datas;
    }

    /**
     * 保存角色.
     *
     * @param roles   roles
     * @param result  BindingResult
     * @param request HttpServletRequest
     * @return ResponseData ResponseData
     * @throws BaseException BaseException
     */
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData submitRole(@RequestBody List<Role> roles, BindingResult result, HttpServletRequest request)
            throws BaseException {
        getValidator().validate(roles, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        IRequest requestContext = StatelessRequestHelper.createServiceRequest(request);
        return new ResponseData(roleService.batchUpdate(requestContext, roles));
    }

    /*
 * 编辑role
 * */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    @Timed
    public ResponseData editRole(HttpServletRequest request, @RequestBody(required = false) Role role) {
        IRequest iRequest = StatelessRequestHelper.createServiceRequest(request);
        Role roles = roleService.updateByPrimaryKey(iRequest, role);
        boolean flag = false;
        if (roles != null) {
            flag = true;
        }
        return new ResponseData(flag);
    }

    /***
     * 新增role
     *
     * @param request
     * @param role
     * @return
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    @Timed
    public ResponseData addRole(HttpServletRequest request, @RequestBody(required = false) Role role) {
        IRequest iRequest = StatelessRequestHelper.createServiceRequest(request);
        Role roles = roleService.insert(iRequest, role);
        boolean flag = false;
        if (roles != null) {
            flag = true;
        }
        return new ResponseData(flag);
    }


    /**
     * 批量删除角色.
     *
     * @param roles   需要删除的角色
     * @param result  BindingResult
     * @param request HttpServletRequest
     * @return 返回响应信息
     * @throws BaseException 抛出业务异常
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData deleteRole(@RequestBody List<Role> roles, BindingResult result, HttpServletRequest request)
            throws BaseException {
        getValidator().validate(roles, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        // IRequest requestContext = createRequestContext(request);
        roleService.batchDelete(roles);
        return new ResponseData();
    }
}
