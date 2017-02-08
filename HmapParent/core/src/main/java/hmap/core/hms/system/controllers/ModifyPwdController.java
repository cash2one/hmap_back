package hmap.core.hms.system.controllers;


import com.hand.hap.account.dto.User;
import com.hand.hap.account.exception.UserException;
import com.hand.hap.account.service.IUserInfoService;
import com.hand.hap.account.service.IUserService;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hmap.core.hms.system.service.IModifyPwdService;
import hmap.core.token.JdbcTokenStore;
import hmap.core.util.AESUtil;
import hmap.core.util.Constant;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by shanhd on 2016/11/25.
 */

@Controller
public class ModifyPwdController extends BaseController {

    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IModifyPwdService iModifyPwdService;
    @Resource(name="tokenStore")
    private JdbcTokenStore tokenStore;


    @RequestMapping(value={"/api/modifyPwd/modifyPwd"},method = RequestMethod.POST)
    @ResponseBody
    public ResponseData modifyPwd(HttpServletRequest request,@RequestBody JSONObject jsonObject) throws UserException {

        ResponseData responseData=new ResponseData();
        IRequest iRequest= createRequestContext(request);
        String oldPwd= AESUtil.decrypt(jsonObject.getString("oldPwd"), Constant.AES_KEY);
        System.out.println(oldPwd);
        String newPwd=AESUtil.decrypt(jsonObject.getString("newPwd"), Constant.AES_KEY);
        System.out.println(newPwd);
        String accessToken=request.getHeader("Authorization").split(" ")[1];
        OAuth2AccessToken token = this.tokenStore.readAccessToken(accessToken);
        OAuth2Authentication result = this.tokenStore.readAuthentication(token);

        System.out.println(result.getName());
        User user=userService.selectByUserName(result.getName());
        System.out.println(user.getUserId());
        if(userInfoService.validatePassword(iRequest,oldPwd,newPwd,newPwd,user.getUserId())){
            Date date = new Date();
            userService.updatePassword(user.getUserId(),newPwd,date);
        }else{
            responseData.setSuccess(false);
            responseData.setMessage("原密码错误，请重新输入");
        }

        return responseData;
    }

    @RequestMapping(value={"/api/modifyPwd/modifyPwdSimple"},method = RequestMethod.POST)
    @ResponseBody
    public ResponseData modifyPwdSimple(HttpServletRequest request,@RequestBody JSONObject jsonObject) throws UserException {

        ResponseData responseData=new ResponseData();
        IRequest iRequest= createRequestContext(request);
        String oldPwd= AESUtil.decrypt(jsonObject.getString("oldPwd"), Constant.AES_KEY);
        System.out.println(oldPwd);
        String newPwd=AESUtil.decrypt(jsonObject.getString("newPwd"), Constant.AES_KEY);
        System.out.println(newPwd);
        String accessToken=request.getHeader("Authorization").split(" ")[1];
        OAuth2AccessToken token = this.tokenStore.readAccessToken(accessToken);
        OAuth2Authentication result = this.tokenStore.readAuthentication(token);

        System.out.println(result.getName());
        User user=userService.selectByUserName(result.getName());
        System.out.println(user.getUserId());
        if(iModifyPwdService.validatePassword(iRequest,oldPwd,newPwd,newPwd,user.getUserId())){
            Date date = new Date();
            userService.updatePassword(user.getUserId(),newPwd,date);
        }else{
            responseData.setSuccess(false);
            responseData.setMessage("原密码错误，请重新输入");
        }

        return responseData;
    }

}
