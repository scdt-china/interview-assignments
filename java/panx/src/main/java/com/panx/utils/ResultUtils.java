package com.panx.utils;

public class ResultUtils<T>{

    private T datas;
    private Integer resp_code;
    private String resp_msg;

    public static <T> ResultUtils<T> succeed(T datas, String msg) {
        return succeed(datas, ResultCodeEnum.SUCCESS.getCode(), msg);
    }

    public static <T> ResultUtils<T> succeed(T datas, Integer code, String msg) {
        return new ResultUtils(datas, code, msg);
    }

    public static <T> ResultUtils<T> failed(T datas, String msg) {
        return succeed(datas, ResultCodeEnum.FAILED.getCode(), msg);
    }

    public static <T> ResultUtils<T> failed(T datas, Integer code, String msg) {
        return new ResultUtils(datas, code, msg);
    }

    public ResultUtils(T datas, Integer resp_code, String resp_msg) {
        this.datas = datas;
        this.resp_code = resp_code;
        this.resp_msg = resp_msg;
    }

    public T getDatas() {
        return datas;
    }

    public void setDatas(T datas) {
        this.datas = datas;
    }

    public Integer getResp_code() {
        return resp_code;
    }

    public void setResp_code(Integer resp_code) {
        this.resp_code = resp_code;
    }

    public String getResp_msg() {
        return resp_msg;
    }

    public void setResp_msg(String resp_msg) {
        this.resp_msg = resp_msg;
    }
}
