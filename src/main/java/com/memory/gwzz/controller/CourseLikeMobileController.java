package com.memory.gwzz.controller;

import com.memory.common.controller.BaseController;
import com.memory.common.utils.Message;
import com.memory.entity.jpa.CourseLike;
import com.memory.gwzz.service.CourseLikeMobileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName CourseLikeMobileController
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/5 9:20
 */
@RestController
@RequestMapping(value = "courseLike/mobile")
public class CourseLikeMobileController extends BaseController {
    private final static Logger logger = LoggerFactory.getLogger(CourseLikeMobileController.class);

    @Autowired
    private CourseLikeMobileService courseLikeMobileService;

    /**
     * 添加课程点赞
     * URL：192.168.1.185:8081/gwzz/courseLike/mobile/addLike
     * @param cid String 课程Id
     * @param uid String 用户对象
     * @return 点赞对象
     */
    @RequestMapping(value = "addLike" ,method = RequestMethod.POST)
    public Message add(@RequestParam String cid, @RequestParam String uid){
        msg = Message.success();
        try {
            msg.setRecode(0);
            CourseLike courseLike = courseLikeMobileService.like(cid,uid);
            msg.setData(courseLike);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("异常");
        }
        return msg;
    }
}
