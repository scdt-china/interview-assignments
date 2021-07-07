package com.zdkj.modler.shorturl.enums;

import com.zdkj.handler.error.emun.AbstractBaseExceptionEnum;

/**
 * @author bihuiwen
 * @version 1.0
 * @description: TODO
 * @date 2021/7/5 下午10:13
 */
public enum ShortUrlExceptionEnum implements AbstractBaseExceptionEnum {

    PARAM1_ERROR(300, "链接格式错误。"),
    /**
     * 长域名格式错误
     */
    PARAM_ERROR(601, "链接格式错误。"),
    /**
     * 系统内部异常
     */
    INTERNAL_ERROR(602, "内部错误。"),
    /**
     * id已超过限制
     */
    ID_LIMIT(603,"系统繁忙，请联系管理员。"),
    /**
     * 短域名不存在
     */
    NOT_EXIST(703, "短域名不存在或已失效。"),
    /**
     * 内存已达阈值，暂停解析
     */
    PAUSE_RESOLVE(666, "系统调整中，暂停解析。");

    private final Integer code;

    private final String message;

    ShortUrlExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
