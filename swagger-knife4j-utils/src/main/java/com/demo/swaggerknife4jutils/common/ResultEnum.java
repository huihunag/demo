package com.demo.swaggerknife4jutils.common;

/**
 * @description 响应说明
 * @Date 2022/6/20 21:28
 * @Author HUANGXINWEI
 */
public enum ResultEnum {

    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    VALIDATE_FAILED(404, "参数检验失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "没有相关权限"),
    LOCK_IP(407, "IP被锁定,请稍后再试");

    private long code;
    private String message;

    private ResultEnum(long code, String message) {
        this.code = code;
        this.message = message;
    }

    public long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
