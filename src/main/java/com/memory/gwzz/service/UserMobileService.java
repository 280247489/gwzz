package com.memory.gwzz.service;

import com.memory.entity.jpa.User;
import org.springframework.web.multipart.MultipartFile;

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
    User updUserName(User user, String userName);
    User updUserSex(User user, String userSex);
    User updUserBirthday(User user, String userBirthday);
    User updUserLogo(User user, MultipartFile userLogo);
    User updAddress(User user, String userProvince,String userCity,String userArea,String userAddress);
    User updUserNickName(User user,String userNickName);
    User updPassWord(User user, String newPassWord);
}
