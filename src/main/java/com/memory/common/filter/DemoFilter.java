package com.memory.common.filter;

import com.alibaba.fastjson.JSON;
import com.memory.common.utils.StringUtil;
import com.memory.common.utils.Utils;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;


/**
 * @Auther: cui.Memory
 * @Date: 2018/11/5 0005 14:24
 * @Description: 过滤器类
 */
//@Component
//@WebFilter(urlPatterns = "/*")

public class DemoFilter implements Filter {
    private final static Logger logger = LoggerFactory.getLogger(DemoFilter.class);

    private String [] filterStr ={".js",".html",".png",".jpg",".css",".ico",".gif"};

  //  private static Integer count =0;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
//        System.out.println("DemoFilter-init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        /*System.out.println("DemoFilter-doFilter");*/
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        //特殊字符过滤
        Enumeration<String> parameterNames = req.getParameterNames();
        while(parameterNames.hasMoreElements()){
            String parameter = parameterNames.nextElement();
            req.setAttribute(parameter, StringUtil.getHtmlIncodeByString(req.getParameter(parameter)));
        }

        String URL = req.getRequestURI();
        //过滤静态资源的打印
        if(!isStaticResource(URL)){
            //StringBuffer httpServletRequest = Utils.getResult();
            //请求类型，请求地址，请IP
            StringBuffer stringBuffer = new StringBuffer("" +
                    "  --------begin--------------------" +
                    "  → Request : " + req.getMethod() + " | " + req.getRemoteAddr() +
                    "  → URL : " + req.getRequestURL() +
                    "  → Params :" + JSON.toJSONString(req.getParameterMap()) +
                    "  -------------------end-----------");
            logger.info("↓↓↓"+stringBuffer.toString());
        }


      //  count++;
   //     System.out.println("count ============================================================="+count);

        filterChain.doFilter(req, res);
    }

    @Override
    public void destroy() {
//        System.out.println("DemoFilter-destroy");
    }

    private boolean isStaticResource(String url){
        boolean flag = false;
        for (String str: filterStr) {
            if(url.indexOf(str) >-1){
                flag =true;
            }
        }
        return flag;
    }


}
