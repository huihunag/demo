package com.demo.swaggerknife4jutils.utils.file.excel.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description: Excel注解集
 * @author: huangxinwei
 * @date: 2021/1/22 11:23
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Excels {
    Excel[] value();
}
