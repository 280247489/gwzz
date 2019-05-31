package com.memory.common.utils;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jsms.api.SendSMSResult;
import cn.jsms.api.ValidSMSResult;
import cn.jsms.api.common.SMSClient;
import cn.jsms.api.common.model.SMSPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.util.DigestUtils;

import java.net.MalformedURLException;
import java.util.UUID;

/**
 * @Auther: cui.Memory
 * @Date: 2018/11/7 0007 16:48
 * @Description:
 */
public class Utils {

    @Value("${APPKEY}")
    private  String APP_KEY;

    @Value("${MASTERSECRET}")
    private  String MASTER_SECRET;

    private final static Logger logger = LoggerFactory.getLogger(Utils.class);

    public static final String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z" };

    /**
     * 生成32位UUID字符串
     */
    public static String generateUUIDs() {
        return UUID.randomUUID().toString().replace("-", "");
    }


    public static String getShortUUID() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString();
    }

    public static String getShortUUTimeStamp(){return getShortUUID()+ System.currentTimeMillis();}




    /**
     *md5加密
     */
    public static String md5Password(String str){ return  DigestUtils.md5DigestAsHex(str.getBytes()); }

    public static void main(String[] args) {
        System.out.println(getShortUUTimeStamp());
    }


    /**
     * 检测网络资源是否可访问
     * @param url
     * @return
     * @throws MalformedURLException
     */
    public static Boolean isHttpAccess(String url) throws MalformedURLException {
      return   new UrlResource(url).exists();
    }


    /**
     * 发送短信验证码
     * @param tempId
     * @param phone
     * @return
     */
    public static String sendSMSCode(int tempId,String phone){
        SMSClient smsClient = new SMSClient("f68fc210a844b0acd31f0e6a","03c3145a36f490cb28b3feef");
        SMSPayload smsPayload = SMSPayload.newBuilder()
                .setMobileNumber(phone)
                .setTempId(tempId)
                .build();
        String resule = null;
                try {
                    SendSMSResult sendSMSResult = smsClient.sendSMSCode(smsPayload);
                    resule= sendSMSResult.toString();
                    System.out.println(sendSMSResult.toString());
                    logger.info(sendSMSResult.toString());

                } catch (APIConnectionException e) {
                    e.printStackTrace();
                    logger.error("Connection error. Should retry later. ", e);
                } catch (APIRequestException e) {
                    e.printStackTrace();
                    logger.error("Error response from JPush server. Should review and fix it. ", e);
                    logger.info("HTTP Status: " + e.getStatus());
                    logger.info("Error Message:" + e.getMessage());
                }
                return resule;
    }

    /**
     * 验证码校验
     * @param msg_id
     * @param valid
     * @return
     */
    public static Boolean sendValidSMSCode(String msg_id,String valid){
        SMSClient smsClient = new SMSClient("f68fc210a844b0acd31f0e6a","03c3145a36f490cb28b3feef");
        Boolean flag = false;
        try {
            ValidSMSResult validSMSResult = smsClient.sendValidSMSCode(msg_id,valid);
            flag = validSMSResult.getIsValid();
            System.out.println(validSMSResult.toString());
            logger.info(validSMSResult.toString());
        } catch (APIConnectionException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            logger.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            e.printStackTrace();
            if (e.getErrorCode() == 50010){
                //do something
            }
            System.out.println(e.getStatus() + " errorCode: " + e.getErrorCode() + " " + e.getErrorMessage());
            logger.error("Error response from JPush server. Should review and fix it. ", e);
            logger.info("HTTP Status: " + e.getStatus());
            logger.info("Error Message: " + e.getMessage());
        }
        return flag;
    }


}


