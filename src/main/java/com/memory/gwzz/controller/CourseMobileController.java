package com.memory.gwzz.controller;

import com.memory.common.controller.BaseController;
import com.memory.common.utils.Message;
import com.memory.entity.jpa.Course;
import com.memory.entity.jpa.LiveMaster;
import com.memory.gwzz.redis.service.CourseRedisMobileService;
import com.memory.gwzz.repository.CourseMobileRepository;
import com.memory.gwzz.repository.LiveMasterMobileRepository;
import com.memory.gwzz.service.CourseMobileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CourseMobileController
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/22 9:47
 */
@RestController
@RequestMapping(value = "course/mobile")
public class CourseMobileController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(CourseMobileController.class);

    @Autowired
    private CourseMobileService courseMobileService;
    @Autowired
    private CourseMobileRepository courseMobileRepository;

    @Autowired
    private LiveMasterMobileRepository liveMasterMobileRepository;

    @Autowired
    private CourseRedisMobileService courseRedisMobileService;

    @Value(value = "${fileUrl}")
    private String fileUrl;




    /**
     * 根据Id查询课程详情
     * URL:192.168.1.185:8081/gwzz/course/mobile/getCourseById
     * @param cid String 唯一标识ID
     * @param uid String 用户Id
     * @param os 操作系统 0 ios， 1 android
     * @param terminal 类型 0 app， 1分享
     * @return
     */
    @RequestMapping(value = "getCourseById",method = RequestMethod.POST)
    public Message getCourseById(@RequestParam String cid,@RequestParam String uid,@RequestParam Integer os, @RequestParam Integer terminal){
        try {
            msg = Message.success();
            Course course = courseMobileRepository.findByIdAndCourseOnline(cid,1);
            if (course!=null){
                courseRedisMobileService.courseView(cid,uid,os,terminal);
                //重写课程阅读量、点赞量、分享量
                String id = course.getId();
                course.setCourseTotalView(courseRedisMobileService.getCourseView(id));
                course.setCourseTotalLike(courseRedisMobileService.getCourseLike(id));
                course.setCourseTotalShare(courseRedisMobileService.getCourseShare(id));
                String isLive = "noData";
                String albumId = course.getAlbumId();
                LiveMaster liveMaster =liveMasterMobileRepository.findByCourseIdAndLiveMasterIsOnline(cid,1);
                if (liveMaster!=null){
                    isLive=liveMaster.getId();
                }
                Integer isCourseLike = courseRedisMobileService.getUserCourseLike(cid,uid);
                List<com.memory.gwzz.model.Course> courseList = courseMobileService.getCourseById(albumId);
                Map<String,Object> returnMap = new HashMap<>();
                returnMap.put("course",course);
                returnMap.put("courselist",courseList);
                returnMap.put("isLive",isLive);
                returnMap.put("isLike",isCourseLike);
                returnMap.put("fileUrl",fileUrl);
                msg.setRecode(0);
                msg.setMsg("成功");
                msg.setData(returnMap);
            }else {
                msg.setRecode(2);
                msg.setMsg("无此数据或课程已被下线！");
            }
        }catch (Exception e){
            e.printStackTrace();
            msg = Message.error();
            logger.error("异常信息");
        }
        return msg;
    }

    /**
     * 根据关键字查询专辑下的课程
     * URL:192.168.1.185:8081/gwzz/course/mobile/listCourseByKey
     * @param start
     * @param limit
     * @param userId
     * @param key
     * @param albumId
     * @return
     */
    @RequestMapping(value = "listCourseByKey",method = RequestMethod.POST)
    public Message listCourseByKey(@RequestParam Integer start, @RequestParam Integer limit, @RequestParam String userId, @RequestParam String key,@RequestParam String albumId){
        try {
            msg = Message.success();
            //替换空格中英文符号
            String p = "(?i)[^a-zA-Z0-9\u4E00-\u9FA5]";
            String strKey = key.replaceAll(p, "");
            if (!"".equals(strKey)){
                courseRedisMobileService.searchCourse(userId,strKey);
            }
            Map<String,Object> returnMap = courseMobileService.fandCourseByKey(albumId, start, limit, strKey);
//            Map<String,Object> returnMap = new HashMap<>();
//            returnMap.put("courseList",courseMobileService.fandCourseByKey(albumId, start, limit, strKey));
//            returnMap.put("fileUrl",fileUrl);

            msg.setMsg("查询成功");
            msg.setData(returnMap);
        }catch (Exception e){
            e.printStackTrace();
            msg = Message.error();
            logger.error("异常信息");
        }
        return msg;
    }

    /**
     * 根据Id查询课程详情分享
     * URL:192.168.1.185:8081/gwzz/course/mobile/shareCourseById
     * @param cid String 唯一标识ID
     * @param os 操作系统 0 ios， 1 android
     * @return
     */
    @RequestMapping(value = "shareCourseById",method = RequestMethod.POST)
    public Message shareCourseById(@RequestParam String cid,@RequestParam Integer os){
        try {
            String uid = "wx";
            Integer terminal = 1;
            msg = Message.success();
            Course course = courseMobileRepository.findByIdAndCourseOnline(cid,1);
            if (course!=null){
                courseRedisMobileService.courseView(cid,uid,os,terminal);
                //重写课程阅读量、点赞量、分享量
                String id = course.getId();
                course.setCourseTotalView(courseRedisMobileService.getCourseView(id));
                course.setCourseTotalLike(courseRedisMobileService.getCourseLike(id));
                course.setCourseTotalShare(courseRedisMobileService.getCourseShare(id));

                Map<String,Object> returnMap = new HashMap<>();
                returnMap.put("course",course);
                returnMap.put("fileUrl",fileUrl);
                msg.setRecode(0);
                msg.setMsg("成功");
                msg.setData(returnMap);
            }else {
                msg.setRecode(2);
                msg.setMsg("无此数据或课程已被下线！");
            }
        }catch (Exception e){
            e.printStackTrace();
            msg = Message.error();
            logger.error("异常信息");
        }
        return msg;
    }


}
