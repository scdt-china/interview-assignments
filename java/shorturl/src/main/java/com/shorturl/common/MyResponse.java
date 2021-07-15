
package com.shorturl.common;

import io.swagger.annotations.*;

import java.io.Serializable;

/**
 * 在这里编写类的功能描述
 *
 * @author penghang
 * @created 7/8/21
 */
@ApiModel("通用返回")
public class MyResponse<T> implements Serializable {

    private static final long serialVersionUID = -2465140747749709626L;

    @ApiModelProperty("接口请求成功")
    private boolean success;
    @ApiModelProperty("错误码")
    private int code;
    @ApiModelProperty("错误信息")
    private String errMsg;
    @ApiModelProperty("返回对象")
    private T data;

    public static <T> MyResponse<T> buildSuccess(T date) {
        MyResponse<T> response = new MyResponse<T>();
        response.setData(date);
        response.setSuccess(true);
        return response;
    }

    public static <T> MyResponse<T> buildSuccess() {
        MyResponse<T> response = new MyResponse<T>();
        response.setSuccess(true);
        return response;
    }

    public static <T> MyResponse<T> buildFailure() {
        MyResponse<T> response = new MyResponse<T>();
        response.setSuccess(false);
        return response;
    }

    public static <T> MyResponse<T> buildFailure(String errorMsg) {
        MyResponse<T> response = new MyResponse<T>();
        response.setSuccess(false);
        response.setCode(0);
        response.setErrMsg(errorMsg);
        return response;
    }

    public static <T> MyResponse<T> buildFailure(int errorCode, String errorMsg) {
        MyResponse<T> response = new MyResponse<T>();
        response.setSuccess(false);
        response.setCode(errorCode);
        response.setErrMsg(errorMsg);
        return response;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "MyResponse{" +
                "success=" + success +
                ", code=" + code +
                ", errMsg='" + errMsg + '\'' +
                ", data=" + data +
                '}';
    }
}