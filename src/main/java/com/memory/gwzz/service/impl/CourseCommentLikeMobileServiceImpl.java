package com.memory.gwzz.service.impl;

import com.memory.common.utils.Utils;
import com.memory.domain.dao.DaoUtils;
import com.memory.entity.jpa.CourseComment;
import com.memory.entity.jpa.CourseCommentLike;
import com.memory.entity.jpa.User;
import com.memory.gwzz.redis.service.CourseCommentRedisMobileService;
import com.memory.gwzz.repository.CourseCommentLikeMobileRepository;
import com.memory.gwzz.service.CourseCommentLikeMobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @ClassName CourseCommentLikeMobileServiceImpl
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/5 10:16
 */
@Service("courseCommentLikeMobileService")
public class CourseCommentLikeMobileServiceImpl implements CourseCommentLikeMobileService {
    @Autowired
    private DaoUtils daoUtils;

    @Autowired
    private CourseCommentLikeMobileRepository courseCommentLikeMobileRepository;

    @Autowired
    private CourseCommentRedisMobileService courseCommentRedisMobileService;

//    @Transactional
//    @Override
//    public synchronized CourseCommentLike like(String cid, String uid) {
//        CourseCommentLike courseCommentLike = null;
//        CourseComment courseComment = (CourseComment) daoUtils.getById("CourseComment",cid);
//        User user = (User) daoUtils.getById("User",uid);
//        if (courseComment != null && user != null){
//            courseCommentLike = this.getBycidAndUid(cid, uid);
//            if (courseCommentLike != null){
//                if (courseCommentLike.getCommentLikeYn()==1){
//                    Integer a =0;
//                    courseCommentLike.setCommentLikeYn(a);
//                    courseComment.setCommentTotalLike(courseComment.getCommentTotalLike()-1);
//                }else{
//                    courseCommentLike.setCommentLikeYn(1);
//                    courseComment.setCommentTotalLike(courseComment.getCommentTotalLike()+1);
//                }
//            }else{
//                if (courseComment != null && user != null){
//                    courseCommentLike = new CourseCommentLike();
//                    courseCommentLike.setId(Utils.generateUUIDs());
//                    courseCommentLike.setCommentId(cid);
//                    courseCommentLike.setUserId(uid);
//                    courseCommentLike.setCommentLikeYn(1);
//                    courseCommentLike.setCreateTime(new Date());
//
//                    courseComment.setCommentTotalLike(courseComment.getCommentTotalLike()+1);
//                }
//
//            }
//            daoUtils.save(courseComment);
//            daoUtils.save(courseCommentLike);
//        }
//
//        return courseCommentLike;
//    }

    public CourseCommentLike getBycidAndUid(String cid,String uid){
        return courseCommentLikeMobileRepository.findByCommentIdAndUserId(cid, uid);
    }

    @Transactional
    @Override
    public int like(String courseId,String courseCommentId, String userId) {
        CourseComment courseComment = (CourseComment) daoUtils.getById("CourseComment",courseCommentId);
        User user = (User) daoUtils.getById("User",userId);
        if(courseComment != null && user != null){
            courseCommentRedisMobileService.courseCommentLike(courseId, courseCommentId, userId);

        }
        return courseCommentRedisMobileService.isLike(courseCommentId,userId);
    }
}
