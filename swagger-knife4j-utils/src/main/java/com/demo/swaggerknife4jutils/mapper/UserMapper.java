package com.demo.swaggerknife4jutils.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.swaggerknife4jutils.bean.plus.UserDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description： 用户mapper
 * @author：weirx
 * @date：2022/1/17 14:55
 * @version：3.0
 */
@Mapper
public interface UserMapper extends BaseMapper<UserDO> {
}
