package com.memory.common.filter;

import com.memory.common.utils.StringUtil;
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
@Component
@WebFilter(urlPatterns = "/*")
public class DemoFilter implements Filter {
    private final static Logger logger = LoggerFactory.getLogger(DemoFilter.class);
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
        //请求类型，请求地址，请IP
        StringBuffer stringBuffer = new StringBuffer("" +
                "\n--------begin--------------------" +
                "\n→ Request : " + req.getMethod() + " | " + req.getRemoteAddr() +
                "\n→ " + req.getRequestURL() +
                "\n→ " + req.getQueryString() +
                "\n-------------------end-----------");
        logger.info("↓↓↓"+stringBuffer.toString());
        filterChain.doFilter(req, res);
    }

    @Override
    public void destroy() {
//        System.out.println("DemoFilter-destroy");
    }
}
