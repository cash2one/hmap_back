package com.hand.hap.account.service;

import com.hand.hap.account.dto.User;
import com.hand.hap.account.exception.UserException;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;

import java.util.Date;
import java.util.List;

/**
 * Copyright (c) 2017. Hand Enterprise Solution Company. All right reserved.
 * Project Name:HmapParent
 * Package Name:com.hand.hap.account.service
 * Date:2017/1/23
 * Create By:yahang.liu@hand-china.com
 */
public interface IUserService extends IBaseService<User>, ProxySelf<IUserService> {

    /**
     * do login ,return the user in db.
     *
     * @param user
     * @return
     */
    User login(User user) throws UserException;

    User selectByUserName(String userName);

    void updatePassword(Long userId, String password, Date date);

    User updateUserInfo(IRequest request, User user);

    List<User> selectListByUserName(String userName);
}
