package com.memory.gwzz.controller;

import com.memory.common.controller.BaseController;
import com.memory.common.utils.Message;
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

    /**
     * 查询广告页
     * URL：192.168.1.185:8081/gwzz/advertise/mobile/advertiseInit
     * @return List
     */
    @RequestMapping(value = "advertiseInit", method = RequestMethod.POST)
    public Message getAdvertiseOnline(){
        msg = Message.success();
        try {
            msg.setRecode(0);
            msg.setMsg("查询成功");
            msg.setData(advertiseMobileService.getAdvertiseOnline());
        }catch (Exception e){
            e.printStackTrace();
            msg.setRecode(1);
            msg.setMsg("系统错误");
            logger.error("异常信息");
        }
        return  msg;
    }


}
