package com.utechworld.bms.common.config;

import com.utechworld.bms.common.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by gehaisong on 2018/4/26.
 * Spring Boot的自动配置是符合我们大多数需求的。在你既需要保留Spring Boot提供的便利，
 * 有需要增加自己的额外的配置的时候，可以定义一个配置类并继承WebMvcConfigurerAdapter
 */
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {
    /**
     * 添加拦截器：
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**");
    }
    /**
     * 跨域CORS配置
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        super.addCorsMappings(registry);
        registry.addMapping("/cors/**")
                .allowedHeaders("*")
                .allowedMethods("POST","GET")
                .allowedOrigins("*");
    }


}
