package com.rufeng.shorturl.exception;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.rufeng.shorturl.enums.ErrorCode;
import com.rufeng.shorturl.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;
import java.lang.reflect.UndeclaredThrowableException;

/**
 * @author qujingguo
 * @version 1.0.0
 * @date 2021/5/27 12:00 下午
 * @description 全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 业务异常
     */
    @ExceptionHandler(CustomException.class)
    public Result businessException(CustomException e) {
        return Result.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public Result handlerNotFoundException(Exception e) {
        return Result.error(ErrorCode.HANDLER_NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        if (e instanceof java.lang.reflect.UndeclaredThrowableException && ((UndeclaredThrowableException) e).getUndeclaredThrowable() instanceof BlockException) {
            return Result.error(ErrorCode.LIMIT);
        }

        return Result.error(ErrorCode.EXCEPTION.getCode(), e.getMessage());
    }


    @ExceptionHandler(BindException.class)
    public Result validatedBindException(BindException e) {
        String message = e.getAllErrors().get(0).getDefaultMessage();
        return Result.error(ErrorCode.VALIDATED_ERROR.getCode(), message);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public Result constraintViolationException(ConstraintViolationException e) {
        String message = e.getConstraintViolations().iterator().next().getMessage();
        return Result.error(ErrorCode.VALIDATED_ERROR.getCode(), message);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object validExceptionHandler(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return Result.error(ErrorCode.VALIDATED_ERROR.getCode(), message);
    }

    /*********************sentinel相关异常 */
    @ExceptionHandler(BlockException.class)
    public Object blockExceptionHandler(BlockException e) {
        log.error("blockException：" + e.getMessage(), e);
        return Result.error(ErrorCode.LIMIT);
    }

    @ExceptionHandler(FlowException.class)
    public Object flowExceptionHandler(FlowException e) {
        log.error("FlowException：" + e.getMessage(), e);
        return Result.error(ErrorCode.LIMIT);
    }

    @ExceptionHandler(DegradeException.class)
    public Object degradeExceptionHandler(DegradeException e) {
        log.error("DegradeException：" + e.getMessage(), e);
        return Result.error(ErrorCode.LIMIT);
    }


    @ExceptionHandler(SystemBlockException.class)
    public Object systemBlockExceptionHandler(SystemBlockException e) {
        log.error("SystemBlockException：" + e.getMessage(), e);
        return Result.error(ErrorCode.LIMIT);
    }


}

