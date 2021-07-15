
package com.shorturl.web.advice;


import com.shorturl.common.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentConversionNotSupportedException;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {

    @ExceptionHandler(BindException.class)
    public Object validExceptionHandler(BindException e){
        List<ObjectError> ErrorList = e.getBindingResult().getAllErrors();
        assert ErrorList != null;
        return bindingResult(ErrorList);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object validExceptionHandler(MethodArgumentNotValidException e){
        List<ObjectError> ErrorList = e.getBindingResult().getAllErrors();
        assert ErrorList != null;
        return bindingResult(ErrorList);
    }

    private Object bindingResult(List<ObjectError> ErrorList) {
        String msg = CommonErrorCodeEnum.PARAM_ERROR.getDesc();;
            StringBuilder errorSB = new StringBuilder();
            for (ObjectError fieldError : ErrorList) {
                errorSB.append("[").append(fieldError.getDefaultMessage())
                        .append("]");
            msg = errorSB.toString();
        }
        return MyResponse.buildFailure(CommonErrorCodeEnum.PARAM_ERROR.getCode(), msg);
    }

    @ExceptionHandler(BusinessException.class)
    public Object validExceptionHandler(BusinessException e){
        Integer code = e.getCode();
        assert code != null;
        return MyResponse.buildFailure(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentConversionNotSupportedException.class)
    public Object validExceptionHandler(MethodArgumentConversionNotSupportedException e){
        String code = e.getErrorCode();
        assert code != null;
        return MyResponse.buildFailure(CommonErrorCodeEnum.SYSTEM_ERROR.getCode(), e.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Object validExceptionHandler(HttpRequestMethodNotSupportedException e){
        String msg = e.getMessage();
        assert msg != null;
        return MyResponse.buildFailure(CommonErrorCodeEnum.SYSTEM_ERROR.getCode(), msg);
    }

}