package com.memory.gwzz.redis.service.impl;

import com.memory.domain.dao.DaoUtils;
import com.memory.entity.jpa.Course;
import com.memory.entity.jpa.CourseLike;
import com.memory.entity.jpa.LiveMaster;
import com.memory.gwzz.redis.service.CourseRedisMobileService;
import com.memory.gwzz.repository.CourseLikeMobileRepository;
import com.memory.gwzz.repository.LiveMasterMobileRepository;
import com.memory.redis.config.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.memory.redis.CacheConstantConfig.*;


/**
 * @ClassName CourseRedisMobileServiceImpl
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/7/4 15:57
 */
@Service("courseRedisMobileService")
public class CourseRedisMobileServiceImpl implements CourseRedisMobileService {
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private DaoUtils daoUtils;

    @Autowired
    private LiveMasterMobileRepository liveMasterMobileRepository;

    @Autowired
    private CourseLikeMobileRepository courseLikeMobileRepository;

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 搜索课程
     * @param userId
     * @param searchKey
     */
    @Override
    public void searchCourse(String userId, String searchKey){
        String keyIncr = SEARCHCOURSESEARCHAPPID + userId;
        String searchCourse = SEARCHCOURSEDATE + formatter.format(new Date());
        redisUtil.hincr(keyIncr,searchKey,1);
        redisUtil.hincr(searchCourse,searchKey,1);
    }

    /**
     * 添加课程阅读量
     * @param courseId
     * @param userId
     * @param os
     * @param terminal
     */
    @Override
    public void courseView(String courseId, String userId, Integer os, Integer terminal){
        String courseView = COURSEVIEW + courseId;
        String courseViewId = COURSEVIEWID + courseId;
        String courseViewIosIn = COURSEVIEWIOSIN + courseId;
        String courseViewAndroidIn = COURSEVIEWANDRODIIN + courseId;
        String courseViewAndroidOut = COURSEVIEWANDROIDOUT + courseId;
        try {
            Course course = (Course) daoUtils.getById("Course",courseId);
            String albumId = course.getAlbumId();

            //添加专辑阅读量
            if (!"".equals(albumId)){
                String albumView = ALBUMVIEW + albumId;
                String albumViewId = ALBUMVIEWID + albumId;
                redisUtil.incr(albumView,1);
                redisUtil.hincr(albumViewId,userId,1);
            }

            //添加课程阅读量
            redisUtil.incr(courseView,1);
            redisUtil.hincr(courseViewId,userId,1);

            if (terminal==1){
                redisUtil.incr(courseViewAndroidOut,1);
            }else{
                if (os==1){
                    redisUtil.incr(courseViewAndroidIn,1);
                }else {
                    redisUtil.incr(courseViewIosIn,1);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 添加课程分享次数
     * @param courseId
     * @param userId
     * @param os
     */
    @Override
    public void courseShare(String courseId, String userId, Integer os){
        String courseShare = COURSESHARE + courseId;
        String courseShareId = COURSESHAREID + courseId;
        String courseShareIos = COURSESHAREIOS + courseId;
        String courseShareAndroid = COURSESHAREANDROID + courseId;
        try {
            redisUtil.incr(courseShare,1);
            redisUtil.hincr(courseShareId,userId,1);
            if (os==0){
                redisUtil.incr(courseShareIos,1);
            }else {
                redisUtil.incr(courseShareAndroid,1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 添加课程点赞
     * @param courseId
     * @param userId
     */
    @Override
    public void courseLike(String courseId, String userId){
        String courseLike = COURSELIKE + courseId;
        String courseLikeDetail = COURSELIKEDETAIL + userId;
        try {
            Object isLike =  redisUtil.hget(courseLikeDetail,courseId);
            if (isLike == null){
                redisUtil.incr(courseLike,1);
                redisUtil.hset(courseLikeDetail,courseId,"1");
            }else{
                Integer like = Integer.valueOf(isLike.toString());
                if (like==1){
                    redisUtil.decr(courseLike,1);
                    redisUtil.hset(courseLikeDetail,courseId,"0");
                }else{
                    redisUtil.incr(courseLike,1);
                    redisUtil.hset(courseLikeDetail,courseId,"1");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 查询课程阅读量
     * @param courseId
     * @return
     */
    @Override
    public int getCourseView (String courseId){
        Integer view = 0;
        Integer courseView = 0;
        Integer courseViewManager = 0;
        Integer liveView = 0;
        Object v1 = null;
        Object v2 = null;
        Object v3 = null;
        try {
            v1 = redisUtil.get(COURSEVIEW + courseId);
            v2 = redisUtil.get(ALBUMVIEWMANAGER + courseId);
            if (v1==null){
                Course course = (Course) daoUtils.getById("Course",courseId);
                redisUtil.set(COURSEVIEW + courseId,course.getCourseTotalView()+"");
                v1 = redisUtil.get(COURSEVIEW + courseId);
                if (v1 == null){
                    courseView = course.getCourseTotalView();
                }else{
                    courseView = Integer.valueOf(v1.toString());
                }
            }else{
                courseView = Integer.valueOf(v1.toString());
            }
            if (v2 == null){
                redisUtil.set(COURSEVIEWMANAGER + courseId,"0");
                courseViewManager = 0;
            }else {
                courseViewManager = Integer.valueOf(v2.toString());
            }
            LiveMaster liveMaster = liveMasterMobileRepository.findByCourseId(courseId);
            if (liveMaster!=null){
                String liveId = liveMaster.getId();
                v3 = redisUtil.get(LIVEVIEW + liveId);
                if (v3 == null){
                    redisUtil.set(LIVEVIEW + liveId,liveMaster.getLiveMasterView()+liveMaster.getLiveMasterView()+"");//读取直播阅读量
                    v3 = redisUtil.get(LIVEVIEW + liveId);
                    if (v3 == null){
                        liveView = liveMaster.getLiveMasterView();
                    }else{
                        liveView = Integer.valueOf(v3.toString());
                    }
                }else{
                    liveView = Integer.valueOf(v3.toString());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        view = courseView + courseViewManager + liveView;
        return view;
    }

    /**
     * 查询课程分享量
     * @param courseId
     * @return
     */
    @Override
    public int getCourseShare(String courseId){
        Integer share = 0;
        Integer courseShare = 0;
        Integer liveShare = 0;
        Object s1 = null;
        Object s2 = null;
        try {
            s1 = redisUtil.get(COURSESHARE + courseId);
            if (s1 == null){
                Course course = (Course) daoUtils.getById("Course",courseId);
                redisUtil.set(COURSESHARE + courseId,course.getCourseTotalShare()+"");
                s1 = redisUtil.get(COURSESHARE + courseId);
                if (s1==null){
                    courseShare = course.getCourseTotalShare();
                }else {
                    courseShare = Integer.valueOf(s1.toString());
                }
            }else {
                courseShare = Integer.valueOf(s1.toString());
            }
            LiveMaster liveMaster = liveMasterMobileRepository.findByCourseId(courseId);
            if (liveMaster != null){
                String liveId = liveMaster.getId();
                s2 = redisUtil.get(LIVESHARE + liveId);
                if (s2 == null){
                    redisUtil.set(LIVESHARE + liveId,liveMaster.getLiveMasterShare()+"");//读取直播分享量
                    s2 = redisUtil.get(LIVESHARE + liveId);
                    if (s2 == null){
                        liveShare = liveMaster.getLiveMasterShare();
                    }else {
                        liveShare = Integer.valueOf(s2.toString());
                    }
                }else {
                    liveShare = Integer.valueOf(s2.toString());
                }
            }else {
                liveShare = 0;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        share = courseShare + liveShare;
        return share;
    }

    /**
     * 查询课程点赞量
     * @param courseId
     * @return
     */
    @Override
    public int getCourseLike(String courseId){
        Integer like = 0;
        Object l = null;
        try {
            l = redisUtil.get(COURSELIKE + courseId);
            if (l == null){
                Course course = (Course) daoUtils.getById("Course",courseId);
                redisUtil.set(COURSELIKE + courseId,course.getCourseTotalLike()+"");
                l = redisUtil.get(COURSELIKE + courseId);
                if (l == null){
                    like = course.getCourseTotalLike();
                }else {
                    like = Integer.valueOf(l.toString());
                }
            } else {
                like = Integer.valueOf(l.toString());
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return like;
    }

    /**
     * 查询当前用户是否点赞
     * @param courseId
     * @param userId
     * @return
     */
    @Override
    public int getUserCourseLike(String courseId, String userId){
        Integer isLike =0;
        try {
            Object userLike = redisUtil.hget(COURSELIKEDETAIL + userId,courseId);
            if (userLike!=null){
                isLike = Integer.valueOf(userLike.toString());
            }else{
                CourseLike courseLike = courseLikeMobileRepository.findByCourseIdAndUserId(courseId, userId);
                if (courseLike != null){
                    isLike = courseLike.getLikeStatus();
                }else {
                    isLike = 0;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return isLike;
    }

}
