package com.demo.swaggerknife4jutils.mybatis;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

interface Demo{
    void say();
}

@SpringBootTest
public class ProxyTests {

    @Test
    void test() {

    }

    public Demo createProxyInstance(final InvocationHandler handler, final Class<?> clazz){
        return new Demo() {
            @Override
            public void say() {
                try {
                    // 模拟虚拟机获取接口方法拦截，并非实际虚拟机操作
                    Method sayMethod = clazz.getMethod("say");
                    Object invoke = handler.invoke(this, sayMethod,null);
                }catch (Exception e){
                    e.printStackTrace();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        };
    }

}
