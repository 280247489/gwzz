package com.memory.gwzz.controller;

import com.memory.common.controller.BaseController;
import com.memory.common.utils.Message;
import com.memory.entity.jpa.Banner;
import com.memory.gwzz.model.LiveMaster;
import com.memory.gwzz.repository.BannerMobileRepository;
import com.memory.gwzz.service.HomePageMobileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

    @Autowired
    private BannerMobileRepository bannerMobileRepository;

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
            Map<String,Object> returnMap = homePageMobileService.HomePageOne();
            msg.setData(returnMap);
            msg.setRecode(0);
            msg.setMsg("查询成功");
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

    /**
     * 根据Id查询Banner跳转详情
     * URL:192.168.1.185:8081/gwzz/homePage/mobile/getBannerById
     * @param id String 唯一标识
     * @param openId 用户openId
     * @param userId 用户Id
     * @param terminal 终端  0 ：app 或  1 ：h5
     * @param os 操作系统 0：ios 或 1：android
     * @return
     */
    @RequestMapping(value = "getBannerById",method = RequestMethod.POST)
    public Message getBannerById(@RequestParam String id, @RequestParam String openId, @RequestParam String userId, @RequestParam Integer terminal, @RequestParam Integer os){
        try {
            msg = Message.success();
            Banner banner = bannerMobileRepository.findByIdAndBannerOnline(id,1);
            if (banner==null){
                msg.setRecode(1);
                msg.setMsg("该轮播不存在");
            }else {
                Map<String,Object> returnMap = homePageMobileService.getAdvertiseById(banner, userId,openId, terminal,os);
                msg.setRecode(0);
                msg.setData(returnMap);
                msg.setMsg("成功");
            }
        }catch (Exception e){
            e.printStackTrace();
            msg = Message.error();
            logger.error("异常信息");

        }
        return msg;
    }
}
