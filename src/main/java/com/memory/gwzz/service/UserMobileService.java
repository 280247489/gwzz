package com.memory.gwzz.service;

import com.memory.entity.jpa.User;

/**
 * @ClassName UserMobileService
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/5/27 15:44
 */
public interface UserMobileService {
    User checkPhone(String userPhone);
    String getSMSCode(String userPhone);
    User registerPhone(String phone, String userPwd);
    Boolean checkSmsCode(String msgId,String code);
    User registerWeChat( String userId,  String userUnionId,  String userOpenId, String userName,  String userSex,  String userLogo );
    User logo(String phone,String userPwd,String userOpenId, Integer type);
    User setUserPwd(String phone,String userPwd);
}
