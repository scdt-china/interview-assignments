package com.sequoia.shorturl.common;

/**
 *
 * 错误信息枚举
 *
 * @Author xiaojun
 *
 * @Date 2021/6/27
 *
 * @version v1.0.0
 *
 */
public enum AppErrors {

    ERROR_MSG_NO_AUTH("1001", "没有权限"),
    ERROR_MSG_USERNAME_PWD_IS_WRONG("1002", "用户名或者密码错误");

    private String errorCode;
    private String message;

    private AppErrors(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public String errorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String message() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static String message(String errorCode) {
        String message = null;
        for (AppErrors err : AppErrors.values()) {
            if (err.errorCode().equals(errorCode)) {
                message = err.message();
                break;
            }
        }
        return message;
    }
}
