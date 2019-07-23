package com.memory.cms.redis.service.impl;

import com.alibaba.fastjson.JSON;
import com.memory.cms.redis.service.AlbumRedisCmsService;
import com.memory.cms.redis.service.CourseRedisCmsService;
import com.memory.cms.redis.service.LiveRedisCmsService;
import com.memory.cms.service.CourseCmsService;
import com.memory.cms.service.LiveMasterCmsService;
import com.memory.common.utils.Utils;
import com.memory.entity.jpa.Course;
import com.memory.entity.jpa.LiveMaster;
import com.memory.redis.config.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import static com.memory.redis.CacheConstantConfig.ALBUMVIEW;
import static com.memory.redis.CacheConstantConfig.ALBUMVIEWMANAGER;

/**
 * @author INS6+
 * @date 2019/7/17 13:33
 */
@Service
public class AlbumRedisCmsServiceImpl implements AlbumRedisCmsService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private CourseCmsService courseCmsService;

    @Autowired
    private LiveMasterCmsService liveMasterCmsService;

    @Autowired
    private CourseRedisCmsService courseRedisCmsService;

    @Autowired
    private LiveRedisCmsService liveRedisCmsService;

    @Override
    public Integer getAlbumRealViewTotal(String albumId) {
        //获取指定专辑下的上线状态的课程
        List<Course> list= courseCmsService.queryCourseByAlbumIdAndCourseOnline(albumId,1);
        List<LiveMaster> masterList = liveMasterCmsService.queryLiveMasterList();
        Integer courseAllRealViewTotal = 0;
        Integer liveAllRealViewTotal = 0;
        for (Course course : list) {
            //统计课程实际阅读量
            courseAllRealViewTotal += courseRedisCmsService.getCourseRedisRealViewTotal(course.getId());

            for (LiveMaster master : masterList) {
                //直播和课程关联
                if(course.getId().equals(master.getCourseId())){
                    //统计直播实际阅读量
                    liveAllRealViewTotal += liveRedisCmsService.getLiveRedisViewTotal(master.getId());
                }

            }

        }

        Integer albumRealViewTotal = courseAllRealViewTotal + liveAllRealViewTotal;

        String albumRedisRealViewTotalKey = ALBUMVIEW + albumId;

        //同步 专辑 实际阅读量到redis
        redisUtil.set(albumRedisRealViewTotalKey, JSON.toJSON(albumRealViewTotal+""));

        return albumRealViewTotal;
    }

    @Override
    public Integer getAlbumManagerViewTotal(String albumId) {

        //获取指定专辑下的上线状态的课程
        List<Course> list= courseCmsService.queryCourseByAlbumIdAndCourseOnline(albumId,1);
        List<LiveMaster> masterList = liveMasterCmsService.queryLiveMasterList();

        Integer courseManagerViewTotal = 0;

        for (Course course : list) {

            courseManagerViewTotal += courseRedisCmsService.getCourseManagerViewTotal(course.getId());

        }

        String albumViewManagerKey =  ALBUMVIEWMANAGER +  albumId;
        //同步专辑 伪阅读量到redis
        redisUtil.set(albumViewManagerKey,JSON.toJSON(courseManagerViewTotal+""));

        return courseManagerViewTotal;
    }

    @Override
    public Integer getAlbumRealViewTotalWithList(String albumId) {
        //获取指定专辑下的上线状态的课程
        List<Course> list= courseCmsService.queryCourseByAlbumIdAndCourseOnline(albumId,1);
        List<LiveMaster> masterList = liveMasterCmsService.queryLiveMasterList();
        Integer courseAllRealViewTotal = 0;
        Integer liveAllRealViewTotal = 0;
        List<String> courseIds = new ArrayList<String>();
        for (Course course : list) {
            courseIds.add(course.getId());
        }

        //课程实际阅读数
        List<Object>  redisRealViewValues =courseRedisCmsService.getCourseRedisRealViewTotal(courseIds);
        for (Object redisRealViewValue : redisRealViewValues) {
            courseAllRealViewTotal += Utils.getIntVal(redisRealViewValue);
        }

        //绑定了课程的直播列表
        List<LiveMaster> bindMasterList = liveMasterCmsService.queryLiveMasterByInCourseId(courseIds);
        List<String> liveIds = new ArrayList<String>();
        for (LiveMaster master : bindMasterList) {
            liveIds.add(master.getId());
        }
        List<Object> redisLiveRealViewValues = liveRedisCmsService.getLiveRedisViewTotal(liveIds);
        for (Object redisLiveRealViewValue : redisLiveRealViewValues) {
            liveAllRealViewTotal += Utils.getIntVal(redisLiveRealViewValue);
        }
        Integer albumRealViewTotal = courseAllRealViewTotal + liveAllRealViewTotal;

        String albumRedisRealViewTotalKey = ALBUMVIEW + albumId;

        //同步 专辑 实际阅读量到redis
        redisUtil.set(albumRedisRealViewTotalKey, JSON.toJSON(albumRealViewTotal+""));

        return albumRealViewTotal;
    }

    @Override
    public Integer getAlbumManagerViewWithList(String albumId) {
        //获取指定专辑下的上线状态的课程
        List<Course> list= courseCmsService.queryCourseByAlbumIdAndCourseOnline(albumId,1);

        List<LiveMaster> masterList = liveMasterCmsService.queryLiveMasterList();
        List<String> courseIds = new ArrayList<String>();
        Integer courseManagerViewTotal = 0;
        for (Course course : list) {
            courseIds.add(course.getId());
        }
        List<Object> redisManagerViewValues = courseRedisCmsService.getCourseManagerViewTotal(courseIds);
        for (Object redisManagerViewValue : redisManagerViewValues) {
            courseManagerViewTotal += Utils.getIntVal(redisManagerViewValue);
        }

        String albumViewManagerKey =  ALBUMVIEWMANAGER +  albumId;
        //同步专辑 伪阅读量到redis
        redisUtil.set(albumViewManagerKey,JSON.toJSON(courseManagerViewTotal+""));

        return courseManagerViewTotal;
    }

    @Override
    public Integer getAlbumAllViewTotal(String albumId) {

        Integer albumRealViewTotal = getAlbumRealViewTotalWithList(albumId);
        Integer courseManagerViewTotal = getAlbumManagerViewWithList(albumId);
        Integer albumAllViewTotal = albumRealViewTotal + courseManagerViewTotal;
        return albumAllViewTotal;
    }

    @Override
    public Integer getAlbumAllViewTotalByKey(String albumId) {

        String albumViewTotal = ALBUMVIEW +albumId;
        Integer albumRealViewTotal = (Utils.isNotNull(redisUtil.get(albumViewTotal)))?(Integer.valueOf(redisUtil.get(albumViewTotal).toString())):0;
        String albumManagerViewTotal = ALBUMVIEWMANAGER + albumId;
        Integer courseManagerViewTotal = (Utils.isNotNull(redisUtil.get(albumManagerViewTotal)))?(Integer.valueOf(redisUtil.get(albumManagerViewTotal).toString())):0;
        Integer albumAllViewTotal = albumRealViewTotal + courseManagerViewTotal;
        return albumAllViewTotal;
    }
}
