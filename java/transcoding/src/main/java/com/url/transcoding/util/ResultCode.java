package com.url.transcoding.util;


import org.omg.CORBA.DynAnyPackage.Invalid;

/**
 * 枚举了一些常用API操作码
 */
public enum ResultCode implements IErrorCode {

    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    VALIDATE_FAILED(404, "参数检验失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    AUTHORIZATION_HEADER_IS_EMPTY(600,"请求头中的token为空"),
    GET_TOKEN_KEY_ERROR(601,"远程获取TokenKey异常"),
    GEN_PUBLIC_KEY_ERROR(602,"生成公钥异常"),
    JWT_TOKEN_EXPIRE(603,"token校验异常"),
    TOMANY_REQUEST_ERROR(429,"后端服务触发流控"),
    BACKGROUD_DEGRADE_ERROR(604,"后端服务触发降级"),
    BAD_GATEWAY(502,"网关服务异常"),
    FORBIDDEN(403, "没有相关权限"),
    INVALID_URL(408, "链接不存在或者已过期");

    private long code;
    private String message;

    private ResultCode(long code, String message) {
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
