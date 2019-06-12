package com.memory.gwzz.service.impl;

import com.memory.common.utils.FileUploadUtil;
import com.memory.common.utils.Utils;
import com.memory.domain.dao.DaoUtils;

import com.memory.entity.jpa.User;
import com.memory.gwzz.repository.UserMobileRepository;
import com.memory.gwzz.service.UserMobileService;
import com.memory.redis.CacheConstantConfig;
import com.memory.redis.config.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @ClassName UserMobileServiceImpl
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/5/27 15:44
 */
@Service("userMobileService")
public class UserMobileServiceImpl implements UserMobileService {

    @Autowired
    private UserMobileRepository userMobileRepository;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private DaoUtils daoUtils;

    SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");

    /**
     * 用户注册
     * @param phone
     * @param userPwd
     * @return
     */
    @Transactional
    @Override
    public User registerPhone(String phone, String userPwd){
        User user = checkPhone(phone);
        Date date = new Date();
        if (user==null){
            user = new User(Utils.generateUUIDs(), Utils.md5Password(userPwd), "", "",  phone,
                    "","", "", "", "", "",
                    "", "", "", date, 0, 0, 0, 0);
            userMobileRepository.save(user);

        }
        return  user;
    }

    /**
     * 微信注册接口
     * @param userId
     * @param userUnionId
     * @param userOpenId
     * @param userNickName
     * @param userSex
     * @param userLogo
     * @return
     */
    public User registerWeChat( String userId,  String userUnionId,  String userOpenId,
                                String userNickName,  String userSex,  String userLogo ){
        User user = (User) daoUtils.getById("User",userId);
        Date date = new Date();
        if (user==null){
            user = new User(Utils.generateUUIDs(), "", userUnionId, userOpenId,  "",
                    userNickName, userLogo, "",userSex, "", "",
                    "", "", "", date, 0, 0,0, 0);
        }else{
            if ("".equals(user.getUserTel())){
                user.setUserNickName(userNickName);
                user.setUserSex(userSex);
                user.setUserLogo(userLogo);
            }
            user.setUserUnionId(userUnionId);
            user.setUserOpenId(userOpenId);

        }
        userMobileRepository.save(user);
        return user;
    }

    /**
     * 用户登录
     * @param phone
     * @param userPwd
     * @param userOpenId
     * @param type
     * @return
     */
    @Override
    public User login(String phone,String userPwd,String userOpenId, Integer type){
        User user = null;
        if (type==1){
            user = userMobileRepository.findByUserTelAndPasswordAndUserNologinAndUserCancel(phone,Utils.md5Password(userPwd),0,0);
        }else if (type==2){
            user = userMobileRepository.findByUserOpenId(userOpenId);
        }
        if (user!=null){

        }
        return  user;
    }

    @Override
    public User setUserPwd(String phone,String userPwd){
        User user = checkPhone(phone);
        user.setPassword(Utils.md5Password(userPwd));
        userMobileRepository.save(user);
        return user;
    }


    /**
     * 根据手机号校验用户是否存在
     * @param userPhone
     * @return
     */
    @Override
    public User checkPhone(String userPhone){return  userMobileRepository.findByUserTelAndUserNologinAndUserCancel(userPhone,0,0); }

    /**
     * 发送短信验证码
     * @param userPhone
     * @return
     */
    @Override
    public String getSMSCode(String userPhone){
        String msgId = Utils.sendSMSCode(164674,userPhone);
        if (!"".equals(msgId)){
            redisUtil.incr(CacheConstantConfig.USER_SMS_SUM+":"+userPhone,1,RedisUtil.CACHE_TIME_D_1);
        }
        return msgId;
    }

    /**
     * 校验短信验证码
     * @param msgId
     * @param code
     * @return
     */
    @Override
    public Boolean checkSmsCode(String msgId,String code){
        Boolean flag = false;
        flag = Utils.sendValidSMSCode(msgId,code);
        return  flag;
    }

    /**
     * 修改用户姓名
     * @param user
     * @param userName
     * @return
     */
    @Override
    public User updUserName(User user, String userName){
        user.setUserName(userName);
        return userMobileRepository.save(user);
    }

    /**
     * 修改用户性别
     * @param user
     * @param userSex
     * @return
     */
    @Override
    public User updUserSex(User user, String userSex){
        user.setUserSex(userSex);
        return userMobileRepository.save(user);
    }

    /**
     * 修改用户生日
     * @param user
     * @param userBirthday
     * @return
     */
    @Override
    public User updUserBirthday(User user, String userBirthday){
        user.setUserBirthday(userBirthday);
        return userMobileRepository.save(user);
    }

    /**
     * 修改用户头像
     * @param user
     * @param userLogo
     * @return
     */
    @Override
    public User updUserLogo(User user, MultipartFile userLogo){
        String filePath ="D:\\Tomcat 7.0\\webapps";
        String dbUrl = "/gwzz_file/user/logo/";
         if (userLogo!=null){
             user.setUserLogo(FileUploadUtil.uploadFile(userLogo,filePath,dbUrl,Utils.getShortUUTimeStamp()));
         }
        return userMobileRepository.save(user);
    }

    /**
     * 修改用户地址
     * @param user
     * @param userProvince
     * @param userCity
     * @param userArea
     * @param userAddress
     * @return
     */
    @Override
    public User updAddress(User user, String userProvince, String userCity, String userArea, String userAddress) {
        user.setUserProvince(userProvince);
        user.setUserCity(userCity);
        user.setUserArea(userArea);
        user.setUserAddress(userAddress);
        return userMobileRepository.save(user);
    }

    /**
     * 修改用户昵称
     * @param user
     * @param userNickName
     * @return
     */
    @Override
    public User updUserNickName(User user, String userNickName) {
        user.setUserNickName(userNickName);
        return userMobileRepository.save(user);
    }

    /**
     * 修改密码
     * @param user
     * @param newPassWord
     * @return
     */
    @Override
    public User updPassWord(User user, String newPassWord) {
        user.setPassword(Utils.md5Password(newPassWord));
        return userMobileRepository.save(user);
    }

    /**
     * 注销用户
     * @param user
     * @return
     */
    @Override
    public User logOFFUser(User user) {
        user.setUserCancel(1);
        return userMobileRepository.save(user);
    }
}
