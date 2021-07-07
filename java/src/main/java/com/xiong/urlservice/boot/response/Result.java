package com.xiong.urlservice.boot.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: create by xiong
 * @version: v1.0
 * @description:
 * @date:2021/6/22 1:58 下午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    private static final long serialVersionUID = 1L;

    private static Integer success = 200;
    private static Integer fail = 500;

    private Integer code;
    private String message;
    private T data;

    public static Result success() {
        Result responseObj = new Result();
        responseObj.code = success;
        return responseObj;
    }

    public static <T> Result success(T data) {
        Result responseObj = new Result();
        responseObj.code = success;
        responseObj.data = data;
        return responseObj;
    }

    public static <T> Result fail(String message) {
        Result responseObj = new Result();
        responseObj.code = fail;
        responseObj.message = message;
        return responseObj;
    }
}
