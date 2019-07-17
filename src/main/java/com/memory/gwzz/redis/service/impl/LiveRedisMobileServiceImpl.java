package com.memory.gwzz.redis.service.impl;

import com.alibaba.fastjson.JSON;
import com.memory.domain.dao.DaoUtils;
import com.memory.entity.jpa.Course;
import com.memory.entity.jpa.LiveMaster;
import com.memory.gwzz.redis.service.LiveRedisMobileService;
import com.memory.redis.config.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.memory.redis.CacheConstantConfig.*;

/**
 * @ClassName LiveRedisMobileServiceImpl
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/22 17:15
 */
@Service(value = "liveRedisMobileService")
public class LiveRedisMobileServiceImpl implements LiveRedisMobileService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private DaoUtils daoUtils;

    /**
     * 将直播内容赋值成notExist
     * @param liveMasterId
     */
    @Override
    public void liveRedisNotExist(String liveMasterId) {
        String keyHash = SHARELIVECONTENT + liveMasterId;
        try {
            redisUtil.hset(keyHash, "master", "notExist");
            redisUtil.hset(keyHash, "slave", JSON.toJSONString("notExist"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 将直播内容放到redis 中
     * @param liveMasterId
     * @param title
     * @param slave
     */
    @Override
    public void liveRedis(String liveMasterId, String title, List<Map<String, Object>> slave) {
        String keyHash = SHARELIVECONTENT + liveMasterId;
        try {
            redisUtil.hset(keyHash, "master", title);
            redisUtil.hset(keyHash, "slave", JSON.toJSONString(slave));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 添加直播阅读量
     * @param liveMasterId
     * @param userId
     * @param terminal
     * @param os
     */
    @Override
    public void liveView(String liveMasterId, String userId, Integer os, Integer terminal){
        String liveView = LIVEVIEW + liveMasterId;
        String liveViewId = LIVEVIEWID + liveMasterId;
        String liveViewIosIN = LIVEVIEWIOSIN + liveMasterId;
        String liveViewIosOut = LIVEVIEWIOSOUT + liveMasterId;
        String liveViewAndroidIn = LIVEVIEWANDROIDIN + liveMasterId;
        String liveViewAndroidOut = LIVEVIEWANDROIDOUT + liveMasterId;
        try {
            //判断当前直播是否有专辑，有:专辑阅读量+1
            LiveMaster liveMaster = (LiveMaster) daoUtils.getById("LiveMaster",liveMasterId);
            if (liveMaster!=null){
                Course course = (Course) daoUtils.getById("Course",liveMaster.getCourseId());
                if (course!=null){
                    String aid = course.getAlbumId();
                    if (!"".equals(aid)){
                        redisUtil.incr(ALBUMVIEW + aid,1);
                    }
                }
            }

            redisUtil.incr(liveView,1);
            redisUtil.hincr(liveViewId,userId,1);
            if (terminal==0){
                if (os==0){
                    redisUtil.incr(liveViewIosIN,1);
                }else{
                    redisUtil.incr(liveViewAndroidIn,1);
                }
            }else{
                if (os==0){
                    redisUtil.incr(liveViewIosOut,1);
                }else{
                    redisUtil.incr(liveViewAndroidOut,1);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 添加直播分量量
     * @param liveMasterId
     * @param userId
     * @param os
     * @param terminal
     */
    @Override
    public void liveShare(String liveMasterId, String userId, Integer os, Integer terminal){
        String liveShare = LIVESHARE + liveMasterId;
        String liveShareId = LIVESHAREID + liveMasterId;
        String liveShareIosIn = LIVESHAREIOSIN + liveMasterId;
        String liveShareIosOut = LIVESHAREIOSOUT + liveMasterId;
        String liveShareAndroidIn = LIVESHAREANDROIDIN + liveMasterId;
        String liveShareAndroidOut = LIVESHAREANDROIDOUT + liveMasterId;
        try {
            redisUtil.incr(liveShare,1);
            redisUtil.hincr(liveShareId,userId,1);
            if (terminal==1){
                if (os==0){
                    redisUtil.incr(liveShareIosOut,1);
                }else{
                    redisUtil.incr(liveShareAndroidOut,1);
                }
            }else{
                if (os==0){
                    redisUtil.incr(liveShareIosIn,1);
                }else{
                    redisUtil.incr(liveShareAndroidIn,1);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 查询直播阅读量
     * @param liveMasterId
     * @return
     */
    @Override
    public int getLiveView (String liveMasterId){
        Integer liveView = 0;
        Object v = null;
        try {
            v = redisUtil.get(LIVEVIEW + liveMasterId);
            if (v == null){
                LiveMaster liveMaster = (LiveMaster) daoUtils.getById("LiveMaster",liveMasterId);
                if (liveMaster!=null){
                    redisUtil.set(LIVEVIEW + liveMasterId,liveMaster.getLiveMasterView()+"");
                    v = redisUtil.get(LIVEVIEW + liveMasterId);
                    if (v == null){
                        liveView = liveMaster.getLiveMasterView();
                    }else{
                        liveView = Integer.valueOf(v.toString());
                    }
                }
            }else {
                liveView = Integer.valueOf(v.toString());
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return liveView;
    }

    /**
     * 查询直播的分享量
     * @param liveMasterId
     * @return
     */
    @Override
    public int getLiveShare (String liveMasterId){
        Integer liveShare = 0;
        Object s = null;
        try {
            s = redisUtil.get(LIVESHARE + liveMasterId);
            if (s == null){
                LiveMaster liveMaster = (LiveMaster) daoUtils.getById("LiveMaster",liveMasterId);
                if (liveMaster!=null){
                    redisUtil.set(LIVESHARE + liveMasterId,liveMaster.getLiveMasterShare()+"");
                    s = redisUtil.get(LIVEVIEW + liveMasterId);
                    if (s == null){
                        liveShare = liveMaster.getLiveMasterShare();
                    }else {
                        liveShare = Integer.valueOf(s.toString());
                    }
                }
            }else {
                liveShare = Integer.valueOf(s.toString());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return liveShare;
    }

    /**
     * 获取直播内容
     * @param uuid
     * @return
     */
    @Override
    public Object getSlaveById(String uuid) {
        String keyHash = SHARELIVECONTENT + uuid;
        return redisUtil.hget(keyHash,"slave");
    }

}
