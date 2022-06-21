package com.demo.interceptredisurlip.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * @description
 * @Date 2022/6/20 21:09
 * @Author HUANGXINWEI
 */
@Configuration
@Slf4j
public class MyWebAppConfig extends WebMvcConfigurerAdapter {
    @Bean
    IpUrlLimitInterceptor getIpUrlLimitInterceptor(){
        return new IpUrlLimitInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getIpUrlLimitInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
