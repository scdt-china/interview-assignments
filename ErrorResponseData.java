package com.zdkj.pojo.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author bihuiwen
 * @version 1.0
 * @description: TODO
 * @date 2021/7/4 下午11:34
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ErrorResponseData extends ResponseData {

    /**
     * 异常的具体类名称
     */
  //  private String exceptionClazz;

    ErrorResponseData(String message) {
        super(false, DEFAULT_ERROR_CODE, message, null);
    }

    public ErrorResponseData(Integer code, String message) {
        super(false, code, message, null);
    }

    ErrorResponseData(Integer code, String message, Object object) {
        super(false, code, message, object);
    }
}
