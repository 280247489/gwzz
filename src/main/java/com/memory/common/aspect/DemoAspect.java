package com.memory.common.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: cui.Memory
 * @Date: 2018/11/2
 * @Description: AOP切面，织入切入点
 */
//@Component
//@Aspect
public class DemoAspect {

    private final static Logger logger = LoggerFactory.getLogger(DemoAspect.class);

    @Before("request_log()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //请求类型，请求地址，请IP
        StringBuffer stringBuffer = new StringBuffer("========== Request : " + request.getMethod() +
                " " + request.getRequestURL() +
                " " + request.getRemoteAddr());
        logger.info(stringBuffer.toString());
        //请求类..方法
        logger.info("class_method = {}", joinPoint.getSignature().getDeclaringTypeName() + ".." +joinPoint.getSignature().getName() + "()");
        //请求参数
        logger.info("args = {}", joinPoint.getArgs());
    }
    @After("request_log()")
    public void doAfter(){
    }
    @AfterReturning(returning = "data", pointcut = "request_log()")
    public void doAfterReturning(Object data){
        /*HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //请求类型，请求地址，请IP
        StringBuffer stringBuffer = new StringBuffer("========== Response : " + request.getMethod() +
                " " + request.getRequestURL() +
                " " + request.getRemoteAddr());
        logger.info(stringBuffer.toString());
        logger.info("response = {}", data);*/
    }
    @Pointcut("execution(public * com.memory.xhm.controller.*.*(..))")
    public void request_log(){
    }
}
