package com.demo.interceptredisurlip.controller;

import com.demo.interceptredisurlip.common.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description 测试请求
 * @Date 2022/6/20 21:59
 * @Author HUANGXINWEI
 */
@Controller
@RequestMapping("/test")
@Slf4j
public class TestController {

    @RequestMapping("/test1")
    @ResponseBody
    public ApiResult test1(@RequestBody String str){
        log.info("测试请求入参={}", str);
        return ApiResult.success(null);
    }

}
