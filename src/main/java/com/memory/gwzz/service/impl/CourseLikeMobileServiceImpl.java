package com.memory.gwzz.service.impl;

import com.memory.common.utils.Utils;
import com.memory.domain.dao.DaoUtils;
import com.memory.entity.jpa.Course;
import com.memory.entity.jpa.CourseLike;
import com.memory.entity.jpa.User;
import com.memory.gwzz.redis.service.CourseRedisMobileService;
import com.memory.gwzz.repository.CourseLikeMobileRepository;
import com.memory.gwzz.service.CourseLikeMobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CourseLikeMobileServiceImpl
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/4 17:15
 */
@Service("courseLikeMobileService")
public class CourseLikeMobileServiceImpl implements CourseLikeMobileService {
    @Autowired
    private DaoUtils daoUtils;

    @Autowired
    private CourseLikeMobileRepository courseLikeMobileRepository;

    @Autowired
    private CourseRedisMobileService courseRedisMobileService;

//    @Transactional
//    @Override
//    public synchronized CourseLike like(String cid, String uid) {
//        Course course = (Course) daoUtils.getById("Course",cid);
//        User user = (User) daoUtils.getById("User",uid);
//        CourseLike courseLike = null;
//        if (course !=null && user!=null){
//            courseLike = this.getBycidAndUid(cid, uid);
//            if (courseLike != null){
//                if (courseLike.getLikeStatus()==1){
//                    courseLike.setLikeStatus(0);
//                    course.setCourseTotalLike(course.getCourseTotalLike()-1);
//                }else{
//                    courseLike.setLikeStatus(1);
//                    course.setCourseTotalLike(course.getCourseTotalLike()+1);
//                }
//            }else {
//                if(course !=null && user != null){
//                    courseLike = new CourseLike();
//                    courseLike.setId(Utils.generateUUIDs());
//                    courseLike.setCourseId(cid);
//                    courseLike.setUserId(uid);
//                    courseLike.setLikeStatus(1);
//                    courseLike.setCreateTime(new Date());
//
//                    course.setCourseTotalLike(course.getCourseTotalLike()+1);
//                }
//            }
//            daoUtils.save(course);
//            daoUtils.save(courseLike);
//        }
//
//        return courseLike;
//    }

    public CourseLike getBycidAndUid(String cid,String uid){
        return courseLikeMobileRepository.findByCourseIdAndUserId(cid, uid);
    }
    @Transactional
    @Override
    public int like(String cid, String uid) {
        Course course = (Course) daoUtils.getById("Course",cid);
        User user = (User) daoUtils.getById("User",uid);
        if (course !=null && user!=null){
            courseRedisMobileService.courseLike(cid, uid);
        }
        return courseRedisMobileService.getUserCourseLike(cid,uid);
    }

    @Override
    public Map<String,Object> ListCourseLikeByUserId(String userId, Integer start, Integer limit){
        Map<String,Object> returnMap = new HashMap<>();

        StringBuffer stringBuffer = new StringBuffer(" SELECT NEW com.memory.gwzz.model.Course(c.id, c.courseNumber,c.courseTitle, c.courseLogo, c.courseLabel,c.courseOnline,c.courseTotalComment,c.courseTotalView,c.courseReleaseTime ) " +
                "FROM Course c, CourseLike cl WHERE c.id=cl.courseId AND cl.likeStatus = 1 ");
        Map<String,Object> map = new HashMap<>();

        StringBuffer stringBuffer1 = new StringBuffer("SELECT count(*) FROM course_like where like_status = 1 ");
        map.put("userId",userId);
        stringBuffer.append(" AND cl.userId =: userId ");
        stringBuffer1.append("AND user_id =:userId");

        DaoUtils.Page pageArticle = new DaoUtils.Page();
        pageArticle.setPageIndex(start);
        pageArticle.setLimit(limit);

        stringBuffer.append(" ORDER BY cl.createTime DESC");

        List<Course> courseList = daoUtils.findByHQL(stringBuffer.toString(),map,pageArticle);
        //重写课程阅读量
        for (int j = 0;j<courseList.size();j++){
            String courseId = courseList.get(j).getId();
            courseList.get(j).setCourseTotalView(courseRedisMobileService.getCourseView(courseId));
        }

        Integer courseCount = daoUtils.getTotalBySQL(stringBuffer1.toString(),map);

        returnMap.put("courseList",courseList);
        returnMap.put("courseCount",courseCount);

        return returnMap;
    }

    @Override
    public int isCourseLike (String cid, String uid){
        Integer isCLike = 0;
        CourseLike courseLike = courseLikeMobileRepository.findByCourseIdAndUserId(cid,uid);
        if (courseLike==null){
            isCLike=0;
        }else{
            if (courseLike.getLikeStatus()==1){
                isCLike=1;
            }else{
                isCLike=0;
            }
        }
        return isCLike;
    }
}
