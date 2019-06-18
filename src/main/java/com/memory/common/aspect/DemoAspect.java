package com.memory.common.aspect;

import com.google.gson.Gson;
import com.memory.common.utils.StringUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @Auther: cui.Memory
 * @Date: 2018/11/2
 * @Description: AOP切面，织入切入点
 */
@Component
@Aspect
public class DemoAspect {

    private final static Logger logger = LoggerFactory.getLogger(DemoAspect.class);

    @Before("request_log()")
    public void doBefore(JoinPoint joinPoint){

    }
    @After("request_log()")
    public void doAfter(){
    }
    @AfterReturning(returning = "data", pointcut = "request_log()")
    public void doAfterReturning(Object data){
    }

    @Pointcut("execution(public * com.memory.*.controller.*.*(..))")
    public void request_log(){
    }

    /**
     * 环绕
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("request_log()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();

        // 开始打印请求日志
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //特殊字符过滤
        Enumeration<String> parameterNames = request.getParameterNames();
        while(parameterNames.hasMoreElements()){
            String parameter = parameterNames.nextElement();
            request.setAttribute(parameter, StringUtil.getHtmlIncodeByString(request.getParameter(parameter)));
        }

        String Url = request.getRequestURL().toString();
        String HttpMethod = request.getMethod();
        String ClassMethod =  proceedingJoinPoint.getSignature().getDeclaringTypeName()+ proceedingJoinPoint.getSignature().getName();
        String IP = request.getRemoteAddr();
        String RequestArgs = new Gson().toJson(request.getParameterMap());
        String ResponseArgs = new Gson().toJson(result);
        long times = System.currentTimeMillis() - startTime;

        logger.info(String.format(" %n" +
                        " ======Start====== " +
                        "%n URL : %s  " +
                        "%n HTTP Method : %s  " +
                        "%n Class Method : %s " +
                        "%n IP : %s " +
                        "%n Request Args : %s " +
                        "%n Response Args : %s " +
                        "%n Time-Consuming : %s " +
                        "%n ======End====== "  ,
                         Url,HttpMethod,ClassMethod,IP,RequestArgs,ResponseArgs,times));
        return result;
    }



}
