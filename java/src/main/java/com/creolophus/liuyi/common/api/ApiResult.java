package com.creolophus.liuyi.common.api;

import com.creolophus.liuyi.common.base.AbstractObject;
import java.util.Collections;

/**
 * @author magicnana
 * @date 2019/5/15 下午2:16
 */
public final class ApiResult<T> implements AbstractObject {

    private int code;
    private String message;
    private T data;

    public ApiResult() {
        this.code = ApiError.S_OK.getCode();
        this.data = (T) Collections.emptyMap();
    }

    public ApiResult(T data) {
        this.code = ApiError.S_OK.getCode();
        this.data = data == null ? (T) Collections.emptyMap() : data;
    }

    public ApiResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "{\"code\":" + code + ",\"message\":\"" + message + "\", \"data\":" + (data == null
            ? ""
            : data.toString()) + '}';
    }
}
