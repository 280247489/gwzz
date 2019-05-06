package com.memory.common.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Auther: cui.Memory
 * @Date: 2018/11/5 0005 11:17
 * @Description: 拦截器配置类
 */
@Configuration
public class DemoMvcConfigurer implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry interceptorRegistry) {
        interceptorRegistry.addInterceptor(new DemoInterceptor()).addPathPatterns("/*/**");
                             //.excludePathPatterns("/*/**"); //拦截全部 /*/**

        WebMvcConfigurer.super.addInterceptors(interceptorRegistry);
    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/*/**").allowedOrigins("http://domain.com", "http://domain2.com")
//                .allowedMethods("GET", "POST", "DELETE", "PUT", "OPTIONS")
//                .allowCredentials(false).maxAge(3600);
//    }
}
