package com.zhaolong.lesson4.configuration;

import com.zhaolong.lesson4.SessionIntercptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SessionConfiguration implements WebMvcConfigurer {

//    @Bean
//    SessionIntercptor sessionIntercptor(){
//        return  new SessionIntercptor();
//    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SessionIntercptor()).addPathPatterns("/user/**").excludePathPatterns("/student/**");

    }
    //资源处理
    @Override
    public  void addResourceHandlers(ResourceHandlerRegistry registry) {
        //路径替换
        registry.addResourceHandler("/zhaolong/1/resources/**").addResourceLocations("classpath:/static/");
    }

}
