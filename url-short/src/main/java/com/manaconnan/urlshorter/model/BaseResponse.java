package com.manaconnan.urlshorter.model;


import com.alibaba.fastjson.JSON;

/**
 * @Author mazexiang
 * @CreateDate 2021/7/3
 * @Version 1.0
 */
public class BaseResponse<T> {
    /**
     * 状态码
     */
    private int code;
    /**
     * 返回信息
     */
    private String message;
    /**
     * 返回数据
     */
    private T data;

    public BaseResponse<T> setCode(ResponseCode code){
        this.code = code.getCode();
        return this;
    }

    public BaseResponse<T> setMessage(String msg){
        this.message = msg;
        return this;
    }
    public BaseResponse<T> setData(T data){
        this.data = data;
        return this;
    }
    public T getData() {
        return data;
    }

    public String toJsonStr() {
        return JSON.toJSONString(this);
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }
}
