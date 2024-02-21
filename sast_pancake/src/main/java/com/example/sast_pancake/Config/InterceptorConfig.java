package com.example.sast_pancake.Config;

import com.example.sast_pancake.interceptor.AdminInterceptor;
import com.example.sast_pancake.interceptor.JwtInterceptor;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Resource
    private JwtInterceptor jwtInterceptor;
    @Resource
    private AdminInterceptor adminInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //登录JWT拦截
        registry.addInterceptor(jwtInterceptor).addPathPatterns("/**").excludePathPatterns("/user/**");
        //管理员拦截
        registry.addInterceptor(adminInterceptor).addPathPatterns("/**").excludePathPatterns("/user/**");
    }



}


