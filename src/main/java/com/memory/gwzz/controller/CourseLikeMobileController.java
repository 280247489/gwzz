package com.memory.gwzz.controller;

import com.memory.common.controller.BaseController;
import com.memory.common.utils.Message;
import com.memory.entity.jpa.Course;
import com.memory.entity.jpa.CourseLike;
import com.memory.entity.jpa.User;
import com.memory.gwzz.repository.CourseMobileRepository;
import com.memory.gwzz.repository.UserMobileRepository;
import com.memory.gwzz.service.CourseLikeMobileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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

    @Autowired
    private CourseMobileRepository courseMobileRepository;

    @Autowired
    private UserMobileRepository userMobileRepository;

    @Value(value = "${fileUrl}")
    private String fileUrl;

    /**
     * 添加课程点赞
     * URL：192.168.1.185:8081/gwzz/courseLike/mobile/addLike
     * @param cid String 课程Id
     * @param uid String 用户对象
     * @return
     */
    @RequestMapping(value = "addLike" ,method = RequestMethod.POST)
    public Message add(@RequestParam String cid, @RequestParam String uid){

        try {
            msg = Message.success();
            Course course = courseMobileRepository.findByIdAndCourseOnline(cid,1);
            if (course!=null){
                Integer courseLike = courseLikeMobileService.like(cid,uid);
                msg.setRecode(courseLike);
            }else {
                msg.setRecode(1);
                msg.setMsg("课程不存在！");
            }

        }catch (Exception e){
            e.printStackTrace();
            msg = Message.error();
            logger.error("异常信息");
        }
        return msg;
    }

    /**
     * 查询我的点赞课程
     * URL：192.168.1.185:8081/gwzz/courseLike/mobile/listCourseLikeByUserId
     * @param userId String 用户Id
     * @param start int 第几页
     * @param limit int 每页条数
     * @return
     */
    @RequestMapping(value = "listCourseLikeByUserId",method = RequestMethod.POST)
    public Message listCourseLikeByUserId(@RequestParam String userId, @RequestParam Integer start,@RequestParam Integer limit){
        try {
            msg = Message.success();
            User user = userMobileRepository.findByIdAndUserNologinAndUserCancel(userId,0,0);
            if (user != null){
                Map<String,Object> returnMap = courseLikeMobileService.ListCourseLikeByUserId(userId, start, limit);
                returnMap.put("fileUrl",fileUrl);
                msg.setRecode(0);
                msg.setData(returnMap);
            }else {
                msg.setRecode(1);
                msg.setMsg("该用户不存在");
            }
        }catch (Exception e){
            msg = Message.error();
            logger.error("异常信息");
            e.printStackTrace();
        }
        return msg;
    }
}
