package com.demo.swaggerknife4jutils.bean.plus;

import com.baomidou.mybatisplus.annotation.*;
import com.demo.swaggerknife4jutils.bean.enums.SexEnum;
import com.demo.swaggerknife4jutils.bean.mask.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@TableName(value = "user")
@Data
@ApiModel(value = "Mybatis-Plus测试类", subTypes = {User.class},discriminator = "type")
public class UserDO {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键ID")
    private Long id;

    /**
     * 昵称
     */
    @TableField("nickname")
    @ApiModelProperty(value = "昵称")
    private String nickname;

    /**
     * 真实姓名
     */
    @ApiModelProperty(value = "真实姓名")
    private String realName;

    /**
     * 性别
     */
    @TableField(value = "sex")
    @ApiModelProperty(value = "性别")
    private SexEnum sex;

    /**
     * 版本
     */
    @TableField(value = "version",update = "%s+1")
    @ApiModelProperty(value = "版本")
    private Integer version;

    /**
     * 时间字段，自动添加
     */
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    @ApiModelProperty(value = "时间字段，自动添加")
    private LocalDateTime createTime;

    @TableLogic
    @ApiModelProperty(value = "逻辑删除")
    private Integer isDelete;

}
