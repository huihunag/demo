package com.demo.swaggerknife4jutils.controller.mask;

import com.alibaba.fastjson.JSONObject;
import com.demo.swaggerknife4jutils.bean.mask.User;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description 数据掩码
 * @Date 2022/8/30 9:39
 * @Author Administrator
 * @Param null
 * @Return
 */
@Controller
@RequestMapping("/mask")
@Slf4j
@Api(tags = "数据处理注解", description = "数据处理注解")
public class MaskController {

    @PostMapping("/mask")
    @ResponseBody
    @ApiOperation("数据掩码")
    public ApiResult mask(@RequestBody User user){
        log.info("测试请求入参={}", user);
        return ApiResult.success(user);
    }

}
