package com.memory;

import com.alibaba.fastjson.JSON;
import com.memory.cms.service.CourseMemoryLoadService;
import com.memory.cms.service.CourseMemoryService;
import com.memory.common.yml.MyRedisConfig;
import com.memory.entity.jpa.CourseMemoryLoad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringBootApplication
public class MainApplication extends SpringBootServletInitializer {

    @Autowired
    private MyRedisConfig myRedisConfig;

    @Autowired
    private CourseMemoryLoadService courseMemoryLoadService;

    @Autowired
    private CourseMemoryService courseMemoryService;

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(MainApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }



    @PostConstruct
    public void init(){

        load_course_memory();

    }


    /**
     * 重启服务，内存加载并发缓存课程
     */
    public void load_course_memory(){
        //获取数据加载状态为0的课程
        List<CourseMemoryLoad>   loadList = courseMemoryLoadService.queryAllCourseMemoryLoadByLoadStatus(0);
        for (CourseMemoryLoad courseMemoryLoad : loadList) {
            String course_id = courseMemoryLoad.getCourseId();
            courseMemoryService.addMemory(course_id);
        }
    }


}
