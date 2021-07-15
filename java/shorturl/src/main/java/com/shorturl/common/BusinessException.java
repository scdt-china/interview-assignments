
package com.shorturl.common;


/**
 * 异常类
 *
 * @author penghang
 * @created 7/8/21
 */
public class BusinessException extends RuntimeException {

    private Integer code;
    private String message;

    public BusinessException() {
    }

    public BusinessException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public BusinessException(CommonErrorCodeEnum commonErrorCodeEnum) {
        this.code = commonErrorCodeEnum.getCode();
        this.message = commonErrorCodeEnum.getDesc();
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}