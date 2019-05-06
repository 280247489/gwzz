package com.memory.common.schedule;

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
    private static final SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Scheduled(fixedRate = 3000)
    public void doTask(){
        System.out.println("CurrentTime: " + sf.format(new Date()));
    }
}
