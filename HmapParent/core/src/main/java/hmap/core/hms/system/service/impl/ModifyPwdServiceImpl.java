/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.hms.service.impl Date:2016/8/4 0004 Create By:zongyun.zhou@hand-china.com
 */
package hmap.core.hms.system.service.impl;


import com.hand.hap.account.dto.User;
import com.hand.hap.account.exception.UserException;
import com.hand.hap.account.service.IUserService;
import com.hand.hap.core.IRequest;
import com.hand.hap.security.PasswordManager;
import hmap.core.hms.system.service.IModifyPwdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModifyPwdServiceImpl implements IModifyPwdService {
    @Autowired
    private IUserService userService;
    @Autowired
    private PasswordManager passwordManager;
    @Override
    public boolean validatePassword(IRequest request, String oldPwd, String newPwd, String newPwdAgain, Long userId) throws UserException {
        if(!"".equals(oldPwd) && !"".equals(newPwd) && !"".equals(newPwdAgain)) {
            if(!newPwd.equals(newPwdAgain)) {
                throw new UserException("error.password.not_same_twice", (Object[])null);
            } else {
                User tmp = new User();
                tmp.setUserId(userId);
                User userInDB = (User)this.userService.selectByPrimaryKey(request, tmp);
                String pwd = userInDB.getPasswordEncrypted();
                if(!this.passwordManager.matches(oldPwd, pwd)) {
                    throw new UserException("error.password.current_password", (Object[])null);
                } else if(this.passwordManager.matches(newPwd, pwd)) {
                    throw new UserException("error.system.not_allowed_same_with_old_password", (Object[])null);
                } else {
                    return true;
                }
            }
        } else {
            throw new UserException("error.user.password_not_empty", (Object[])null);
        }
    }
}
