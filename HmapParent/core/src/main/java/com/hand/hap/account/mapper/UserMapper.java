package com.hand.hap.account.mapper;

import com.hand.hap.account.dto.User;
import com.hand.hap.mybatis.common.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Copyright (c) 2017. Hand Enterprise Solution Company. All right reserved.
 * Project Name:HmapParent
 * Package Name:com.hand.hap.account.mapper
 * Date:2017/1/23
 * Create By:yahang.liu@hand-china.com
 */
public interface UserMapper extends Mapper<User> {

    User selectByUserName(String userName);

    List<User> selectByIdList(List<Long> userIds);

    int updatePassword(@Param("userId") Long userId, @Param("password") String passwordNew);

    int updateUserInfo(User user);
    List<User> selectListByUserName(String userName);
}