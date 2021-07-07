package com.zdkj.pojo.response;

/**
 * @author bihuiwen
 * @version 1.0
 * @description: TODO
 * @date 2021/7/4 下午11:35
 */
public class SuccessResponseData extends ResponseData{

    public SuccessResponseData() {
        super(true, DEFAULT_SUCCESS_CODE, DEFAULT_SUCCESS_MESSAGE, null);
    }

    public SuccessResponseData(Object object) {
        super(true, DEFAULT_SUCCESS_CODE, DEFAULT_SUCCESS_MESSAGE, object);
    }

    public SuccessResponseData(Integer code, String message, Object object) {
        super(true, code, message, object);
    }
}
