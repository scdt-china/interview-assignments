package com.rufeng.shorturl.enums;

/**
 * @author qujingguo
 * @version 1.0.0
 * @date 2021/5/27 11:36 上午
 * @description 错误信息code码
 */
public enum ErrorCode {
    SUCCESS("0000", "成功"),
    INVALID_PARAM("1000", "参数异常"),
    VALIDATED_ERROR("1001", "校验异常"),
    HANDLER_NOT_FOUND("4000", "路径不存在，请检查路径是否正确"),
    LIMIT("5000", "限流中，请稍后再试"),
    NOT_FOUND("2000", "未找到域名"),
    EXCEPTION("9000", "未知异常"),
    ;
    private final String code;

    private final String desc;

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    ErrorCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
