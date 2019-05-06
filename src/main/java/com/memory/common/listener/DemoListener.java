package com.memory.common.listener;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @Auther: cui.Memory
 * @Date: 2018/11/5 0005 14:40
 * @Description:
 * ApplicationStartedEvent ：spring boot启动开始时执行的事件
 * ApplicationEnvironmentPreparedEvent：spring boot 对应Enviroment已经准备完毕，但此时上下文context还没有创建。
 * ApplicationPreparedEvent：spring boot上下文context创建完成，但此时spring中的bean是没有完全加载完成的。
 * ApplicationFailedEvent：spring boot启动异常时执行事件
 */
@Component
public class DemoListener implements ApplicationListener<ApplicationStartedEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        System.out.println("onApplicationEvent-ApplicationStartedEvent");
    }
}
