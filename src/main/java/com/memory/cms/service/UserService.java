package com.memory.cms.service;

import com.memory.cms.entity.UserInfos;

import java.util.List;

/**
 * @author INS6+
 * @date 2019/5/7 10:23
 */

public interface UserService {

    List<UserInfos> getUserList();

    UserInfos getUserById(String id);

    UserInfos updateUser(UserInfos userInfos);

    void deleteUser(String id);

    UserInfos addUserInfo(UserInfos userInfos);







}
