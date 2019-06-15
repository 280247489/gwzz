package com.memory.common.schedule;

import com.memory.cms.controller.AlbumCmsController;
import com.memory.cms.service.CourseCmsService;
import com.memory.redis.config.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Auther: cui.Memory
 * @Date: 2018/11/5 0005 13:50
 * @Description: 定时任务类
 * //开启定时任务类
 * SpringBoot启动类中添加 @EnableScheduling
 */
@Component
public class DemoTask {

    private static final Logger log = LoggerFactory.getLogger(DemoTask.class);
    private static final SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private CourseCmsService courseCmsService;


    @Scheduled(fixedRate = 3000)
    public void doTask(){
        System.out.println("CurrentTime: " + sf.format(new Date()));
    }


    /**
     * 同步h5直播阅读数量
     * 定时任务
     *
     * 只会同步课程状态为上线的课程阅读数量， 下线的课程的阅读数量在 操作下线的时候进行同步
     *
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void syncShareViewCount(){

        //courseCmsService.

        //1.获取需要同步的直播课程id

        //2.根据课程id 获取 redis 统计记录

        //3.同步数据到db

        //redisUtil.get()


    }

}
