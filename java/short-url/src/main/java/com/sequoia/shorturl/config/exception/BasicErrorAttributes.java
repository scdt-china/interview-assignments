package com.sequoia.shorturl.config.exception;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;
/**
 *
 * 基础属性错误
 *
 * @Author xj
 *
 * @Date 2021/06/27
 *
 * @version v1.0.0
 *
 */
public class BasicErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, includeStackTrace);
        addCustomeError(errorAttributes, webRequest);
        return errorAttributes;
    }

    private void addCustomeError(Map<String, Object> errorAttributes, WebRequest webRequest) {
        errorAttributes.put("result", "error");
        Throwable error = getError(webRequest);
        if (error instanceof AppException) {
            AppException ae = (AppException) error;
            errorAttributes.put("errorCode", ae.getErrorCode());
        }
    }
}
