/*
 * @Author: Axiuxiu
 * @Date: 2022-06-11 22:46:50
 * @LastEditTime: 2022-06-14 16:16:48
 * @Description: web配置
 * @Todo: 
 */
package com.example.musicserver.config;

import java.io.File;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.musicserver.manage.interceptor.AdminLoginInterceptor;

@Configuration
public class ManageWebMvcConfig implements WebMvcConfigurer {
    // spring 容器管理拦截器对象
    @Bean
    public HandlerInterceptor getALItcp() {
        return new AdminLoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getALItcp())
                .addPathPatterns("/manage/admin/**")
                .excludePathPatterns("/manage/admin/login", "/manage/admin/signup");
    }

    @Override
    /**
     * 上传文件资源定位
     */
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**")
                .addResourceLocations( // 注意这里要加上file:
                        "file:" + System.getProperty("user.dir") + File.separator + "upload" + File.separator);
    }
}
