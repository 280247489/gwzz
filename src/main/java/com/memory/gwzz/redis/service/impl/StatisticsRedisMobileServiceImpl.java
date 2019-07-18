package com.memory.gwzz.redis.service.impl;

import com.memory.gwzz.redis.service.StatisticsRedisMobileService;
import com.memory.redis.config.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.memory.redis.CacheConstantConfig.VVANDROID;
import static com.memory.redis.CacheConstantConfig.VVIOS;

/**
 * @ClassName StatisticsRedisMobileServiceImpl
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/7/18 9:05
 */
@Service("statisticsRedisMobileService")
public class StatisticsRedisMobileServiceImpl implements StatisticsRedisMobileService {

    @Autowired
    private RedisUtil redisUtil;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 日活统计
     * @param userId
     * @param os
     */
    @Override
    public void SetVv(String userId,Integer os){
        Date date = new Date();
        try {
            String vvIos = VVIOS + simpleDateFormat.format(date);
            String vvAndroid = VVANDROID + simpleDateFormat.format(date);
            if (os==0){
                redisUtil.hincr(vvIos,userId,1);
            }else{
                redisUtil.hincr(vvAndroid,userId,1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
