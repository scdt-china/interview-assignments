package com.zdkj.handler.error.emun;

/**
 * @author bihuiwen
 * @version 1.0
 * @description: TODO
 * @date 2021/7/4 下午8:58
 */
public enum ServerExceptionEnum implements AbstractBaseExceptionEnum {

    /**
     * 当前请求参数为空或数据缺失
     */
    REQUEST_EMPTY(300, "当前请求参数为空或数据缺失，请联系管理员"),

    /**
     * 服务器出现未知异常
     */
    SERVER_ERROR(500, "服务器出现异常，请联系管理员");

    private final Integer code;

    private final String message;

    ServerExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return  code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}

