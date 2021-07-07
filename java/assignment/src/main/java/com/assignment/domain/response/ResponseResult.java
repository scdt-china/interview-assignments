package com.assignment.domain.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: zhangruiqi03
 * @Date: 2021/6/29 12:31 AM
 */
@Data
public class ResponseResult implements Serializable {
    private static final long serialVersionUID = 1L;


    public static  int error = -1;
    public static int success = 0;

    int code = success;
    Object msg;
    public ResponseResult() {
    }
    public ResponseResult(Object msg) {
        this.msg = msg;
    }

    public ResponseResult(int code, Object msg) {
        this.code = code;
        this.msg = msg;
    }
}
