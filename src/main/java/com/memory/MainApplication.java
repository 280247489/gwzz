package com.memory;

import com.alibaba.fastjson.JSON;
import com.memory.common.yml.MyRedisConfig;
import com.memory.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import redis.clients.jedis.JedisPool;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringBootApplication
public class MainApplication extends SpringBootServletInitializer {

    @Autowired
    private MyRedisConfig myRedisConfig;

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(MainApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }



    @PostConstruct
    public void init(){

        System.out.println("init this ... redis connecting ...");
        System.out.println(JSON.toJSONString(myRedisConfig));
        //redis初始化
        RedisUtil.initDialStatsPool(myRedisConfig.getHost(),myRedisConfig.getPort(),
                ("").equals(myRedisConfig.getPassword())?null:myRedisConfig.getPassword(),myRedisConfig.getTimeout(),myRedisConfig.getDatabase());

       // JedisPool jedis = RedisUtil.getDialStatsPool();
        //System.out.println(   JSON.toJSONString( RedisUtil.getByKey("xrLlwgWI1558084748010",jedis)));

        //RedisUtil.close(jedis.getResource());
    }

}
