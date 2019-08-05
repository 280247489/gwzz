package com.memory.redis.sub;

import com.memory.cms.service.LiveMemoryLoadService;
import com.memory.cms.service.LiveMemoryService;
import com.memory.entity.jpa.LiveMemoryLoad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author INS6+
 * @date 2019/7/24 11:29
 */


@Component
public class MessageReceiver {

    @Autowired
    private LiveMemoryLoadService liveMemoryLoadService;

    @Autowired
    private LiveMemoryService LIveMemoryService;

    public static MessageReceiver messageReceiver;

    @PostConstruct
    public void init(){
        messageReceiver = this;
    }


    /**接收消息的方法*/
    public void receiveMessage(String message){
        this.load_live_memory();
        System.out.println("==============接收消息=============");
        System.out.println(message);
    }
/*
    public void load_live_memory() {
        //获取数据加载状态为0的课程
        List<LiveMemoryLoad> loadList = liveMemoryLoadService.queryAllLiveMemoryLoadByLoadStatus(0);
        for (LiveMemoryLoad courseMemoryLoad : loadList) {
            String master_id = courseMemoryLoad.getId();
            LIveMemoryService.addLiveMemory(master_id);
        }

    }*/

    public static synchronized void load_live_memory(){

        //获取数据加载状态为0的课程
        List<LiveMemoryLoad> loadList = messageReceiver.liveMemoryLoadService.queryAllLiveMemoryLoadByLoadStatus(0);
        for (LiveMemoryLoad courseMemoryLoad : loadList) {
            String master_id = courseMemoryLoad.getId();
            messageReceiver.LIveMemoryService.addLiveMemory(master_id);
        }

    }

}

