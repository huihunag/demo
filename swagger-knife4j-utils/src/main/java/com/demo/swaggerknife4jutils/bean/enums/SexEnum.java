package com.demo.swaggerknife4jutils.bean.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.demo.swaggerknife4jutils.bean.mask.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @description： 性别枚举
 * @author：weirx
 * @date：2022/1/17 16:26
 * @version：3.0
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@ApiModel(value = "性别枚举", subTypes = {User.class},discriminator = "type")
public enum SexEnum implements IEnum<Integer> {
    MAN(1, "男"),
    WOMAN(2, "女");
    @ApiModelProperty(value = "性别代码")
    private Integer code;
    @ApiModelProperty(value = "性别展示")
    private String name;

    SexEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public Integer getValue() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static SexEnum bySexEnumtoCode(Integer code){
        SexEnum[] sexEnums = SexEnum.values();
        for (SexEnum sexEnum: sexEnums){
            if(sexEnum.code.equals(code)){
                return sexEnum;
            }
        }
        return null;
    }

}
