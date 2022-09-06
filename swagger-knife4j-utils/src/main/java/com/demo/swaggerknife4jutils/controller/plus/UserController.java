package com.demo.swaggerknife4jutils.controller.plus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.swaggerknife4jutils.bean.plus.UserDO;
import com.demo.swaggerknife4jutils.common.ApiResult;
import com.demo.swaggerknife4jutils.service.plus.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description： 用户controller
 * @author：weirx
 * @date：2022/1/17 17:39
 * @version：3.0
 */
@RestController
@RequestMapping("/user")
@Api(tags = "Mybatis-Plus测试", description = "Mybatis-Plus测试")
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * description: 新增
     * @param userDO
     * @return: boolean
     * @author: weirx
     * @time: 2022/1/17 19:11
     */
    @PostMapping("/save")
    @ApiOperation("测试新增")
    public ApiResult save(@RequestBody UserDO userDO) {
        boolean sign = userService.saveOrUpdate(userDO);
        return ApiResult.success(sign);
    }

    /**
     * description: 修改
     * @param userDO
     * @return: boolean
     * @author: weirx
     * @time: 2022/1/17 19:11
     */
    @PostMapping("/update")
    @ApiOperation("测试修改")
    public ApiResult update(@RequestBody UserDO userDO) {
        boolean sign = userService.saveOrUpdate(userDO);
        return ApiResult.success(sign);
    }

    /**
     * description: 删除
     * @param id
     * @return: boolean
     * @author: weirx
     * @time: 2022/1/17 19:11
     */
    @PostMapping("/delete")
    @ApiOperation("测试删除")
    public ApiResult delete(@RequestParam Long id) {
        UserDO userDO = new UserDO();
        userDO.setId(id);
        boolean sign = userService.removeById(userDO);
        return ApiResult.success(sign);
    }

    /**
     * description: 列表
     * @return: java.util.List<com.wjbgn.user.entity.UserDO>
     * @author: weirx
     * @time: 2022/1/17 19:11
     */
    @PostMapping("/list")
    @ApiOperation("测试列表")
    public ApiResult list() {
        List<UserDO> list = userService.list();
        return ApiResult.success(list);
    }

    /**
     * description: 分页列表
     * @param current
     * @param size
     * @return: com.baomidou.mybatisplus.extension.plugins.pagination.Page
     * @author: weirx
     * @time: 2022/1/17 19:11
     */
    @PostMapping("/page")
    @ApiOperation("测试分页")
    public ApiResult page(@RequestParam int current, @RequestParam int size) {
        Page page = userService.page(new Page<>(current,size), new QueryWrapper(new UserDO()));
        return ApiResult.success(page);
    }

}
