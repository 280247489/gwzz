package com.memory.common.aspect;

import com.google.gson.Gson;
import com.memory.common.utils.DateUtils;
import com.memory.common.utils.StringUtil;
import com.memory.common.utils.Utils;
import com.memory.common.utils.BadWordUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Auther: cui.Memory
 * @Date: 2018/11/2
 * @Description: AOP切面，织入切入点
 */
@Component
@Aspect
public class DemoAspect {

    private final static Logger logger = LoggerFactory.getLogger(DemoAspect.class);


    @Pointcut("execution(public * com.memory.*.controller.*.*(..))")
    public void request_log(){
    }


    @Pointcut("execution(public * com.memory.gwzz.controller.ArticleCommentMobileController.add(..)) " )
    public void filterWords(){
    }

    @Before("request_log()")
    public void doBefore(JoinPoint joinPoint){

    }
    @After("request_log()")
    public void doAfter(){
    }
    @AfterReturning(returning = "data", pointcut = "request_log()")
    public void doAfterReturning(Object data){
    }



    /**
     * Request Response 日志打印
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("request_log()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) {
        Object result = null;
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        try {
            String methodName = proceedingJoinPoint.getSignature().getName();
            String Url = request.getRequestURL().toString();
            String ClassMethod =  proceedingJoinPoint.getSignature().getDeclaringTypeName()+"."+ methodName;
            String HttpMethod = request.getMethod();
            String IP = request.getRemoteAddr();
            Object RequestArgs= new Gson().toJson(getParamInfo(proceedingJoinPoint).get("args"));
            //字符串参数过滤 < >
            Object[] obj = proceedingJoinPoint.getArgs();
            //jodit富文本编辑器内容不做<>过滤处理
            if(!methodName.equals("addCourse")  && !methodName.equals("updateCourse")
                    && !methodName.equals("addArticle")    && !methodName.equals("updateArticle")){
                for (int i=0; i<obj.length;i++){
                    if(obj[i] instanceof String){
                        //**为空参数不处理**
                        if(!"".equals(obj[i]) && obj[i]!=null ){
                            obj[i]=  StringUtil.getHtmlIncodeByString( obj[i].toString());
                        }
                    }
                }
            }

            result = proceedingJoinPoint.proceed(obj);

            String ResponseArgs = new Gson().toJson(result);

            logger.info(String.format(
                    "%n ======Start====== " +
                            "%n Times : " +DateUtils.getCurrentDate() +
                            "%n URL : %s  " +
                            "%n HTTP Method : %s  " +
                            "%n Class Method : %s " +
                            "%n IP : %s " +
                            "%n Request Args : %s " +
                            "%n +++++++++++++++++++++++++  " +
                            "%n Response Args : %s " +
                            "%n ======End====== "  ,
                    Url,HttpMethod,ClassMethod,IP,RequestArgs,ResponseArgs));
        }catch (Throwable e){
            e.printStackTrace();
        }

        return result;
    }


    /**
     * 评论相关违禁词过滤,替换成*
     * ******评论相关接口需固定传入参数（content，content_replace）***
     *
     * @param proceedingJoinPoint
     * @return
     */
    @Around("filterWords()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint){
        Object returnVal =null;
        try{
            // 开始打印请求日志
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            String content =  request.getParameter("content");
            String content_replace = BadWordUtil.replaceBadWord(content,2,"*");
            String[] parameterNames = ((MethodSignature) proceedingJoinPoint.getSignature()).getParameterNames();
            Map<String,Integer> mapIndex = new HashMap<String, Integer>();
            for (int i=0;i<parameterNames.length;i++){
                String key = parameterNames[i];
                if("content_replace".equals(key)){
                    mapIndex.put("content_replace",i);
                }
            }
            Object [] objects = proceedingJoinPoint.getArgs();
            objects[mapIndex.get("content_replace")] = content_replace;
            returnVal = proceedingJoinPoint.proceed(objects);
        }catch (Throwable e){
            e.printStackTrace();
        }
        return returnVal;

    }


    //获取key、value 形式的请求参数
    private ConcurrentHashMap<String, Object> getParamInfo(JoinPoint point) throws Exception {
        ConcurrentHashMap<String, Object> info = new ConcurrentHashMap<>(1);

        String[] parameterNames = ((MethodSignature) point.getSignature()).getParameterNames();
        ConcurrentHashMap<String, Object> args = null;

        if (Objects.nonNull(parameterNames)) {
            args = new ConcurrentHashMap<>(parameterNames.length);
            for (int i = 0; i < parameterNames.length; i++) {
                Object value = null;
                //**为空参数不参与处理**
                if( point.getArgs()!=null && point.getArgs()[i] != null){
                    if(Utils.isPrimite( point.getArgs()[i].getClass())){
                        value =  point.getArgs()[i].toString();
                        args.put(parameterNames[i], value);
                        //MultipartFile 文件不做处理
                    }else if(!"org.springframework.web.multipart.support.StandardMultipartHttpServletRequest".equals(point.getArgs()[i].getClass().getTypeName())){
                        value = new Gson().toJson(point.getArgs()[i]);
                        args.put(parameterNames[i], value);
                    }

                }else {
                    args.put(parameterNames[i], "");
                }
            }
            info.put("args", args);
        }
        return info;
    }





}
