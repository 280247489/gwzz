package com.memory.rabbitmq;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;

import java.io.IOException;
import java.util.Map;

/**
 * @program parent
 * @Author: cui.Memory
 * @Date: 2018/11/26 20:23
 * @description:
 */
//@Component
public class RabbitMQReceive {
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "4", durable = "true"),
                    exchange = @Exchange(name = "group1", durable = "true", type = "fanout"),
                    key = "key.*"
            )
    )
    @RabbitHandler
    public void onMessage(@Payload String aa,
                          @Headers Map<String, Object> headers,
                          Channel channel){
        try {
            System.out.println("RabbitMQReceive: "+aa);
            channel.basicAck((long)headers.get(AmqpHeaders.DELIVERY_TAG), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
