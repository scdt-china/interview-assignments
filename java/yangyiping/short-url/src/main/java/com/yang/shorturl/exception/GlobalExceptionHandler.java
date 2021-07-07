package com.yang.shorturl.exception;

import com.yang.shorturl.dto.ResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 全局异常处理
 *
 * @author yangyiping1
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResultDTO<String> exceptionHandler(Exception e) {
        ResultDTO<String> resultDTO = new ResultDTO<>();
        resultDTO.setCode(500);
        resultDTO.setMessage(e.getMessage());
        log.error("controller异常", e);
        return resultDTO;
    }
}
