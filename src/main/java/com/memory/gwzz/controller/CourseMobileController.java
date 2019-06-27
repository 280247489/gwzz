package com.memory.gwzz.controller;

import com.memory.common.controller.BaseController;
import com.memory.common.utils.Message;
import com.memory.entity.jpa.Course;
import com.memory.entity.jpa.CourseLike;
import com.memory.entity.jpa.LiveMaster;
import com.memory.gwzz.repository.CourseLikeMobileRepository;
import com.memory.gwzz.repository.CourseMobileRepository;
import com.memory.gwzz.repository.LiveMasterMobileRepository;
import com.memory.gwzz.service.CourseMobileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
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
    private CourseLikeMobileRepository courseLikeMobileRepository;


    /**
     * 根据Id查询课程详情
     * URL:192.168.1.185:8081/gwzz/course/mobile/getCourseById
     * @param cid String 唯一标识ID
     * @param uid String 用户Id
     * @return
     */
    @RequestMapping(value = "getCourseById",method = RequestMethod.POST)
    public Message getCourseById(@RequestParam String cid,@RequestParam String uid){
        try {
            msg = Message.success();
            Course course = courseMobileRepository.findByIdAndCourseOnline(cid,1);
            if (course!=null){
                String isLive = "noData";
                Integer isLike = 0;
                String albumId = course.getAlbumId();
                LiveMaster liveMaster =liveMasterMobileRepository.findByCourseIdAndLiveMasterIsOnline(cid,1);
                if (liveMaster!=null){
                    isLive=liveMaster.getId();
                }
                CourseLike courseLike = courseLikeMobileRepository.findByCourseIdAndUserId(cid,uid);
                if (courseLike!=null){
                    if (courseLike.getLikeStatus()==1){
                        isLike=1;
                    }else{
                        isLike=0;
                    }
                }else{
                    isLike=0;
                }

                Map<String,Object> returnMap = new HashMap<>();
                returnMap.put("course",course);
                returnMap.put("courselist",courseMobileService.getCourseById(albumId));
                returnMap.put("isLive",isLive);
                returnMap.put("isLike",isLike);
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
