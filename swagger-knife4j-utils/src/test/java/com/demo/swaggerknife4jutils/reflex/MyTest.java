package com.demo.swaggerknife4jutils.reflex;

import com.demo.swaggerknife4jutils.reflex.service.UserService;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Locale;

public class MyTest {

    @Test
    public void test() throws Exception {
        UserController userController = new UserController();
        Class<? extends UserController> clazz = userController.getClass();
        // 创建对象
        UserService userService = new UserService();
        System.out.println(userService);
        // 获取所有的属性
        Field serviceField = clazz.getDeclaredField("userService");
        serviceField.setAccessible(true);
        // 只有通过具体的方法才能够设置具体的属性值
        String name = serviceField.getName();
        // 拼接方法的名称
        name = name.substring(0,1).toUpperCase() + name.substring(1,name.length());
        String setMethodNme = "set" + name;
        // 通过方法注入属性的对象
        Method method = clazz.getMethod(setMethodNme, UserService.class);
        // 反射
        method.invoke(userController,new UserService());
        System.out.println(userController.getUserService());
    }

}
