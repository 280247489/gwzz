package com.memory.gwzz.redis.service.impl;

import com.memory.domain.dao.DaoUtils;
import com.memory.entity.jpa.CourseComment;
import com.memory.entity.jpa.CourseCommentLike;
import com.memory.gwzz.redis.service.CourseCommentRedisMobileService;
import com.memory.gwzz.repository.CourseCommentLikeMobileRepository;
import com.memory.redis.config.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.memory.redis.CacheConstantConfig.COURSECOMMENTLIKE;
import static com.memory.redis.CacheConstantConfig.COURSECOMMENTLIKEDETAIL;

/**
 * @ClassName CourseCommentRedisMobileServiceImpl
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/7/17 10:46
 */
@Service("courseCommentRedisMobileService")
public class CourseCommentRedisMobileServiceImpl implements CourseCommentRedisMobileService{

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private DaoUtils daoUtils;

    @Autowired
    private CourseCommentLikeMobileRepository courseCommentLikeMobileRepository;

    /**
     * 添加课程评论点赞
     * @param courseId
     * @param courseCommentId
     * @param userId
     */
    @Override
    public void courseCommentLike(String courseId,String courseCommentId,String userId){
        String courseCommentLike = COURSECOMMENTLIKE + courseId;
        String courseCommentLikeDetail = COURSECOMMENTLIKEDETAIL + userId;
        try {
            //查询当前用户是否点赞
            Object isLike = redisUtil.hget(courseCommentLikeDetail,courseCommentId);
            if (isLike != null){
                Integer like = Integer.valueOf(isLike.toString());
                if (like==1){
                    redisUtil.hdecr(courseCommentLike,courseCommentId,1);
                    redisUtil.hset(courseCommentLikeDetail,courseCommentId,"0");
                }else{
                    redisUtil.hincr(courseCommentLike,courseCommentId,1);
                    redisUtil.hset(courseCommentLikeDetail,courseCommentId,"1");
                }
            }else {
                redisUtil.hincr(courseCommentLike,courseCommentId,1);
                redisUtil.hset(courseCommentLikeDetail,courseCommentId,"1");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 获取课程评论点赞数
     * @param courseId
     * @param courseCommentId
     * @return
     */
    @Override
    public int getCourseCommentLike(String courseId, String courseCommentId ){
        Integer courseCommentLike = 0;
        Object ccl = null;
        try {
            ccl = redisUtil.hget(COURSECOMMENTLIKE + courseId,courseCommentId);
            if (ccl != null){
                courseCommentLike = Integer.valueOf(ccl.toString());
            }else {
                CourseComment courseComment = (CourseComment) daoUtils.getById("ArticleComment",courseCommentId);
                redisUtil.hset(COURSECOMMENTLIKE +courseId,courseCommentId,courseComment.getCommentTotalLike()+"");
                ccl = redisUtil.hget(COURSECOMMENTLIKE + courseId,courseCommentId);
                if (ccl!=null){
                    courseCommentLike = Integer.valueOf(ccl.toString());
                }else {
                    courseCommentLike = courseComment.getCommentTotalLike();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return courseCommentLike;
    }
    /**
     * 查询当前用户是否点赞
     * @param courseCommentId
     * @param userId
     * @return
     */
    @Override
    public int isLike ( String courseCommentId, String userId){
        Integer isLike = 0;
        try {
            Object userCourseCommentLike =  redisUtil.hget(COURSECOMMENTLIKEDETAIL + userId,courseCommentId);
            if (userCourseCommentLike != null){
                isLike = Integer.valueOf(userCourseCommentLike.toString());
            }else{
                CourseCommentLike courseCommentLike = courseCommentLikeMobileRepository.findByCommentIdAndUserId(courseCommentId, userId);
                if (courseCommentLike != null){
                    isLike = courseCommentLike.getCommentLikeYn();
                }else{
                    isLike = 0;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return isLike;
    }
}
