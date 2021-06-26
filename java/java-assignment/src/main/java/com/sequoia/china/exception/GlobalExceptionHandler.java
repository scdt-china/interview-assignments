package com.sequoia.china.exception;

import com.sequoia.china.common.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @Description 全局异常处理类
 * @Author helichao
 * @Date 2021/6/25 10:09 下午
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private final static Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(value = SequoiaRunTimeException.class)
    @ResponseBody
    public ResponseData<?> bizExceptionHandler(SequoiaRunTimeException e) {
        String code = e.getCode();
        String msg = e.getMsg();
        Object data = e.getData();
        Exception e1 = e.getE();
        log.error("业务处理发生异常：{}", msg, e1);
        return ResponseData.error(code, msg, data);
    }

    /**
     * 处理其他异常
     * 未被捕获的异常
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseData<?> exceptionHandler(Exception e) {
        log.error("系统发生异常：", e);
        return ResponseData.error(ErrorEnum.SCE_0000.getCode(), ErrorEnum.SCE_0000.getMsg());
    }
}
