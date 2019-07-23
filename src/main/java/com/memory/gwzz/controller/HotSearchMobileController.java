package com.memory.gwzz.controller;

import com.memory.common.controller.BaseController;
import com.memory.common.utils.Message;
import com.memory.entity.jpa.HotSearch;
import com.memory.gwzz.service.HotSearchMobileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName HotSearchMobileController
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/7/23 10:53
 */
@RestController
@RequestMapping(value = "hotSearch/mobile")
public class HotSearchMobileController extends BaseController {
    private final static Logger logger = LoggerFactory.getLogger(HotSearchMobileController.class);

    @Autowired
    private HotSearchMobileService hotSearchMobileService;

    /**
     * 查询热门搜索
     * URL:192.168.1.185:8081/gwzz/hotSearch/mobile/hotSearchList
     * @return
     */
    @RequestMapping(value = "hotSearchList",method = RequestMethod.POST)
    public Message hotSearchList(){
        try {
            msg = Message.success();
            List<HotSearch> list = hotSearchMobileService.hotSearchList();
            msg.setRecode(0);
            msg.setData(list);

        }catch (Exception e){
            e.printStackTrace();
            msg = Message.error();
            logger.error("系统错误");
        }
        return msg;
    }
}
