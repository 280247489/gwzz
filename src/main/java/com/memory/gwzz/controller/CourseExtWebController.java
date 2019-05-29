package com.memory.gwzz.controller;

import com.alibaba.fastjson.JSON;
import com.memory.cms.service.CourseMemoryService;
import com.memory.common.controller.BaseController;
import com.memory.common.utils.Message;
import com.memory.gwzz.service.CourseExtWebService;
import com.memory.redis.config.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static com.memory.redis.CacheConstantConfig.*;


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
    private CourseMemoryService courseMemoryService;


    @Autowired
    private RedisUtil redisUtil;




    /**
     * URL：192.168.1.185：8081/gwzz/courseExt/list
     * @param courseId 课程ID
     * @return
     */
    @RequestMapping(value = "list",method = RequestMethod.POST)
    public Message list(@RequestParam String courseId,@RequestParam String openId){
        try {
            msg = Message.success();
            msg.setRecode(0);

            msg.setData(courseId);
         //  msg.setData(JSON.parseObject(JSON.toJSONString(courseExtWebService.getCourseExt(courseId)),HashMap.class) );
       //   msg.setData(JSON.parse(courseExtWebService.getCourseExt(courseId).toString()));

            String keyHash = SHARECOURSECONTENT + courseId;
            if( COURSEMAP.containsKey(keyHash)){
                courseExtWebService.setCourseExtView(courseId,openId);
                msg.setData(courseMemoryService.getCourseExtById(courseId));
                System.out.println("内存===============================");
            }else {

                System.out.println("redis===============================");

                Object obj =  redisUtil.hget(keyHash,COURSEEXT);
                if(obj != null){
                    courseExtWebService.setCourseExtView(courseId,openId);

                    Map<String,Object> map = new HashMap<>();
                  //  String keyHash = SHARECOURSECONTENT + courseId;
                    System.out.println("keyHash ===========" +keyHash);
                    map.put(COURSE,  redisUtil.hget(keyHash,COURSE));
                    map.put(COURSEEXT, JSON.parse( obj.toString()));

                    msg.setData(  map);
                }else{
                    msg.setData("");
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return msg;
    }






}
