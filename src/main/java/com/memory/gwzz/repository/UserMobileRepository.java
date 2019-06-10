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
    User findByUserTelAndUserNologinAndUserCancel(String userTel, Integer userNologin, Integer userCancel);
    User findByUserTelAndPasswordAndUserNologinAndUserCancel(String userPhone, String userPwd, Integer userNologin, Integer userCancel );
    User findByUserOpenId(String userOpenId);
    User findByIdAndUserNologinAndUserCancel(String id,Integer userNologin,Integer userCancel);
}
