package com.memory.redis.sub;

import com.memory.entity.jpa.LiveMemoryLoad;
import com.memory.gwzz.service.LiveMemoryLoadMobileService;
import com.memory.gwzz.service.LiveMemoryMobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author INS6+
 * @date 2019/7/24 11:29
 */


@Component
public class MessageReceiver {

    @Autowired
    private LiveMemoryLoadMobileService liveMemoryLoadMobileService;

    @Autowired
    private LiveMemoryMobileService liveMemoryMobileService;


    /**接收消息的方法*/
    public void receiveMessage(String message){
        this.load_live_memory();
        System.out.println("==============接收消息=============");
        System.out.println(message);
    }

    public void load_live_memory(){
        //获取数据加载状态为0的课程
        System.out.println("+++++++++同步内存+++++++++");
        List<LiveMemoryLoad> loadList = liveMemoryLoadMobileService.queryAllLiveMemoryLoadByLoadStatus(0);
        for (LiveMemoryLoad courseMemoryLoad : loadList) {
            String master_id = courseMemoryLoad.getId();
            liveMemoryMobileService.addLiveMemory(master_id);
        }
    }

}

