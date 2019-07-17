package com.memory.gwzz.redis.service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName LiveRedisMobileService
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/6/22 17:14
 */
public interface LiveRedisMobileService {
    void liveRedisNotExist(String uuid);

    void liveRedis(String uuid, String title, List<Map<String, Object>> slave);

    void liveView(String liveId, String userId, Integer terminal, Integer os);

    void liveShare(String liveMasterId, String userId, Integer os, Integer terminal);

    int getLiveView(String liveMasterId);

    int getLiveShare(String liveMasterId);

    Object getSlaveById(String uuid);
}
