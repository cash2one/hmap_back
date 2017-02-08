/*
 * #{copyright}#
 */

package hmap.core.hms.system.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import hmap.core.util.StatelessRequestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hand.hap.account.dto.UserRole;
import com.hand.hap.account.service.IUserRoleService;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.exception.BaseException;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;

/**
 * 查询并保存角色的功能.
 * 
 * @author xiawang.liu@hand-china.com
 */
@Controller
@RequestMapping(value = "/api/userrole")
public class HmsUserRoleController extends BaseController {

    @Autowired
    private IUserRoleService userRoleService;

    /**
     * 查询用户关联的所有角色.
     *
     * @param request
     *            HttpServletRequest
     * @param example
     *            查询参数
     * @return ResponseData ResponseData
     */
    @RequestMapping(value = "/queryUserRoles")
    @ResponseBody
    public ResponseData getUserRoleIds(HttpServletRequest request, UserRole example) {
        IRequest requestContext = StatelessRequestHelper.createServiceRequest(request);
        return new ResponseData(userRoleService.selectUserRoles(requestContext, example));
    }

    /**
     * 保存为用户关联的所有角色.
     *
     * @param request
     *            HttpServletRequest
     * @param userRoles
     *            角色列表
     * @param result
     *            BindingResult
     * @return ResponseData ResponseData
     * @throws com.hand.hap.core.exception.BaseException
     *             BaseException
     */
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData submitUserRole(HttpServletRequest request, @RequestBody List<UserRole> userRoles,
            BindingResult result) throws BaseException {
        getValidator().validate(userRoles, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(userRoleService.batchUpdate(requestContext, userRoles));
    }

}
