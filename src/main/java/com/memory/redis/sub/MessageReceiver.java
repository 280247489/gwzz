package com.memory.redis.sub;

import org.springframework.stereotype.Component;

/**
 * @author INS6+
 * @date 2019/7/24 11:29
 */


@Component
public class MessageReceiver {

    /**接收消息的方法*/
    public void receiveMessage(String message){
        System.out.println(message);
    }

}

