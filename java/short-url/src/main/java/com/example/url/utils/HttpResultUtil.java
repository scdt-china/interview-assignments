package com.example.url.utils;


import com.example.url.common.HttpResult;
import com.example.url.enums.HttpResultEnum;

/**
 * @author ancx
 */
public class HttpResultUtil {

    public static <T> HttpResult<T> success(T data) {
        HttpResult<T> httpResult = new HttpResult<>();
        httpResult.setCode(HttpResultEnum.OK.getCode());
        httpResult.setMessage(HttpResultEnum.OK.getMsg());
        httpResult.setData(data);
        return httpResult;
    }

    public static HttpResult<String> success() {
        return success(null);
    }

    public static HttpResult<String> error(HttpResultEnum httpResultEnum) {
        HttpResult<String> httpResult = new HttpResult<>();
        httpResult.setCode(httpResultEnum.getCode());
        httpResult.setMessage(httpResultEnum.getMsg());
        return httpResult;
    }

    public static <T> HttpResult<T> error(HttpResultEnum httpResultEnum, T data) {
        HttpResult<T> httpResult = new HttpResult<>();
        httpResult.setCode(httpResultEnum.getCode());
        httpResult.setMessage(httpResultEnum.getMsg());
        httpResult.setData(data);
        return httpResult;
    }

    public static <T> HttpResult<T> error(int code, String msg) {
        HttpResult<T> httpResult = new HttpResult<>();
        httpResult.setCode(code);
        httpResult.setMessage(msg);
        return httpResult;
    }

    public static <T> HttpResult<T> error(String msg) {
        return error(-1, msg);
    }
}
