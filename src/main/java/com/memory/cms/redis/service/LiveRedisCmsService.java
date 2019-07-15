package com.memory.cms.redis.service;

import java.util.List;
import java.util.Map;

/**
 * @author INS6+
 * @date 2019/7/15 8:56
 */

public interface LiveRedisCmsService {

    //redis 数据初始化置空("notExist")
    void live2RedisNotExist(String uuid);
    //更新redis数据，设置master 和slave 的值
    void live2Redis(String uuid,String title, List<Map<String,Object>> slave);
    //获取live:comment:的key
    String getKey(String uuid);
    //redis计数，统计阅读数量和阅读人id
    void total2Redis(String uuid,String openId,Integer terminalType,Integer os);
    //获取redis hash key slave 的值
    Object getSlaveById(String uuid);
    //获取redis hash key master 的值
    Object getMasterNameById(String uuid);
}
