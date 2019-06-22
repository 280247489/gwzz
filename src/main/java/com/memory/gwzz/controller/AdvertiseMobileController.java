package com.memory.gwzz.controller;

import com.memory.common.controller.BaseController;
import com.memory.common.utils.Message;
import com.memory.domain.dao.DaoUtils;
import com.memory.entity.jpa.Advertise;
import com.memory.gwzz.repository.AdvertiseMobileRepository;
import com.memory.gwzz.service.AdvertiseMobileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName AdvertiseMobileController
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/5/31 9:15
 */
@RestController
@RequestMapping(value = "advertise/mobile")
public class AdvertiseMobileController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(AdvertiseMobileController.class);

    @Autowired
    private AdvertiseMobileService advertiseMobileService;

    @Autowired
    private AdvertiseMobileRepository advertiseMobileRepository;

    /**
     * 查询广告页
     * URL：192.168.1.185:8081/gwzz/advertise/mobile/advertiseInit
     * @return List
     */
    @RequestMapping(value = "advertiseInit", method = RequestMethod.POST)
    public Message getAdvertiseOnline(){
        try {
            msg = Message.success();
            msg.setRecode(0);
            msg.setMsg("查询成功");
            msg.setData(advertiseMobileService.getAdvertiseOnline());
        }catch (Exception e){
            msg = Message.error();
            logger.error("异常信息");
            e.printStackTrace();
        }
        return  msg;
    }

    /**
     * 根据广告查询跳转详情
     * URL:192.168.1.185:8081/gwzz/advertise/mobile/getAdvertiseById
     * @param id String 唯一标识
     * @param openId 用户 openId
     * @param terminal 终端  0 ：app 或  1 ：h5
     * @param os 操作系统 0：ios 或 1：android
     * @return
     */
    @RequestMapping(value = "getAdvertiseById",method = RequestMethod.POST)
    public Message getAdvertiseById(String id,String openId,Integer terminal,Integer os){
        try {
            msg = Message.success();
            Advertise advertise = advertiseMobileRepository.findByIdAndAdvertiseOnline(id,1);
            if (advertise!=null){
                msg.setRecode(0);
                msg.setData(advertiseMobileService.getAdvertiseById(advertise, openId, terminal,os));
                msg.setMsg("成功");
            }else {
                msg.setRecode(1);
                msg.setMsg("该广告不存在");
            }
        }catch (Exception e){
            e.printStackTrace();
            msg = Message.error();
            logger.error("异常信息");

        }
        return msg;
    }




}
