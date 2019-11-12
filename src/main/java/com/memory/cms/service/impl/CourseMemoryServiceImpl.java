package com.memory.cms.service.impl;

import com.alibaba.fastjson.JSON;
import com.memory.cms.service.CourseCmsService;
import com.memory.cms.service.CourseMemoryLoadService;
import com.memory.cms.service.CourseMemoryService;
import com.memory.entity.jpa.Course;
import com.memory.entity.jpa.CourseExt;
import com.memory.entity.jpa.CourseMemoryLoad;
import com.memory.gwzz.service.CourseExtWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.memory.redis.CacheConstantConfig.*;

/**
 * @author INS6+
 * @date 2019/5/28 16:03
 */
@Service
public class CourseMemoryServiceImpl implements CourseMemoryService {


    @Autowired
    private CourseExtWebService courseExtWebService;

    @Autowired
    private CourseCmsService courseCmsService;

    @Autowired
    private CourseMemoryLoadService courseMemoryLoadService;

    public void addMemory(String courseId){
            String keyHash = SHARECOURSECONTENT + courseId;
            List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
            Map<String,Object> returnMap = new HashMap<String, Object>();

            List<CourseExt> extListSave = courseExtWebService.getCourseExtByDB(courseId);
            Course course =courseCmsService.getCourseById(courseId);
            if(course == null){
                return ;
            }


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

            returnMap.put("course",course.getCourseTitle());
            returnMap.put("courseExt",resultList);
            COURSEMAP.put(keyHash,returnMap);

            CourseMemoryLoad courseMemoryLoad = new CourseMemoryLoad();
            courseMemoryLoad.setCourseId(courseId);
            courseMemoryLoad.setContent(JSON.toJSONString(returnMap));
            courseMemoryLoad.setOperator("admin");
            courseMemoryLoad.setLoadStatus(0);
            courseMemoryLoad.setCreateTime(new Date());
            courseMemoryLoad.setUpdateTime(new Date());
            courseMemoryLoad.setCourseRedisKey(keyHash);
            courseMemoryLoadService.addCourseMemoryLoad(courseMemoryLoad);

    }

    public Object getCourseExtById(String courseId){
            String keyHash = SHARECOURSECONTENT + courseId;
            if( !COURSEMAP.containsKey(keyHash)){
                return null;
            }
        return COURSEMAP.get(keyHash);
    }


    public void clear(String courseId){
            String keyHash = SHARECOURSECONTENT + courseId;
            COURSEMAP.remove(keyHash);

            CourseMemoryLoad courseMemoryLoad =courseMemoryLoadService.getCourseMemoryLoadById(courseId);
            if(courseMemoryLoad != null && courseMemoryLoad.getLoadStatus() != 1){
                courseMemoryLoad.setCourseId(courseId);
                courseMemoryLoad.setLoadStatus(1);
                courseMemoryLoadService.updateCourseMemoryLoadById(courseMemoryLoad);

            }

    }

    public void clearAll(){

            for (Map.Entry<String, Object> entry : COURSEMAP.entrySet()) {
                String mapKey = entry.getKey();
                String courseId = mapKey.substring(mapKey.lastIndexOf(":")+1,mapKey.length());

                CourseMemoryLoad courseMemoryLoad =courseMemoryLoadService.getCourseMemoryLoadById(courseId);
                if(courseMemoryLoad != null && courseMemoryLoad.getLoadStatus() != 1){
                    courseMemoryLoad.setCourseId(courseId);
                    courseMemoryLoad.setLoadStatus(1);
                    courseMemoryLoadService.updateCourseMemoryLoadById(courseMemoryLoad);

                }

            }

            COURSEMAP.clear();

    }


    public Map<String,Object> findAll(){
        return COURSEMAP;
    }




}