package com.demo.swaggerknife4jutils.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @description 响应结果集
 * @Date 2022/6/20 21:33
 * @Author HUANGXINWEI
 */
@ApiModel(value = "响应结果集", subTypes = {ApiResult.class},discriminator = "type")
public class ApiResult<T> {

    @ApiModelProperty(value = "响应代码")
    private long code;
    @ApiModelProperty(value = "响应消息")
    private String message;
    @ApiModelProperty(value = "响应数据")
    private T data;

    public ApiResult() {
    }

    protected ApiResult(long code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> ApiResult<T> success(T data) {
        return new ApiResult<T>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), data);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     * @param  message 提示信息
     */
    public static <T> ApiResult<T> success(T data, String message) {
        return new ApiResult<T>(ResultEnum.SUCCESS.getCode(), message, data);
    }

    /**
     * 失败返回结果
     * @param resultEnum 错误码
     */
    public static <T> ApiResult<T> failed(ResultEnum resultEnum) {
        return new ApiResult<T>(resultEnum.getCode(), resultEnum.getMessage(), null);
    }

    /**
     * 失败返回结果
     * @param message 提示信息
     */
    public static <T> ApiResult<T> failed(String message) {
        return new ApiResult<T>(ResultEnum.FAILED.getCode(), message, null);
    }

    /**
     * 失败返回结果
     */
    public static <T> ApiResult<T> failed() {
        return failed(ResultEnum.FAILED);
    }

    /**
     * 参数验证失败返回结果
     */
    public static <T> ApiResult<T> validateFailed() {
        return failed(ResultEnum.VALIDATE_FAILED);
    }

    /**
     * 参数验证失败返回结果
     * @param message 提示信息
     */
    public static <T> ApiResult<T> validateFailed(String message) {
        return new ApiResult<T>(ResultEnum.VALIDATE_FAILED.getCode(), message, null);
    }

    /**
     * 未登录返回结果
     */
    public static <T> ApiResult<T> unauthorized(T data) {
        return new ApiResult<T>(ResultEnum.UNAUTHORIZED.getCode(), ResultEnum.UNAUTHORIZED.getMessage(), data);
    }

    /**
     * 未授权返回结果
     */
    public static <T> ApiResult<T> forbidden(T data) {
        return new ApiResult<T>(ResultEnum.FORBIDDEN.getCode(), ResultEnum.FORBIDDEN.getMessage(), data);
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
