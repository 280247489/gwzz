package com.memory.rabbitmq.utils;

import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @Auther: cui.Memory
 * @Date: 2018/11/20 0020 10:48
 * @Description:
 */
//@Component
public class RabbitMQUtil {
    private static final Logger logger = LoggerFactory.getLogger(RabbitMQUtil.class);
    private final static String EX_CHANGE_SINGLE = "ex_change_single";
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private Connection connection;
    @Autowired
    private Channel channel;

    //获取通信桥连接
    @Bean
    public Connection getConnection(){
        return rabbitTemplate.getConnectionFactory().createConnection();
    }
    //获取通信管道
    @Bean
    public Channel getChannel(){
        return connection.createChannel(false);
    }

    /**
     * RabbitMQ初始化
     * @throws Exception
     */
    public void init() throws Exception{
    }
    //发送消息-test
    public Boolean send(String msg) throws Exception{
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId("id");
        rabbitTemplate.convertAndSend("group1", "", msg, correlationData);
        return true;
    }

    /**
     * 发送消息
     * @param type
     * @param toId
     * @param msg
     * @return
     * @throws Exception
     */
    public Boolean send(String type, String toId, Object msg) throws Exception{
        CorrelationData correlationData = new CorrelationData();
        //correlationData.setId();
        rabbitTemplate.convertAndSend(
                type.toLowerCase().equals("single") ? EX_CHANGE_SINGLE : toId,
                type.toLowerCase().equals("single") ? toId : "",
                msg, correlationData);
        return true;
    }

    /**
     * 打开RabbitMQ链接
     * @param ioSession
     * @return
     */
    /*public boolean open(IoSession ioSession){
        String groupId = "group1";
        boolean flag = true;
        String userId = ioSession.getAttribute("uid").toString();
        try {
            //创建单聊路由
            //创建用户队列-userId
            //用户队列 绑定 单聊路由
            channel.exchangeDeclare(EX_CHANGE_SINGLE, BuiltinExchangeType.TOPIC, true);
            *//*
            Map<String, Object> queueArgs = new HashMap<String, Object>();
            queueArgs.put("x-dead-letter-exchange", "refreshDispatcherDeadExchange");  //死信队列
            queueArgs.put("x-message-ttl", 10000);     // 消息超时：让发布的message在队列中可以存活多长时间，以毫秒为单位。
            queueArgs.put("x-expires", 1000);          // 队列超时：当前的queue在指定的时间内，没有消费者订阅就会被删除，以毫秒为单位。
            queueArgs.put("x-max-length", 100);        // 队列最大长度：当超过了这个大小的时候，会删除之前最早插入的消息为本次的留出空间。
            queueArgs.put("x-queue-mode", "lazy");     //延迟加载：queue的信息尽可能的都保存在磁盘上，仅在有消费者订阅的时候才会加载到RAM中。
            *//*
            channel.queueDeclare(userId, false, false, true, null);
            channel.queueBind(userId, EX_CHANGE_SINGLE, userId);
            //---
            //创建群聊路由
            //用户队列 绑定 群聊路由
            channel.exchangeDeclare(groupId, BuiltinExchangeType.FANOUT, false, true , null);
            channel.queueBind(userId, groupId, "");
            //---
            //创建用户消费者-（多端消费，pc，mobile）
            //用户队列 绑定 用户消费者
            this.consumeStart(ioSession);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Mina open RabbitMQ Exception: {}", e.getStackTrace().toString());
            flag = false;
        }
        return flag;
    }*/

    /**
     * 关闭RabbitMQ链接
     * @param consumerTag
     * @return
     */
    public boolean close(String consumerTag){
        boolean flag = true;
        try {
            this.consumeEnd(consumerTag);
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
            logger.error("Mina close RabbitMQ Exception: {}", e.getStackTrace().toString());
        }
        return flag;
    }
    //开始消费消息
    /*private void consumeStart(final IoSession ioSession) throws Exception{
        String userId = ioSession.getAttribute("uid" ).toString();
        String type = ioSession.getAttribute("type" ).toString();
        String consumerTag = new StringBuffer(
                ioSession.getAttribute("type" ) + "-" +
                        ioSession.getAttribute("uid" )).toString();
        channel.basicQos(1);
        channel.basicConsume(userId, false, consumerTag,
                new DefaultConsumer(channel) {
                    @Override
                    public void handleDelivery(String consumerTag, Envelope envelope,
                                               AMQP.BasicProperties properties,
                                               byte[] body) throws IOException {
                        // 捕获消息内容
                        //String message = new String(body, "UTF-8");
                        //消息处理（自己实现的方法）
                        try {
                            Object imMessage = new ObjectInputStream(new ByteArrayInputStream(body)).readObject();
                            ioSession.write(imMessage);
                            if(type.equals("mobile")){
                                //消息确认
                                channel.basicAck(envelope.getDeliveryTag(), false);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            logger.error("Mina open RabbitMQ-consumeStart Exception: {}", e.getStackTrace().toString());
                        }

                    }
                });
        logger.info("注册消费者: {}", consumerTag);
    }*/
    //结束消费
    private void consumeEnd(String consumerTag) throws Exception{
        channel.basicCancel(consumerTag);
        logger.info("注销消费者: {}", consumerTag);
    }
    //移除群成员
    public void removeGroup(String groupId, String userId) throws Exception{
        channel.queueUnbind(userId, groupId, "");
    }
    //删除群聊路由
    public void delGroup(String groupId) throws Exception{
        channel.exchangeDelete(groupId, true);
    }
    //删除用户队列
    public void  delUser(String userId) throws Exception{
        channel.queueDelete(userId);
    }
}
