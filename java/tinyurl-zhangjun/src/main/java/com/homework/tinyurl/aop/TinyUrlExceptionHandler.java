package com.homework.tinyurl.aop;

import com.homework.tinyurl.model.common.Result;
import com.homework.tinyurl.model.exception.TinyUrlException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 全局异常
 * @author
 */
@ControllerAdvice
@Order(value = -1000)
@Slf4j
public class TinyUrlExceptionHandler {


    /**
     * 业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler(TinyUrlException.class)
    @ResponseBody
    public Result handleException(TinyUrlException ex) {
        log.error("操作异常", ex);
        Result result = new Result();
        result.setMsg(ex.getMessage());
        result.setCode(ex.getCode());
        return result;
    }

}
