package com.sequoiacapital.assignment.common.conts;

import lombok.Getter;
import lombok.ToString;

/**
 * 通用返回结果枚举
 * @author xin.wu
 * @date 2021/6/24
 */

@ToString
@Getter
public enum ResultStatus {
    /**
     * 成功
     */
    SUCCESS(200, "success"),

    /**
     * 请求错误
     */
    BAD_REQUEST(400, "Bad Request"),

    /**
     * 服务器错误
     */
    INTERNAL_SERVER_ERROR(500, "Server Error"),

    /**
     * 业务错误-手机号已经注册
     */
    REG_ERROR(4001, "reg error"),

    /**
     * 业务错误-手机号已经注册
     */
    AUTH_ERROR(4002, "auth error"),

    /**
     * 业务错误-验证码错误
     */
    CODE_ERROR(4003, "code error");

    /**
     * 业务异常码
     */
    private Integer code;
    /**
     * 业务异常信息描述
     */
    private String msg;


    ResultStatus(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
