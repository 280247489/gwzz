package com.memory;

import com.memory.common.utils.BadWordUtil;
import com.memory.common.yml.MyRedisConfig;
import com.memory.entity.jpa.LiveMemoryLoad;
import com.memory.gwzz.service.LiveMemoryLoadMobileService;
import com.memory.gwzz.service.LiveMemoryMobileService;
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
    private LiveMemoryLoadMobileService liveMemoryLoadMobileService;

    @Autowired
    private LiveMemoryMobileService LiveMemoryMobileService;


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(MainApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }



    @PostConstruct
    public void init(){
        load_live_memory();
        load_illegalWord_2_memory();
    }



    /**
     * 违禁词项目启动初始化，加载到内存中
     */
    public void load_illegalWord_2_memory(){
        BadWordUtil.initWords();
    }

    /**
     * 重启服务，内存加载并发缓存课程
     */
    public void load_live_memory(){
        //获取数据加载状态为0的课程
        List<LiveMemoryLoad> loadList = liveMemoryLoadMobileService.queryAllLiveMemoryLoadByLoadStatus(0);
        for (LiveMemoryLoad courseMemoryLoad : loadList) {
            String master_id = courseMemoryLoad.getId();
            LiveMemoryMobileService.addLiveMemory(master_id);
        }
    }

}
