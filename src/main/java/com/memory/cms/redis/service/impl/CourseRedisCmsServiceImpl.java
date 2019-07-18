package com.memory.cms.redis.service.impl;

import com.alibaba.fastjson.JSON;
import com.memory.cms.redis.service.CourseRedisCmsService;
import com.memory.cms.redis.service.LiveRedisCmsService;
import com.memory.cms.service.LiveMasterCmsService;
import com.memory.common.utils.Utils;
import com.memory.entity.jpa.LiveMaster;
import com.memory.redis.config.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.memory.redis.CacheConstantConfig.*;

/**
 * @author INS6+
 * @date 2019/5/14 11:00
 */
@Service
public class CourseRedisCmsServiceImpl  implements CourseRedisCmsService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private LiveRedisCmsService liveRedisCmsService;

    @Autowired
    private LiveMasterCmsService liveMasterCmsService;



    @Override
    public void initCourseRedisViewTotal(String courseId) {
        //后台添加阅读量数据初始化
        String key = COURSEVIEWMANAGER + courseId;
        //默认设置成0
        redisUtil.set(key,0+"");
    }

    @Override
    public void setCourseRedisViewTotal(String courseId, Integer cumulativeValue) {
        //后台添加阅读量数据初始化
        String key = COURSEVIEWMANAGER + courseId;
        //设置阅读量
        redisUtil.set(key,cumulativeValue+"");
    }

    @Override
    public Integer getCourseAndLiveRedisAllViewTotal(String courseId) {


        //课程实际阅读量
        Integer realViewTotal = getCourseAndLiveRedisRealViewTotal(courseId);

        //课程管理阅读量
        String managerViewTotalKey = COURSEVIEWMANAGER + courseId;
        Integer managerViewTotal =  (redisUtil.get(managerViewTotalKey) != null) ? (Integer.valueOf(redisUtil.get(managerViewTotalKey).toString())):0;

        //总阅读量 = 课程实际阅读量 + 管理设置阅读量
        Integer allViewTotal = realViewTotal + managerViewTotal;

        return allViewTotal;
    }

    @Override
    public Integer getCourseAndLiveRedisRealViewTotal(String courseId) {

        //课程实际阅读量
        String courseRealViewTotalKey = COURSEVIEW + courseId ;
        Integer courseRealViewTotalValue =(redisUtil.get(courseRealViewTotalKey)!=null)? (Integer.valueOf(redisUtil.get(courseRealViewTotalKey).toString()) ):0;

        LiveMaster master = liveMasterCmsService.getLiveMasterByCourseId(courseId);

        //直播实际阅读量
        // wait deal .......................
        Integer liveRedisViewTotalValue = liveRedisCmsService.getLiveRedisViewTotal(master.getId());


        //实际课程阅读量 = 课程实际阅读量 + 直播实际阅读量
        Integer realViewTotal = courseRealViewTotalValue + liveRedisViewTotalValue;

        return realViewTotal;

    }

    @Override
    public Integer getCourseRedisShareTotal(String courseId) {
        //课程分享数量
        String courseShareTotalKey = COURSESHARE + courseId;

        Integer courseShareTotal =  (redisUtil.get(courseShareTotalKey)!=null)?(Integer.valueOf(redisUtil.get(courseShareTotalKey).toString())):0;

        return courseShareTotal;
    }

    @Override
    public Integer getCourseRedisLikeTotal(String courseId) {
        //课程点赞数量
        String courseLikeTotalKey = COURSELIKE + courseId;
        Integer courseLikeTotal = (redisUtil.get(courseLikeTotalKey)!=null)?(Integer.valueOf(redisUtil.get(courseLikeTotalKey).toString())):0;

        return courseLikeTotal;
    }

    @Override
    public Integer getCourseRedisAllViewTotal(String courseId) {

        //课程实际阅读量
        Integer realViewTotal = getCourseRedisRealViewTotal(courseId);

        //课程管理阅读量
        String managerViewTotalKey = COURSEVIEWMANAGER + courseId;
        Integer managerViewTotal =  (redisUtil.get(managerViewTotalKey) != null) ? (Integer.valueOf(redisUtil.get(managerViewTotalKey).toString())):0;

        //总阅读量 = 课程实际阅读量 + 管理设置阅读量
        Integer allViewTotal = realViewTotal + managerViewTotal;

        return allViewTotal;
    }

    @Override
    public Integer getCourseRedisRealViewTotal(String courseId) {

        //课程实际阅读量
        String courseRealViewTotalKey = COURSEVIEW + courseId ;
        Integer courseRealViewTotalValue =(redisUtil.get(courseRealViewTotalKey)!=null)? (Integer.valueOf(redisUtil.get(courseRealViewTotalKey).toString())):0;

        return courseRealViewTotalValue;

    }

    @Override
    public Integer getCourseManagerViewTotal(String courseId) {
        //获取课程伪阅读量
        String courseManagerViewTotalKey = COURSEVIEWMANAGER + courseId;
        Integer courseManagerViewTotal = (Utils.isNotNull(redisUtil.get(courseManagerViewTotalKey)))?((Integer.valueOf(redisUtil.get(courseManagerViewTotalKey).toString()))):0;
        return courseManagerViewTotal;
    }


    @Override
    public List<Object> getCourseAndLiveRedisAllViewTotal(List<String> courseIds) {
        return null;
    }

    @Override
    public List<Object> getCourseAndLiveRedisRealViewTotal(List<String> courseIds) {
        return null;
    }

    @Override
    public List<Object> getCourseManagerViewTotal(List<String> courseIds) {
        return getMulti(COURSEVIEWMANAGER,courseIds);
    }

    @Override
    public List<Object> getCourseRedisAllViewTotal(List<String> courseIds) {
        return null;
    }

    @Override
    public List<Object> getCourseRedisRealViewTotal(List<String> courseIds) {
        return getMulti(COURSEVIEW,courseIds);
    }

    @Override
    public List<Object> getCourseRedisShareTotal(List<String> courseIds) {
        return getMulti(COURSESHARE,courseIds);
    }

    @Override
    public List<Object> getCourseRedisLikeTotal(List<String> courseIds) {
        return getMulti(COURSELIKE,courseIds);

    }


    public List<Object> getMulti(String keyLabel , List<String> keys) {
        List<String> finalKeys =new ArrayList<String>();
        for (String key : keys) {
            String queryKey = keyLabel + key;
            finalKeys.add(queryKey);
        }
        return (List<Object>) redisUtil.getMulti(finalKeys);
    }

}
