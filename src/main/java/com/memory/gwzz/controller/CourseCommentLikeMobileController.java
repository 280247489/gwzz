package com.memory.gwzz.controller;

import com.memory.common.controller.BaseController;
import com.memory.common.utils.Message;
import com.memory.gwzz.service.CourseCommentLikeMobileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName CourseCommentLikeMobileController
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/5 10:32
 */
@RestController
@RequestMapping(value = "courseCommentLike/mobile")
public class CourseCommentLikeMobileController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(CourseCommentMobileController.class);

    @Autowired
    private CourseCommentLikeMobileService courseCommentLikeMobileService;

    /**
     * 添加评论点赞
     * URL：192.168.1.185:8081/gwzz/courseCommentLike/mobile/addLike
     * @param cid String 评论id
     * @param uid String 用户id
     * @return 评论点赞对象
     */
    @RequestMapping(value = "addLike", method = RequestMethod.POST)
    public Message addLike(@RequestParam String cid, @RequestParam String uid){
        msg = Message.success();
        try {
            msg.setRecode(0);
            msg.setData(courseCommentLikeMobileService.like(cid, uid));
        }catch (Exception e){
            logger.error("异常");
            e.printStackTrace();
        }
        return msg;
    }
}
