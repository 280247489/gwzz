package com.memory.gwzz.service.impl;

import com.memory.common.utils.Utils;
import com.memory.domain.dao.DaoUtils;
import com.memory.entity.jpa.Course;
import com.memory.entity.jpa.CourseLike;
import com.memory.entity.jpa.User;
import com.memory.gwzz.repository.CourseLikeMobileRepository;
import com.memory.gwzz.service.CourseLikeMobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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

    @Override
    public CourseLike like(String cid, String uid) {
        Course course = (Course) daoUtils.getById("Course",cid);
        User user = (User) daoUtils.getById("User",uid);
        CourseLike courseLike = null;
        if (course !=null && user!=null){
            courseLike = this.getBycidAndUid(cid, uid);
            if (courseLike != null){
                if (courseLike.getLikeStatus()==1){
                    courseLike.setLikeStatus(0);
                    course.setCourseTotalLike(course.getCourseTotalLike()-1);
                }else{
                    courseLike.setLikeStatus(1);
                    course.setCourseTotalLike(course.getCourseTotalLike()+1);
                }
            }else {
                if(course !=null && user != null){
                    courseLike = new CourseLike();
                    courseLike.setId(Utils.getShortUUID());
                    courseLike.setCourseId(cid);
                    courseLike.setUserId(uid);
                    courseLike.setLikeStatus(1);
                    courseLike.setCreateTime(new Date());

                    course.setCourseTotalLike(course.getCourseTotalLike()+1);
                }
            }
            daoUtils.save(course);
            daoUtils.save(courseLike);
        }

        return courseLike;
    }

    public CourseLike getBycidAndUid(String cid,String uid){
        return courseLikeMobileRepository.findByCourseIdAndUserId(cid, uid);
    }
}
