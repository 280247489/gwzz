package com.memory.cms.service.impl;

import com.memory.cms.repository.UserCmsRepository;
import com.memory.cms.service.UserCmsService;
import com.memory.entity.jpa.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author INS6+
 * @date 2019/5/7 10:36
 */
@Service
public class UserCmsServiceImpl implements UserCmsService {

    @Autowired
    private UserCmsRepository userRepository;

    @Override
    public List<User> getUserList() {
/*        List<User> UserList = new ArrayList<User>();
        UserList = userRepository.findAll();*/
        return  userRepository.findAll();
    }

    @Override
    public User getUserById(String id) {

        return  userRepository.findById(id).get();
    }

    @Override
    public User updateUser(User User) {
        return  userRepository.save(User);
    }

    @Override
    public void deleteUser(String id) {

    }

    @Override
    public User addUserInfo(User User) {
        return null;
    }



}
