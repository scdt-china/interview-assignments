package com.sequoia.china.exception;

import cn.hutool.core.util.StrUtil;

/**
 * @Description 自定义异常枚举类
 * @Author helichao
 * @Date 2021/6/25 10:07 下午
 */
public class SequoiaRunTimeException extends RuntimeException{

    /**
     * 异常枚举编码
     */
    private String code;

    /**
     * 异常提示信息
     */
    private String msg;

    /**
     * 具体异常信息
     */
    private Exception e;

    /**
     * 异常时需要返回的数据
     */
    private Object data;

    /**
     * 只抛出异常编码和异常提示信息
     *
     * @param code 异常编码
     * @param msg  提示信息
     */
    public SequoiaRunTimeException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 抛出异常编码和异常提示信息、返回数据
     *
     * @param code 异常编码
     * @param msg  提示信息
     */
    public SequoiaRunTimeException(String code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 只抛出异常编码和异常提示信息
     *
     * @param errorEnum 异常枚举
     */
    public SequoiaRunTimeException(ErrorEnumService errorEnum) {
        checkEnum(errorEnum);
        this.code = errorEnum.getCode();
        this.msg = errorEnum.getMsg();
    }

    /**
     * 抛出异常编码和异常提示信息、返回数据
     *
     * @param errorEnum 异常枚举
     * @data data 业务数据
     */
    public SequoiaRunTimeException(ErrorEnumService errorEnum, Object data) {
        checkEnum(errorEnum);
        this.code = errorEnum.getCode();
        this.msg = errorEnum.getMsg();
        this.data = data;
    }

    /**
     * 抛出异常编码和异常提示信息、打印异常栈信息
     *
     * @param errorEnum 异常枚举
     * @param e         异常
     */
    public SequoiaRunTimeException(ErrorEnumService errorEnum, Exception e) {
        checkEnum(errorEnum);
        this.code = errorEnum.getCode();
        this.msg = errorEnum.getMsg();
        this.e = e;
    }

    /**
     * 抛出异常编码和异常提示信息、返回数据、并打印异常栈信息、
     *
     * @param errorEnum
     * @param data
     * @param e
     */
    public SequoiaRunTimeException(ErrorEnumService errorEnum, Object data, Exception e) {
        checkEnum(errorEnum);
        this.code = errorEnum.getCode();
        this.msg = errorEnum.getMsg();
        this.data = data;
        this.e = e;
    }

    /**
     * 校验入参枚举是否合规
     *
     * @param errorEnum 异常枚举
     */
    private void checkEnum(ErrorEnumService errorEnum) {
        if (errorEnum == null || StrUtil.isEmpty(errorEnum.getCode()) || StrUtil.isEmpty(errorEnum.getMsg())){
            throw new SequoiaRunTimeException(ErrorEnum.SCE_0001);
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Exception getE() {
        return e;
    }

    public void setE(Exception e) {
        this.e = e;
    }
}
