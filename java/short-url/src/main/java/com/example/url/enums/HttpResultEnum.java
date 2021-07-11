package com.example.url.enums;

import lombok.Getter;

/**
 * @author ancx
 */
@Getter
public enum HttpResultEnum {

    /**
     * 通用请求结果
     */
    OK(200, null),
    PARAM_ERROR(400, "参数校验错误！"),
    NO_TOKEN(401, "您还没有登录，请前往登录页面登录后操作！"),
    INVALID_TOKEN(401, "当前登录已失效，请前往登录页面重新登录！"),
    NO_AUTH(403, "您没有权限进行此请求！"),
    /**
     * 短网址请求结果[100xx]
     */
    MEM_NERVOUS(10001, "内存紧张，停止编码服务！"),
    SHORT_URL_NOT_EXISTS(10002, "此网址不存在！"),
    LONG_URL_ERROR(10003, "长地址格式不正确！"),
    ;

    private final int code;

    private final String msg;

    HttpResultEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
