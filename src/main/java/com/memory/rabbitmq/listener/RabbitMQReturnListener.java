package com.memory.rabbitmq.listener;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.ReturnListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @Auther: cui.Memory
 * @Date: 2018/11/26 0026 13:24
 * @Description:
 */
public class RabbitMQReturnListener implements ReturnListener {
    private static final Logger logger = LoggerFactory.getLogger(RabbitMQReturnListener.class);

    @Override
    public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
        logger.info("handleReturn");
    }
}
