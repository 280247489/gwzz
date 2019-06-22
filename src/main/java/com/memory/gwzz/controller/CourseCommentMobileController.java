package com.memory.gwzz.controller;

import com.memory.common.controller.BaseController;
import com.memory.common.utils.Message;
import com.memory.domain.dao.DaoUtils;
import com.memory.entity.jpa.Course;
import com.memory.entity.jpa.User;
import com.memory.gwzz.repository.CourseMobileRepository;
import com.memory.gwzz.service.CourseCommentMobileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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

    @Autowired
    private DaoUtils daoUtils;

    @Autowired
    private CourseMobileRepository courseMobileRepository;


    /**
     * 课程评论
     * URL:192.168.1.185:8081/gwzz/courseComment/mobile/add
     * @param courseId String 课程ID
     * @param userId String 用户id
     * @param commentType int 评论类型（0评论，1回复）
     * @param commentParentId String 上级Id
     * @param content 评论内容
     * @param content_replace
     * @return
     */
    @RequestMapping(value = "add",method = RequestMethod.POST)
    public Message add(@RequestParam String courseId, @RequestParam String userId,  @RequestParam Integer commentType, @RequestParam String commentParentId,
                       @RequestParam("content") String content ,@RequestParam("content_replace") String content_replace){
        try {
            msg = Message.success();
            User user = (User) daoUtils.getById("User",userId);
            Course course = courseMobileRepository.findByIdAndCourseOnline(courseId,1);
            if (course==null){
                msg.setRecode(1);
                msg.setMsg("无此课程");
            }else{
                if (user==null){
                    msg.setRecode(2);
                    msg.setMsg("无此用户信息");
                }else {
                    msg.setRecode(0);
                    msg.setData(courseCommentMobileService.add(courseId,user,commentType,commentParentId,content,content_replace));
                }
            }

        }catch (Exception e){
            msg = Message.error();
            logger.error("异常信息");
            e.printStackTrace();
        }
        return msg;
    }

    /**
     * 查询课程一级评论
     * URL:192.168.1.185:8081/gwzz/courseComment/mobile/listCourseCommentOne
     * @param courseId String 课程Id
     * @param start int 第几页
     * @param limit  int 每页条数
     * @return
     */
    @RequestMapping(value = "listCourseCommentOne",method = RequestMethod.POST)
    public Message listCourseCommentOne(@RequestParam String courseId,@RequestParam Integer start,@RequestParam Integer limit){
        try {
            msg = Message.success();
            Course course = courseMobileRepository.findByIdAndCourseOnline(courseId,1);
            if (course!=null){
                msg.setRecode(0);
                msg.setMsg("成功");
                msg.setData(courseCommentMobileService.listComByCid(courseId, start, limit));
            }else{
                msg.setRecode(2);
                msg.setMsg("无此课程");
            }
        }catch (Exception e){
            msg = Message.error();
            logger.error("异常信息");
            e.printStackTrace();
        }
        return msg;
    }

    /**
     * 查询课程2级评论
     * URL:192.168.1.185:8081/gwzz/courseComment/mobile/listCourseCommentTwo
     * @param courseId  String 课程Id
     * @param commentId String 评论Id
     * @param start int 第几页
     * @param limit int 每页条数
     * @return
     */
    @RequestMapping(value = "listCourseCommentTwo",method = RequestMethod.POST)
    public Message listCourseCommentTwo(@RequestParam String courseId,@RequestParam String commentId,@RequestParam Integer start,@RequestParam Integer limit){
        try {
            msg = Message.success();
            Course course = courseMobileRepository.findByIdAndCourseOnline(courseId,1);
            if (course==null){
                msg.setRecode(2);
                msg.setMsg("无此课程");
            }else{
                Map<String,Object> map = courseCommentMobileService.listCouComByRid(commentId, start, limit);
                if (map.values()!=null){
                    msg.setRecode(0);
                    msg.setMsg("成功");
                    msg.setData(map);
                }else{
                    msg.setRecode(3);
                    msg.setMsg("无此评论");
                }

            }
        }catch (Exception e){
            msg = Message.error();
            logger.error("异常信息");
            e.printStackTrace();
        }
        return msg;
    }
}
