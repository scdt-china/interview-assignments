package com.creolophus.liuyi.common.api;

import com.creolophus.liuyi.common.json.JSON;
import org.springframework.http.HttpStatus;

/**
 * @author magicnana
 * @date 2019/5/15 下午2:17
 */
public class ApiError {

    public static final ApiError S_OK = new ApiError(200, "OK");
    public static final ApiError E_INTERNAL_ERROR = new ApiError(
        HttpStatus.INTERNAL_SERVER_ERROR.value(), "暂时无法提供服务");
    public static final ApiError E_ERROR = new ApiError(HttpStatus.BAD_REQUEST.value(), "服务器内部错误");
    public static final ApiError E_ERROR_BUILD_ERROR = new ApiError(HttpStatus.BAD_REQUEST.value(),
        "无法处理此业务");


    private int code;
    private String message;

    public ApiError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static void main(String[] args) {
        ApiError error = new ApiError(100, "hahasdfs  %s");
        error.format("hello 你好");

        System.out.println(JSON.toJSONString(error));
    }

    public ApiError format(String msg) {
        return new ApiError(this.getCode(), String.format(this.message, msg));
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
