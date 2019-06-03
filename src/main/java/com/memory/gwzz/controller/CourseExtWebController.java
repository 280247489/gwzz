package com.memory.gwzz.controller;

import com.alibaba.fastjson.JSON;
import com.memory.cms.service.CourseCmsService;
import com.memory.cms.service.CourseMemoryService;
import com.memory.common.controller.BaseController;
import com.memory.common.utils.Message;
import com.memory.entity.jpa.Course;
import com.memory.entity.jpa.CourseExt;
import com.memory.gwzz.service.CourseExtWebService;
import com.memory.gwzz.service.CourseWebService;
import com.memory.redis.config.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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


    @Autowired
    private CourseWebService courseWebService;



    /**
     * URL：192.168.1.185：8081/gwzz/courseExt/list
     * @param courseId 课程ID
     * @return
     */
    @RequestMapping(value = "list"/*,method = RequestMethod.POST*/)
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
                //判断redis中是否含有此次课程数据
                //有课程数据
                if(obj != null){
                    System.out.println("redis有课程数据==============================");
                    if(!"notExist".equals(redisUtil.hget(keyHash,COURSE)) ){
                        Map<String,Object> map = new HashMap<>();
                        System.out.println("keyHash ===========" +keyHash);
                        map.put(COURSE,  redisUtil.hget(keyHash,COURSE));
                        map.put(COURSEEXT, JSON.parse( obj.toString()));
                        //share 统计计数
                        courseExtWebService.setCourseExtView(courseId,openId);
                        msg.setData(map);
                    }else{
                        msg.setData("notExist");
                    }

                }else{
                  //没有课程数据
                  //查询数据库，如果数据库中可以查询到数据则根据课程课程状态判断后添加到redis中

                    Course course = courseWebService.getCourseById(courseId);
                    if(course != null){
                        System.out.println("redis没有课程数据==============================");
                        //上线状态
                        if(course.getCourseOnline() ==  1){
                            System.out.println("redis没有课程数据，查询数据库，并且数据库的课程是上线状态,同步db2redis==============================");
                            //查询数据库db
                            List<CourseExt> list = courseExtWebService.getCourseExtByDB(courseId);
                            //添加到redis中
                            redisUtil.hset(keyHash, "course", course.getCourseTitle());
                            redisUtil.hset(keyHash, "courseExt", JSON.toJSONString(overMethod(list)));
                            //返回redis查询的数据
                            Map<String,Object> map = new HashMap<>();
                            map.put(COURSE,  redisUtil.hget(keyHash,COURSE));
                            map.put(COURSEEXT, JSON.parse( redisUtil.hget(keyHash,COURSEEXT).toString()));
                            msg.setData(map);
                        }else{
                            System.out.println("redis没有课程数据，查询数据库，并且数据库的课程是下线状态==============================");
                             //下线状态
                            //将redis中的数据赋值为notExist状态.
                            redisUtil.hset(keyHash, "course", "notExist");
                            redisUtil.hset(keyHash, "courseExt", JSON.toJSONString("notExist"));
                            msg.setData("notExist");
                        }

                    }else{
                        System.out.println("redis没有课程数据,数据库也没有数据==============================");
                        msg.setData("notExist");
                    }

                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return msg;
    }





    public List<Map<String,Object>> overMethod(List<CourseExt> extListSave){

        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

        for (int i = 0; i < extListSave.size(); i++) {
            CourseExt courseExt1 = extListSave.get(i);
            Map<String, Object> mapObj = new HashMap<>();
            mapObj.put("courseExtNickname", courseExt1.getCourseExtNickname());
            mapObj.put("courseExtSort", courseExt1.getCourseExtSort());
            mapObj.put("courseExtType", courseExt1.getCourseExtType());
            if(courseExt1.getCourseExtType()==1){
                mapObj.put("courseExtWords", courseExt1.getCourseExtWords());
            }else if(courseExt1.getCourseExtType()==2){
                mapObj.put("courseExtAudio", courseExt1.getCourseExtAudio());
                mapObj.put("courseExtAudioTimes", courseExt1.getCourseExtAudioTimes());
            }else if(courseExt1.getCourseExtType()==3){
                mapObj.put("courseExtImgUrl", courseExt1.getCourseExtImgUrl());
            }
            resultList.add(mapObj);
        }
        return  resultList;
    }





}
