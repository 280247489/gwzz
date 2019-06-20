package com.memory.gwzz.controller;

import com.memory.common.controller.BaseController;
import com.memory.common.utils.Message;
import com.memory.gwzz.model.LiveMaster;
import com.memory.gwzz.service.HomePageMobileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @ClassName HomePageMobileController
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/14 9:20
 */
@RestController
@RequestMapping(value = "homePage/mobile")
public class HomePageMobileController extends BaseController {
    private final static Logger logger = LoggerFactory.getLogger(AdvertiseMobileController.class);
    @Autowired
    private HomePageMobileService homePageMobileService;

    /**
     * 首页查询
     * URL:192.168.1.185:8081/gwzz/homePage/mobile/homePageOne
     * @return Map【"bannerList",轮播图);
     *         "courseList",课程;
     *         "articleList",养生文章;
     *        "albumList",专辑;】
     */
    @RequestMapping(value = "homePageOne",method = RequestMethod.POST)
    public Message homePageOne(){
        try {
            msg = Message.success();
            msg.setRecode(0);
            msg.setMsg("查询成功");
            msg.setData(homePageMobileService.HomePageOne());

        }catch (Exception e){
            msg = Message.error();
            e.printStackTrace();
            logger.error("异常信息");
        }
        return msg;
    }

    /**
     * 查询直播
     * URL:192.168.1.185:8081/gwzz/homePage/mobile/homePageTwo
     *
     * @return liveMaster对象
     */
    @RequestMapping(value = "homePageTwo",method = RequestMethod.POST)
    public Message homePageTwo(){
        try {
            msg = Message.success();

            LiveMaster liveMaster = homePageMobileService.HomePageTwo();
                if (liveMaster==null){
                    msg.setRecode(2);
                    msg.setMsg("NO liveMaster");
                }else {
                    msg.setRecode(0);
                    msg.setMsg("查询成功");
                    msg.setData(liveMaster);
                }
        }catch (Exception e){
            msg = Message.error();
            e.printStackTrace();
            logger.error("异常信息");
        }
        return msg;
    }
}
