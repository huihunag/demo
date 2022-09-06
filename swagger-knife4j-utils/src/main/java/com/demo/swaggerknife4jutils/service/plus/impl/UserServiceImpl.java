package com.demo.swaggerknife4jutils.service.plus.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.swaggerknife4jutils.bean.plus.UserDO;
import com.demo.swaggerknife4jutils.mapper.UserMapper;
import com.demo.swaggerknife4jutils.service.plus.IUserService;
import org.springframework.stereotype.Service;

/**
 * @description： 用户接口实现
 * @author：weirx
 * @date：2022/1/17 15:03
 * @version：3.0
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements IUserService {

}
