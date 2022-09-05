package com.demo.swaggerknife4jutils.bean.mask;

import com.demo.swaggerknife4jutils.utils.mask.DataMasking;
import com.demo.swaggerknife4jutils.utils.mask.DataMaskingFunc;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "数据掩码测试类", subTypes = {User.class},discriminator = "type")
public class User{
    /**
     * 主键ID
     */
    @ApiModelProperty(value = "主键ID")
    private Long id;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    @DataMasking(maskFunc = DataMaskingFunc.ALL_MASK)
    private String name;

    /**
     * 年龄
     */
    @ApiModelProperty(value = "年龄")
    private Integer age;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    @DataMasking(maskFunc = DataMaskingFunc.ALL_MASK)
    private String email;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                '}';
    }
}
