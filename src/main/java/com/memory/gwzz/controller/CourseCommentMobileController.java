package com.memory.gwzz.controller;

import com.memory.common.controller.BaseController;
import com.memory.common.utils.Message;
import com.memory.gwzz.service.CourseCommentMobileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName CourseCommentMobileController
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/4 17:09
 */
@RestController
@RequestMapping(value = "courseComment/mobile")
public class CourseCommentMobileController extends BaseController {
    private final static Logger logger = LoggerFactory.getLogger(CourseCommentMobileController.class);

    @Autowired
    private CourseCommentMobileService courseCommentMobileService;


    /**
     * 课程评论
     * URL:192.168.1.185:8081/gwzz/courseComment/mobile/add
     * @param courseId String 课程Id
     * @param userId String 用户Id
     * @param userLogo String 用户头像
     * @param userName String 用户昵称
     * @param commentType String 评论类型（评论0，回复1）
     * @param commentParentId String 回复上级Id
     * @param commentParentUserName String 回复上级昵称
     * @param commentContent 评论内容
     * @return 评论对象
     */
    @RequestMapping(value = "add",method = RequestMethod.POST)
    public Message add(@RequestParam String courseId, @RequestParam String userId, @RequestParam String userLogo,
                       @RequestParam String userName, @RequestParam Integer commentType, @RequestParam String commentParentId,
                       @RequestParam String commentParentUserName, @RequestParam String commentContent){
        msg = Message.success();
        try {
            msg.setRecode(0);
            msg.setData(courseCommentMobileService.add(courseId, userId, userLogo, userName, commentType, commentParentId, commentParentUserName, commentContent));
        }catch (Exception e){
            logger.error("异常信息");
            e.printStackTrace();
        }
        return msg;
    }
}
