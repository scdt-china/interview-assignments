package org.dengzhiheng.shorturl.Result;


/**
 * 响应结果包装类
 * @author : When6passBye
 * @date : 2021/7/20 下午6:14
 */
public class ResultGenerator {

    /**
     * 默认响应结果
     */
    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

    public static <T> Result<T> genSuccessResult(T data) {
        return new Result<T>()
                .setCode(ResultCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE)
                .setData(data);
    }

    public static Result<String> genDefaultFailResult(String message) {
        return new Result<String>()
                .setCode(ResultCode.FAIL)
                .setMessage(message);
    }

    public static Result<String> gentServerFailResult(String message) {
        return new Result<String>()
                .setCode(ResultCode.INTERNAL_SERVER_ERROR)
                .setMessage(message);
    }
}
