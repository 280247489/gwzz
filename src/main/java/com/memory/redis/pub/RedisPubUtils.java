package com.memory.redis.pub;

import com.alibaba.fastjson.JSON;
import com.memory.cms.controller.AdvertiseCmsController;
import com.memory.redis.pojo.SimpleMessage;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.logging.Logger;

/**
 * @author INS6+
 * @date 2019/7/24 11:29
 */

//redis 发布
@Service
public class RedisPubUtils {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(RedisPubUtils.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate ;


    /**
     * 推送消息
     * @param publisher
     * @param content
     */
    public void publish(String channel,String publisher,String content){
        System.out.println("publish message ...");
        log.info("message send {} by {}", content, publisher);

        ChannelTopic topic = new ChannelTopic(channel);

        SimpleMessage pushMsg = new SimpleMessage();
        pushMsg.setContent(content);
        pushMsg.setPublisher(publisher);
        pushMsg.setCreateTime(new Date());

        redisTemplate.convertAndSend(topic.getTopic(), JSON.toJSONString(pushMsg));

    }






}
