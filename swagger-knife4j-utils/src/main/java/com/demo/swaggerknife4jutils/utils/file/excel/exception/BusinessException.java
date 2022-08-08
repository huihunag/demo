package com.demo.swaggerknife4jutils.utils.file.excel.exception;

/**
 * @description: 业务异常
 * @author: huangxinwei
 * @date: 2021/1/22 11:23
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    protected final String message;

    public BusinessException(String message) {
        this.message = message;
    }

    public BusinessException(String message, Throwable e) {
        super(message, e);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

