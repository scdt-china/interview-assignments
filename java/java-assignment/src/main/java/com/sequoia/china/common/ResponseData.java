package com.sequoia.china.common;

import com.sequoia.china.exception.ErrorEnumService;

/**
 * @Description 返回封装类
 * @Author helichao
 * @Date 2021/6/25 10:03 下午
 */
public class ResponseData<T> {

    /**
     * 返回状态
     */
    private boolean success = true;

    /**
     * 异常编码
     */
    private String errorCode;

    /**
     * 异常提示信息
     */
    private String errorMsg;

    /**
     * 返回的业务数据
     */
    private T data;

    public ResponseData(){}

    public ResponseData(boolean success, String errorCode, String errorMsg, T data) {
        this.success = success;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.data = data;
    }

    /**
     * 成功返回
     *
     * @param data 业务数据
     * @return
     */
    public static ResponseData success(Object data) {
        return new ResponseData(true, null, null, data);
    }

    /**
     * 失败返回
     *
     * @param errorCode 异常编码
     * @param errorMsg  异常提示信息
     * @return
     */
    public static ResponseData error(String errorCode, String errorMsg) {
        return new ResponseData(false, errorCode, errorMsg, null);
    }

    /**
     * 失败返回
     *
     * @param errorCode 异常编码
     * @param errorMsg  异常提示信息
     * @param data      失败时返回的业务数据
     * @return
     */
    public static ResponseData error(String errorCode, String errorMsg, Object data) {
        return new ResponseData(false, errorCode, errorMsg, data);
    }

    /**
     * 失败返回
     * @param service 异常枚举
     * @return
     */
    public static ResponseData error(ErrorEnumService service){
        return new ResponseData(false,service.getCode(), service.getMsg(), null);
    }

    /**
     * 失败返回
     * @param service 异常枚举
     * @param data 失败时返回的业务数据
     * @return
     */
    public static ResponseData error(ErrorEnumService service,Object data){
        return new ResponseData(false,service.getCode(), service.getMsg(), data);
    }



    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
