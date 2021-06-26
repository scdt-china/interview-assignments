package com.sequoia.china.exception;

import cn.hutool.core.util.StrUtil;

/**
 * @Description 异常枚举
 * @Author helichao
 * @Date 2021/6/25 10:10 下午
 */
public enum ErrorEnum implements ErrorEnumService{

    /**
     * 系统异常，请联系管理员
     */
    SCE_0000("SCE-0000", "系统异常，请联系管理员"),

    /**
     * 请维护异常枚举类
     */
    SCE_0001("SCE-0001", "请维护异常枚举类"),

    /**
     * 参数为空（正常应在controller层校验）
     */
    SCE_0002("SCE-0002", "参数为空"),

    /**
     * 非法Url
     */
    SCE_0003("SCE-0003", "非法Url"),

    /**
     * 无匹配的长域名
     */
    SCE_0004("SCE-0004", "无匹配的长域名"),

    /**
     * 超过最大递归次数，请稍后重试
     */
    SCE_0005("SCE-0005", "超过最大递归次数，请稍后重试"),

    ;

    /**
     * 异常编码
     */
    private String code;

    /**
     * 异常提示信息
     */
    private String msg;

    ErrorEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 根据编码获取异常枚举
     * @param code
     * @return
     */
    public static ErrorEnum getEnumByCode(String code){
        if(StrUtil.isEmpty(code)){
            return null;
        }
        for (ErrorEnum eicEnum:ErrorEnum.values()) {
            if(code.equals(eicEnum.getCode())){
                return eicEnum;
            }
        }
        return null;
    }

    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
