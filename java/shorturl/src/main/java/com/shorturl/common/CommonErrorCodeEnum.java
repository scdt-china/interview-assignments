
package com.shorturl.common;


import java.util.HashMap;
import java.util.Map;

/**
 * 系统错误码
 */
public enum CommonErrorCodeEnum {

    UNKNOW_ERROR(13030001, "未知错误"),
    BUSINESS_ERROR(13030002, "业务调用异常"),
    PARAM_ERROR(13030003, "参数不合法"),
    SAVE_URL_ERROR(13030004, "保存url失败！"),
    SYSTEM_ERROR(13030005, "系统异常"),

    ;

    CommonErrorCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private final Integer code;
    private final String msg;
    private static Map<Integer, CommonErrorCodeEnum> codeMap = new HashMap<>();


    public Integer getCode() {
        return code;
    }


    public String getDesc() {
        return msg;
    }

    public static String getMsg(int code) {
        return codeMap.containsKey(code) ? codeMap.get(code).getDesc() : "";
    }

    public static boolean isValid(int code) {
        return codeMap.containsKey(code);
    }

    static {
        for (CommonErrorCodeEnum status : CommonErrorCodeEnum.values()) {
            codeMap.put(status.getCode(), status);
        }
    }
}