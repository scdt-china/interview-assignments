package org.dengzhiheng.shorturl.Result;

/**
 * 响应状态码
 * @author : When6passBye
 * @date : 2021/7/20 下午6:13
 */
public enum ResultCode {

    /**
     * 成功
     */
    SUCCESS(200),
    /**
     * 失败
     */
    FAIL(400),
    /**
     * 接口不存在
     */
    NOT_FOUND(404),
    /**
     * 服务器内部错误
     */
    INTERNAL_SERVER_ERROR(500);

    private final int code;

    ResultCode(int code) {
        this.code = code;
    }

    public int code() {
        return code;
    }
}