package com.memory.gwzz.controller;

import com.memory.common.controller.BaseController;
import com.memory.common.utils.Message;
import com.memory.gwzz.service.ShareUrlMobileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName ShareUrlMobileController
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/12 20:46
 */
@RestController
@RequestMapping(value = "shareUrl/mobile")
public class ShareUrlMobileController extends BaseController {
    private final static Logger logger = LoggerFactory.getLogger(ShareUrlMobileController.class);

    @Autowired
    private ShareUrlMobileService shareUrlMobileService;

    /**
     * 根据名称查询分享的url
     * URL:192.168.1.185:8081/gwzz/shareUrl/mobile/getShareUrlByName
     * @param name String
     * @return ShareUrl 对象
     */
    @RequestMapping(value = "getShareUrlByName",method = RequestMethod.POST)
    public Message getShareUrlByName(@RequestParam  String name){
        msg = Message.success();
        try {
            msg.setRecode(0);
            msg.setData(shareUrlMobileService.getShareUrlByName(name));
        }catch (Exception e){
            e.printStackTrace();
            msg.setRecode(1);
            msg.setMsg("系统错误");
            logger.error("异常信息");
        }
        return msg;
    }
}
