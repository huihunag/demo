package com.demo.swaggerknife4jutils.controller;

import com.demo.swaggerknife4jutils.common.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
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
@Api(tags = "测试请求", description = "测试请求")
public class TestController {

    @PostMapping("/test1")
    @ResponseBody
    @ApiOperation("测试请求")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "str", value = "请求数据体json", required = true)
    })
    public ApiResult test1(@RequestBody String str){
        log.info("测试请求入参={}", str);
        return ApiResult.success(str);
    }

}
