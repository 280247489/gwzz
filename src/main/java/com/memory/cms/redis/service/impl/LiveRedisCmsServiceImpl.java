package com.memory.cms.redis.service.impl;
import com.alibaba.fastjson.JSON;
import com.memory.cms.redis.service.LiveRedisCmsService;
import com.memory.common.utils.Utils;
import com.memory.redis.config.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import static com.memory.redis.CacheConstantConfig.*;
import static com.memory.redis.CacheConstantConfig.LIVEVIEWID;

/**
 * @author INS6+
 * @date 2019/7/15 8:57
 */
@Service
public class LiveRedisCmsServiceImpl implements LiveRedisCmsService {

    @Autowired
    private RedisUtil redisUtil;


    @Override
    public void live2RedisNotExist(String uuid) {
        String keyHash = getKey(uuid);
        redisUtil.hset(keyHash, "master", "notExist");
        redisUtil.hset(keyHash, "slave", JSON.toJSONString("notExist"));
    }

    @Override
    public void live2Redis(String uuid,String title, List<Map<String,Object>> slave) {
        String keyHash = getKey(uuid);
        redisUtil.hset(keyHash, "master", title);
        redisUtil.hset(keyHash, "slave", JSON.toJSONString(slave));
    }

    @Override
    public String getKey(String uuid){
        return  LIVECOMMENT + uuid;
    }

    @Override
    public void total2Redis(String uuid,String openId,Integer terminalType,Integer os) {
        String keyCourseView = LIVEVIEW + uuid;
        String keyCourseViewOs ="";
        String keyCourseViewId= LIVEVIEWID +uuid;

        // ios
        if(os == 0){
            //app
            if(terminalType == 0){
                keyCourseViewOs = LIVEVIEWIOSIN + uuid;
                //h5
            }else {
                keyCourseViewOs = LIVEVIEWIOSOUT +uuid;
            }
            // android
        }else {
            //app
            if(terminalType == 0){
                keyCourseViewOs = LIVEVIEWANDROIDIN + uuid;
                //h5
            }else {
                keyCourseViewOs = LIVEVIEWANDROIDOUT +uuid;
            }
        }

            redisUtil.incr(keyCourseView,1);
            redisUtil.incr(keyCourseViewOs,1);
            redisUtil.hincr(keyCourseViewId,openId,1);

    }

    @Override
    public Object getSlaveById(String uuid) {
        String keyHash = getKey(uuid);
        return redisUtil.hget(keyHash,"slave");
    }

    @Override
    public Object getMasterNameById(String uuid) {
        String keyHash = getKey(uuid);
        return redisUtil.hget(keyHash,"master");
    }

    @Override
    public Integer getLiveRedisViewTotal(String uuid) {
        //获取直播阅读数
        String liveViewTotalKey = LIVEVIEW + uuid;

        Integer liveViewTotal = (Utils.isNotNull(redisUtil.get(liveViewTotalKey)))?(Integer.valueOf(redisUtil.get(liveViewTotalKey).toString())):0;

        return liveViewTotal;
    }

    @Override
    public Integer getLiveRedisShareTotal(String uuid) {
        //获取直播分享量
        String liveShareTotalKey = LIVESHARE + uuid;
        Integer liveShareTotal =(Utils.isNotNull(redisUtil.get(liveShareTotalKey)))?(Integer.valueOf(redisUtil.get(liveShareTotalKey).toString())):0;
        return liveShareTotal;
    }

    @Override
    public Integer getLiveRedisLikeTotal(String uuid) {
        return 0;
    }
}
