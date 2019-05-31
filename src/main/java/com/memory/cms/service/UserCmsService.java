package com.memory.cms.service;

import com.memory.entity.jpa.User;

import java.util.List;

/**
 * @author INS6+
 * @date 2019/5/7 10:23
 */

public interface UserCmsService {

    List<User> getUserList();

    User getUserById(String id);

    User updateUser(User User);

    void deleteUser(String id);

    User addUserInfo(User User);







}
