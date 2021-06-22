package com.rufeng.shorturl.exception;


/**
 * @author qujingguo
 * @version 1.0.0
 * @date 2021/5/27 12:00 下午
 * @description 自定义异常
 */

public class CustomException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String code;

    private final String message;

    public CustomException(String message) {
        this.message = message;
    }

    public CustomException(String message, String code) {
        this.message = message;
        this.code = code;
    }

    public CustomException(String message, Throwable e) {
        super(message, e);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }
}
