package com.memory.cms.controller;

import com.memory.cms.service.CourseCmsService;
import com.memory.cms.service.CourseMemoryLoadService;
import com.memory.cms.service.LiveMasterCmsService;
import com.memory.common.utils.Result;
import com.memory.common.utils.ResultUtil;
import com.memory.entity.bean.Course;
import com.memory.entity.jpa.CourseMemoryLoad;
import com.memory.entity.jpa.LiveMaster;
import org.apache.solr.common.util.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author INS6+
 * @date 2019/5/29 12:15
 */
@RestController
@RequestMapping(value="courseMemoryLoad")
public class CourseMemoryLoadController {

    @Autowired
    private CourseMemoryLoadService courseMemoryLoadService;

    @Autowired
    private CourseCmsService courseService;


    @Autowired
    private LiveMasterCmsService liveMasterCmsService;

    @RequestMapping(value = "/findAll", method = RequestMethod.POST)
    public Result findAll(){
        Result result = new Result();
        try {
            List<Map<String,Object>> returnMap = new ArrayList<Map<String,Object>>();
            Map<String ,Object> mapper = new HashMap<String ,Object>();
            //List<LiveMaster> options = liveMasterCmsService.queryListMasterOptions();
            List<com.memory.entity.bean.Course>  optionsList = courseService.queryCourseOptions();
            for (Course course : optionsList) {
                mapper.put(course.getId(),course.getCourseTitle());
            }

            List<CourseMemoryLoad> list=  courseMemoryLoadService.queryAllCourseMemoryLoadByLoadStatus(0);
            for (CourseMemoryLoad courseMemoryLoad : list) {
                Map<String,Object> map = new HashMap<>();
                map.put("courseId",courseMemoryLoad.getCourseId());
                map.put("content",courseMemoryLoad.getContent());
                map.put("operator",courseMemoryLoad.getOperator());
                map.put("loadStatus",courseMemoryLoad.getLoadStatus());
                map.put("createTime",courseMemoryLoad.getCreateTime());
                map.put("updateTime",courseMemoryLoad.getUpdateTime());
                map.put("courseRedisKey",courseMemoryLoad.getCourseRedisKey());
                map.put("titleName",mapper.get(courseMemoryLoad.getCourseId()));
                returnMap.add(map);

            }
            result = ResultUtil.success(returnMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }





}
