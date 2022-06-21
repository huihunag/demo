package com.demo.interceptredisurlip.util;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeansException;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @description 获取上下文
 * @Date 2022/6/20 21:21
 * @Author HUANGXINWEI
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    /**
     * 实现setApplicationContext 将注入的 ApplicationContext 赋值给 当前类中的applicationContext
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 获取Spring的上下文
     * @return
     */
    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }

    /**
     * 获取Spring上下文容器中所有Bean的名称
     * @return
     */
    public static String[] getBeanDefinitionNames(){
        return applicationContext.getBeanDefinitionNames();
    }

    /**
     * 根据Bean的名称获取Bean
     * @param name
     * @return
     */
    public static Object getBean(String name){
        return applicationContext.getBean(name);
    }

    /**
     * 根据class获取Bean
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz){
        return applicationContext.getBean(clazz);
    }

    /**
     * 根据Bean名称获取Class
     * @param name
     * @return
     */
    public static Class<?> getType(String name){
        return applicationContext.getType(name);
    }

    /**
     * 根据propertyName获取配置信息
     * @param propertyName
     * @return
     */
    public static Object getProperty(String propertyName){
        ConfigurableEnvironment env = (ConfigurableEnvironment) applicationContext.getEnvironment();
        return env.getProperty(propertyName);
    }

    /**
     * 根据property前缀获取配置列表
     * @param propertyPrefix
     * @return
     */
    public static List<String> getPropertyList(String propertyPrefix){

        AtomicReference<List<String>> result = new AtomicReference<>();

        ConfigurableEnvironment env = (ConfigurableEnvironment) applicationContext.getEnvironment();

        env.getPropertySources().stream().forEach(propertySource->{
            if (propertySource instanceof OriginTrackedMapPropertySource) {
                List<String> props = Arrays.asList(((OriginTrackedMapPropertySource) propertySource).getPropertyNames());
                result.set(props.stream().filter(e -> e.startsWith(propertyPrefix)).collect(Collectors.toList()));
            }
        });

        return result.get();
    }

    /**
     * 判断某个配置是否存在
     * @param propertyName
     * @param notBlank 不能是空字符或null
     * @return
     */
    public static boolean containProperty(String propertyName,boolean notBlank){

        Object prop =  applicationContext.getEnvironment().getProperty(propertyName);
        if(null==prop){
            return false;
        }else if(notBlank && Strings.isBlank(prop.toString())){
            return false;
        }
        return true;
    }
}
