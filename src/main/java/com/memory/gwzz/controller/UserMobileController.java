package com.memory.gwzz.controller;

import com.alibaba.fastjson.JSON;
import com.memory.common.controller.BaseController;
import com.memory.common.utils.Message;
import com.memory.common.utils.Utils;
import com.memory.entity.jpa.User;
import com.memory.gwzz.repository.UserMobileRepository;
import com.memory.gwzz.service.UserMobileService;
import com.memory.redis.CacheConstantConfig;
import com.memory.redis.config.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName UserMobileController
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/5/29 9:21
 */
@RestController
@RequestMapping(value = "user/mobile")
public class UserMobileController extends BaseController {
    private final static Logger logger = LoggerFactory.getLogger(UserMobileController.class);

    @Autowired
    private UserMobileService userMobileService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UserMobileRepository userMobileRepository;

    /**
     * 获取短信验证码
     * URL:192.168.1.185:8081/gwzz/user/mobile/getSMSCode
     * @param phone
     * @param type 1注册 2修改密码
     * @return
     */
    @RequestMapping(value = "getSMSCode", method = RequestMethod.POST)
    public Message getSMSCode(@RequestParam String phone,@RequestParam Integer type){
        msg = Message.success();
        try {
            User user = userMobileService.checkPhone(phone);
            String userSMSSum = String.valueOf(redisUtil.get(CacheConstantConfig.USER_SMS_SUM+":"+phone));
            if (!"null".equals(userSMSSum)){
                Integer smsSum = Integer.valueOf(userSMSSum);
                if (smsSum>=5){
                    msg.setRecode(1);
                    msg.setMsg("获取次数超出");
                }else{
                    if (type==1&&user!=null){
                        msg.setRecode(1);
                        msg.setMsg("此号码已经存在！");
                    }else if(type==2&&user==null){
                        msg.setRecode(1);
                        msg.setMsg("此号码不存在！");
                    }else{
                        String msgId = userMobileService.getSMSCode(phone);
                        msg.setRecode(0);
                        msg.setMsg("成功");
                        msg.setData(JSON.parse(msgId));
                    }
                }
            }else if (type==1&&user!=null){
                msg.setRecode(1);
                msg.setMsg("此号码已经存在！");
            }else if (type==2&&user==null){
                msg.setRecode(1);
                msg.setMsg("此号码不存在！");
            }else{
                msg.setRecode(0);
                msg.setMsg("成功");
                msg.setData(JSON.parse(userMobileService.getSMSCode(phone)));
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("异常信息");
        }

        return msg;
    }

    /**
     * 用户注册-手机号
     * URL:192.168.1.185:8081/gwzz/user/mobile/registerPhone
     * @param phone String 电话号
     * @param userPwd String 密码
     * @param msgId String  短信返回唯一标识
     * @param code String 验证码
     * @return user对象
     */
    @RequestMapping(value = "registerPhone", method = RequestMethod.POST)
    public Message registerPhone (@RequestParam String phone, @RequestParam String userPwd, @RequestParam String msgId, @RequestParam String code){
        msg = Message.success();
        try {
            if (userMobileService.checkSmsCode(msgId,code)){
                msg.setMsg("成功");
                msg.setRecode(0);
                msg.setData(userMobileService.registerPhone(phone,userPwd));
            }else {
                msg.setRecode(1);
                msg.setMsg("验证码失效");
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("异常信息");
        }

        return msg;
    }

    /**
     * 用户注册-微信号
     * URL:192.168.1.185:8081/gwzz/user/mobile/registerWeChat
     * @param userId 用户唯一标识ID String
     * @param userUnionId 微信unionId String
     * @param userOpenId 微信openId   String
     * @param userName 微信昵称 String
     * @param userSex 微信性别 String
     * @param userLogo 微信头像URL String
     * @return user对象
     */
    @RequestMapping(value = "registerWeChat", method = RequestMethod.POST)
    public Message registerWeChat (@RequestParam String userId, @RequestParam String userUnionId, @RequestParam String userOpenId,
                                   @RequestParam String userName, @RequestParam String userSex, @RequestParam String userLogo){
        msg = Message.success();
        try {
            User user = userMobileService.registerWeChat(userId, userUnionId, userOpenId, userName, userSex, userLogo);
            msg.setMsg("注册成功");
            msg.setRecode(0);
            msg.setData(user);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("异常信息");
        }
        return msg;
    }


    /**
     * 用户登录
     * URL:192.168.1.185:8081/gwzz/user/mobile/login
     * @param phone 电话号码 String
     * @param userPwd 用户密码 String
     * @param userOpenId 微信OpenId String
     * @param logoType 登录类型 1 手机号登录，2 微信登录
     * @return user对象
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public Message login (@RequestParam String phone, @RequestParam String userPwd, @RequestParam String userOpenId, @RequestParam Integer logoType){
        msg = Message.success();
        try {
            User user = userMobileService.logo(phone, userPwd, userOpenId, logoType);
            if (user ==null){
                msg.setRecode(2);
                msg.setMsg("登录失败");
            }else{
                msg.setRecode(0);
                msg.setMsg("登录成功");
                msg.setData(user);
            }
        }catch (Exception e){
            e.printStackTrace();
            msg.setRecode(1);
            msg.setMsg("异常");
            logger.error("异常信息");
        }
        return  msg;
    }

    /**
     * 忘记密码
     * URL:192.168.1.185:8081/gwzz/user/mobile/setUserPwd
     * @param phone 电话号码 String
     * @param userPwd 用户新密码 String
     * @param msgId 短信唯一标识msgId String
     * @param code 短信验证码 String
     * @return user对象
     */
    @RequestMapping(value = "setUserPwd", method = RequestMethod.POST)
    public Message setUserPwd(@RequestParam String phone, @RequestParam String userPwd, @RequestParam String msgId, @RequestParam String code){
        msg = Message.success();
        try {
            User user = userMobileService.checkPhone(phone);
            if (user!=null){
                if (userMobileService.checkSmsCode(msgId,code)){
                    msg.setRecode(0);
                    msg.setMsg("修改成功");
                    msg.setData(userMobileService.setUserPwd(phone, userPwd));
                }else {
                    msg.setRecode(1);
                    msg.setMsg("验证码失效");
                }
            }else{
                msg.setRecode(1);
                msg.setMsg("无此用户");
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("异常信息");
        }

        return msg;
    }

    /**
     * 修改用户真实姓名
     * URL:192.168.1.185:8081/gwzz/user/mobile/updUserName
     * @param userId 用户主键ID
     * @param userName 用户真实姓名
     * @return user对象
     */
    @RequestMapping(value = "updUserName", method = RequestMethod.POST)
    public Message updUserName(@RequestParam String userId, @RequestParam String userName){
        msg = Message.success();
        try {
            User user = userMobileRepository.findByIdAndUserNologinAndUserCancel(userId,0,0);
            if (user!=null){
                msg.setRecode(0);
                msg.setData(userMobileService.updUserName(user,userName));
            }else{
                msg.setRecode(1);
                msg.setMsg("无此用户");
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("异常信息");
        }
        return msg;
    }

    /**
     * 修改用户性别
     * URL:192.168.1.185:8081/gwzz/user/mobile/updUserSex
     * @param userId String 用户主键ID
     * @param userSex String 用户性别
     * @return user对象
     */
    @RequestMapping(value = "updUserSex", method = RequestMethod.POST)
    public Message updUserSex(@RequestParam String userId, @RequestParam String userSex){
        msg = Message.success();
        try {
            User user = userMobileRepository.findByIdAndUserNologinAndUserCancel(userId,0,0);
            if (user!=null){
                msg.setRecode(0);
                msg.setData(userMobileService.updUserSex(user,userSex));
            }else{
                msg.setRecode(1);
                msg.setMsg("无此用户");
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("异常信息");
        }
        return msg;
    }

    /**
     * 修改用户出生日期
     * URL:192.168.1.185:8081/gwzz/user/mobile/updUserBirthday
     * @param userId String 用户唯一标识ID
     * @param userBirthday String用户生日
     * @return user对象
     */
    @RequestMapping(value = "updUserBirthday", method = RequestMethod.POST)
    public Message updUserBirthday(@RequestParam String userId, @RequestParam String userBirthday){
        msg = Message.success();
        try {
            User user = userMobileRepository.findByIdAndUserNologinAndUserCancel(userId,0,0);
            if (user!=null){
                msg.setRecode(0);
                msg.setData(userMobileService.updUserBirthday(user,userBirthday));
            }else{
                msg.setRecode(1);
                msg.setMsg("无此用户");
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("异常信息");
        }
        return msg;
    }

    /**
     * 修改用户头像
     * URL:192.168.1.185:8081/gwzz/user/mobile/updUserLogo
     * @param userId String 用户唯一标识ID
     * @param UserLogo File 用户头像
     * @return User 对象
     */
    @RequestMapping(value = "updUserLogo", method = RequestMethod.POST)
    public Message updUserLogo(@RequestParam String userId, @RequestParam("file") MultipartFile UserLogo ){
        msg = Message.success();
        try {
            User user = userMobileRepository.findByIdAndUserNologinAndUserCancel(userId,0,0);
            if (user!=null){
                msg.setRecode(0);
                msg.setData(userMobileService.updUserLogo(user,UserLogo));
            }else{
                msg.setRecode(1);
                msg.setMsg("无此用户");
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("异常信息");
        }
        return msg;
    }

    /**
     * 修改用户地址
     * URL:192.168.1.185:8081/gwzz/user/mobile/updAddress
     * @param userId String 用户唯一标识ID
     * @param userProvince String 省
     * @param userCity String 市
     * @param userArea String 区
     * @param userAddress String 详细地址
     * @return
     */
    @RequestMapping(value = "updAddress", method = RequestMethod.POST)
    public Message updAddress(@RequestParam String userId,@RequestParam String userProvince, @RequestParam String userCity,
                              @RequestParam String userArea, @RequestParam String userAddress){
        msg = Message.success();
        try {
            User user = userMobileRepository.findByIdAndUserNologinAndUserCancel(userId,0,0);
            if (user!=null){
                msg.setRecode(0);
                msg.setData(userMobileService.updAddress(user,userProvince,userCity,userArea,userAddress));
            }else{
                msg.setRecode(1);
                msg.setMsg("无此用户");
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("异常信息");
        }
        return msg;
    }

    /**
     * 修改用户昵称
     * URL:192.168.1.185:8081/gwzz/user/mobile/updUserNickName
     * @param userId String 用户唯一标识ID
     * @param userNickName String 用户昵称
     * @return
     */
    @RequestMapping(value = "updUserNickName", method = RequestMethod.POST)
    public Message updUserNickName(@RequestParam String userId,@RequestParam String userNickName){
        msg = Message.success();
        try {
            User user = userMobileRepository.findByIdAndUserNologinAndUserCancel(userId,0,0);
            if (user!=null){
                msg.setRecode(0);
                msg.setData(userMobileService.updUserNickName(user,userNickName));
            }else{
                msg.setRecode(1);
                msg.setMsg("无此用户");
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("异常信息");
        }
        return msg;
    }

    /**
     * 修改密码
     * URL:192.168.1.185:8081/gwzz/user/mobile/updUserPwd
     * @param userId String 用户标识Id
     * @param oldPassword String 旧密码
     * @param newPassWord String新密码
     * @return User对象
     */
    @RequestMapping(value = "updUserPwd",method = RequestMethod.POST)
    public Message updUserPwd(@RequestParam String userId,@RequestParam String oldPassword,@RequestParam String newPassWord){
        msg = Message.success();
        try {
            User user = userMobileRepository.findByIdAndUserNologinAndUserCancel(userId,0,0);
            if (user != null){
                String oldPwd = Utils.md5Password(oldPassword);
                if (user.getPassword().equals(oldPwd) ){
                    msg.setRecode(0);
                    msg.setData(userMobileService.updPassWord(user,newPassWord));
                }else{
                    msg.setRecode(2);
                    msg.setMsg("旧密码错误");
                }

            }else{
                msg.setRecode(1);
                msg.setMsg("无此用户");
            }

        }catch (Exception e){
            e.printStackTrace();
            logger.error("异常");
        }
        return msg;
    }

}
