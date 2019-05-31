package com.memory.gwzz.service.impl;

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
                    "", "", "", "", "",
                    "", "", "", date, 0, 0, 0);
            daoUtils.save(user);

        }
        return  user;
    }

    /**
     * 微信注册接口
     * @param userId
     * @param userUnionId
     * @param userOpenId
     * @param userName
     * @param userSex
     * @param userLogo
     * @return
     */
    public User registerWeChat( String userId,  String userUnionId,  String userOpenId,
                                String userName,  String userSex,  String userLogo ){
        User user = (User) daoUtils.getById("User",userId);
        Date date = new Date();
        if (user==null){
            user = new User(Utils.generateUUIDs(), "", userUnionId, userOpenId,  "",
                    userName, userLogo, userSex, "", "",
                    "", "", "", date, 0, 0, 0);
        }else{
            user.setUserUnionId(userUnionId);
            user.setUserOpenId(userOpenId);
            user.setUserName(userName);
            user.setUserSex(userSex);
            user.setUserLogo(userLogo);
        }
        userMobileRepository.save(user);
        return user;
    }

    /**
     * 用户登录\
     * @param phone
     * @param userPwd
     * @param userOpenId
     * @param type
     * @return
     */
    @Override
    public User logo(String phone,String userPwd,String userOpenId, Integer type){
        User user = null;
        if (type==1){
            user = userMobileRepository.findByUserTelAndPassword(phone,Utils.md5Password(userPwd));
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
    public User checkPhone(String userPhone){return  userMobileRepository.findByUserTel(userPhone); }

    /**
     * 发送短信验证码
     * @param userPhone
     * @return
     */
    @Override
    public String getSMSCode(String userPhone){
        String msgId = Utils.sendSMSCode(164674,userPhone);
        if (!"".equals(msgId)){
            redisUtil.incr(CacheConstantConfig.USER_SMS_SUM+":"+userPhone,RedisUtil.CACHE_TIME_D_1,1);
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






}
