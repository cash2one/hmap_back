package com.hand.hap.account.service.impl;

import com.hand.hap.account.dto.User;
import com.hand.hap.account.exception.UserException;
import com.hand.hap.account.mapper.UserMapper;
import com.hand.hap.account.service.IUserService;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.security.PasswordManager;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Copyright (c) 2017. Hand Enterprise Solution Company. All right reserved.
 * Project Name:HmapParent
 * Package Name:com.hand.hap.account.service.impl
 * Date:2017/1/23
 * Create By:yahang.liu@hand-china.com
 */
@Service
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User> implements IUserService {

    @Autowired
    private PasswordManager passwordManager;

    @Autowired
    private UserMapper userMapper;

    @Override
    public User insertSelective(IRequest request, User record) {
        if (StringUtils.isEmpty(record.getPassword())) {
            record.setPassword(passwordManager.getDefaultPassword());
        }
        record.setPasswordEncrypted(passwordManager.encode(record.getPassword()));
        return super.insertSelective(request, record);
    }

    @Override
    public User login(User user) throws UserException {
        if (user == null || org.apache.commons.lang3.StringUtils.isAnyBlank(user.getUserName(), user.getPassword())) {
            throw new UserException(UserException.ERROR_USER_PASSWORD, UserException.ERROR_USER_PASSWORD, null);
        }
        User user1 = userMapper.selectByUserName(StringUtils.upperCase(user.getUserName()));
        if (user1 == null) {
            throw new UserException(UserException.ERROR_USER_PASSWORD, UserException.ERROR_USER_PASSWORD, null);
        }
        if (User.STATUS_LOCK.equals(user1.getStatus())) {
            throw new UserException(UserException.ERROR_USER_EXPIRED, UserException.ERROR_USER_EXPIRED, null);
        }
        if (user1.getStartActiveDate() != null && user1.getStartActiveDate().getTime() > System.currentTimeMillis()) {
            throw new UserException(UserException.ERROR_USER_EXPIRED, UserException.ERROR_USER_EXPIRED, null);
        }
        if (user1.getEndActiveDate() != null && user1.getEndActiveDate().getTime() < System.currentTimeMillis()) {
            throw new UserException(UserException.ERROR_USER_EXPIRED, UserException.ERROR_USER_EXPIRED, null);
        }
        if (!passwordManager.matches(user.getPassword(), user1.getPasswordEncrypted())) {
            throw new UserException(UserException.ERROR_USER_PASSWORD, UserException.ERROR_USER_PASSWORD, null);
        }
        return user1;
    }

    @Override
    public User selectByUserName(String userName) {
        return userMapper.selectByUserName(userName);
    }

    @Override
    public List<User> selectListByUserName(String userName) {
        return userMapper.selectListByUserName(userName);
    }

    /**
     * updateByPrimaryKeySelective 无法将值置空, 有效期需要置空.
     * <p>
     * 使用updateByPrimaryKey 替代
     *
     * @param request
     * @param record
     * @return
     */
    @Override
    public User updateByPrimaryKeySelective(IRequest request, @StdWho User record) {
        return super.updateByPrimaryKey(request, record);
    }

    @Override
    @Transactional
    public void updatePassword(Long userId, String password, Date date) {
        String passwordEncrypted = passwordManager.encode(password);
        userMapper.updatePassword(userId, passwordEncrypted);
    }

    @Override
    public User updateUserInfo(IRequest request, User user) {
        userMapper.updateUserInfo(user);
        return user;
    }

}
