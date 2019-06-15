package com.memory.common.filter;

import com.memory.common.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.*;
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

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
//        System.out.println("DemoFilter-init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        //特殊字符过滤
        Enumeration<String> parameterNames = req.getParameterNames();
        while(parameterNames.hasMoreElements()){
            String parameter = parameterNames.nextElement();
            System.out.println("value ===" + StringUtil.getHtmlIncodeByString(req.getParameter(parameter)));
            req.setAttribute(parameter, StringUtil.getHtmlIncodeByString(req.getParameter(parameter)));
        }

        filterChain.doFilter(req, res);
    }

    @Override
    public void destroy() {
    }



}
