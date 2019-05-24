package com.memory.gwzz.controller;

import com.alibaba.fastjson.JSON;
import com.memory.common.controller.BaseController;
import com.memory.common.utils.Message;
import com.memory.entity.bean.CourseResult;
import com.memory.gwzz.service.CourseExtWebService;
import com.memory.redis.CacheConstantConfig;
import com.memory.redis.RedisAPI;
import com.memory.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;


/**
 * @ClassName CourseExtController
 * @Descriotion TODO
 * @Author Ganxiqing
 * @Date 2019/5/10 14:55
 */
@RestController
@RequestMapping(value = "courseExt/web")
public class CourseExtWebController extends BaseController {


    @Autowired
    private CourseExtWebService courseExtWebService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate ;




    /**
     * URL：192.168.1.185：8081/gwzz/courseExt/list
     * @param courseId 课程ID
     * @return
     */
    @RequestMapping(value = "list",method = RequestMethod.POST)
    public Message list(@RequestParam String courseId){
       // JedisPool pool = RedisUtil.getDialStatsPool();
        try {
            msg = Message.success();
            msg.setRecode(0);
            String keyHash = CacheConstantConfig.COURSERXT + ":hash:" +courseId;
            String keySum =CacheConstantConfig.COURSERXT + ":sum:"+courseId;
            RedisAPI redisAPI = new RedisAPI( RedisUtil.getDialStatsPool());

           // redisAPI.incr(keySum);
           //  redisTemplate.opsForValue().increment(keySum, 1);
            //  msg.setData(  redisTemplate.opsForHash().entries(keyHash));
           // redisTemplate.opsForValue().increment(keySum, 1);
          //  Map<Object, Object> returnMap = redisTemplate.opsForHash().entries(keyHash);

            msg.setData(courseExtWebService.getCourseExt(courseId));
          //  msg.setData(returnMap);
          // JSON.toJSONString(redisAPI.hgetAll(keyHash)), CourseResult.class)
       //   msg.setData( redisAPI.hgetAll(keyHash));

        }catch (Exception e){
            e.printStackTrace();
        }finally {
           //  RedisUtil.close(pool.getResource());
        }

        //RedisUtil.returnBrokenResource(pool.getResource());
        // pool.close();
        // pool.returnResource(pool.getResource());

        return msg;
    }


}
