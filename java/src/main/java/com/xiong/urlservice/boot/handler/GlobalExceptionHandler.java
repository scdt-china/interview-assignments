package com.xiong.urlservice.boot.handler;

import com.xiong.urlservice.boot.exception.BusinessException;
import com.xiong.urlservice.boot.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: create by xiong
 * @version: v1.0
 * @description: 全局异常处理
 * @date:2021/6/24 5:26 下午
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result defaultErrorHandler(HttpServletRequest request, Exception ex){
        log.error("defaultErrorHandler on [{}] failed. exMsg:",  request.getRequestURL(), ex);
        return Result.fail("服务开小差啦~");
    }

    /**
     * 业务异常处理
     *
     * @param request
     * @param ex
     * @return
     * @throws Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public Result businessExceptionExceptionHandler(HttpServletRequest request, BusinessException ex) {
        log.error("businessExceptionExceptionHandler on [{}] failed. exMsg:",  request.getRequestURL(), ex);
        return Result.fail(ex.getMessage());
    }

}
