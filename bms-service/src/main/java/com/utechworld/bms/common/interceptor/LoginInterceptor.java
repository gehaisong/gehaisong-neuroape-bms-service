package com.utechworld.bms.common.interceptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Enumeration;

/**
 * 拦截器到 WebMvcConfigurer 配置类添加
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private static final Log log = LogFactory.getLog(LoginInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
//        Map reqMap=request.getParameterMap();
        Enumeration enumeration=request.getParameterNames();
        StringBuilder sb=new StringBuilder();
        while(enumeration.hasMoreElements()){
            String paraName=(String)enumeration.nextElement();
            Object paraValue=request.getParameter(paraName);
            sb.append(paraName+": "+paraValue).append(";");
        }
        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        if (ex==null){
            Method method = null;
            if (handler instanceof HandlerMethod){
                HandlerMethod methodHandle = (HandlerMethod) handler;
                method = methodHandle.getMethod();

            }

        }else {
            ex.printStackTrace();
        }
        super.afterCompletion(request, response, handler, ex);
    }


}
