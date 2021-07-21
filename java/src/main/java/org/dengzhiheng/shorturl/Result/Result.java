package org.dengzhiheng.shorturl.Result;

import com.alibaba.fastjson.JSON;

/**
 * 返回实体
 * @author : When6passBye
 * @date : 2021/7/20 下午6:13
 */
public class Result<T> {

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


    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Result<T> setCode(ResultCode resultCode) {
        this.code = resultCode.code();
        return this;
    }

    public Result<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }


    /**
     * 得到JSON格式字符串
     *
     * @return JSON格式字符串
     */
    public String toJsonStr() {
        return JSON.toJSONString(this);
    }

}
