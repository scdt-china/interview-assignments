package com.manaconnan.urlshorter.model;

/**
 * @Author mazexiang
 * @CreateDate 2021/7/3
 * @Version 1.0
 */
public enum ResponseCode {
    /**
     * 成功
     */
    SUCCESS(200),
    /**
     * 失败
     */
    FAIL(400),
    /**
     * 未认证（签名错误）
     */
    UNAUTHORIZED(401),
    /**
     * 接口不存在
     */
    NOT_FOUND(404),
    /**
     * 服务器内部错误
     */
    INTERNAL_SERVER_ERROR(500);

    private final int code;
    ResponseCode(int code){
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}

