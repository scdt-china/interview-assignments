package com.homework.tinyurl.enums;

/**
 * @Deacription
 * @Author zhangjun
 * @Date 2021/7/18 11:44 上午
 **/
public enum TinyUrlExceptionCodeEnum {

    INVALID_URL(10000000L,"不合法的URL,必须以http或https开头"),
    NOT_FOUND(10000001L,"未找到有效的长地址信息");


    /**
     * code码
     */
    private final Long code;

    /**
     * 异常信息
     */
    private final String message;

    TinyUrlExceptionCodeEnum(Long code, String message) {
        this.code = code;
        this.message = message;
    }

    public Long getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
