package com.memory.gwzz.repository;

import com.memory.entity.jpa.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ClassName UserMobileRepository
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/5/27 15:46
 */
public interface UserMobileRepository extends JpaRepository<User,String> {
    User findByUserTel(String userTel);
    User findByUserTelAndPassword(String userPhone, String userPwd);
    User findByUserOpenId(String userOpenId);
}
