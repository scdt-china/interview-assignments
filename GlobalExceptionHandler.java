package com.zdkj.handler.error;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import com.zdkj.handler.context.RequestNoContext;
import com.zdkj.handler.error.shorturl.ShortUrlExp;
import com.zdkj.pojo.response.ErrorResponseData;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * @author bihuiwen
 * @version 1.0
 * @description: TODO
 * @date 2021/7/4 下午9:00
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Log log = Log.get();

    @ExceptionHandler(ShortUrlExp.class)
    @ResponseBody
    public ErrorResponseData shortUrlException(ShortUrlExp e) {
        log.error(">>> 短链接服务异常，请求号为：{}，具体信息为：{}", RequestNoContext.get(), e.getErrorMessage());
        return renderJson(e.getCode(), e.getErrorMessage(), e);
    }

    @ExceptionHandler(org.springframework.validation.BindException.class)
    @ResponseBody
    public ErrorResponseData paramError(BindException e) {
        String argNotValidMessage = getArgNotValidMessage(e.getBindingResult());
        log.error(">>> 参数校验错误异常，请求号为：{}，具体信息为：{}", RequestNoContext.get(), argNotValidMessage);
        return renderJson(300, argNotValidMessage,null);
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponseData serverError(Throwable e) {
        log.error(">>> 服务器运行异常，请求号为：{}", RequestNoContext.get(),e.getMessage());
        e.printStackTrace();
        return renderJson(500, e.getMessage(), e);
    }

    /**
     * 渲染异常json
     * 根据异常枚举和Throwable异常响应，异常信息响应堆栈第一行
     */
    private ErrorResponseData renderJson(Integer code, String message, Throwable e) {
        if (ObjectUtil.isNotNull(e)) {

            //获取所有堆栈信息
            StackTraceElement[] stackTraceElements = e.getStackTrace();

            //默认的异常类全路径为第一条异常堆栈信息的
            String exceptionClassTotalName = stackTraceElements[0].toString();

            //遍历所有堆栈信息，找到系统代码的第一条异常信息
            for (StackTraceElement stackTraceElement : stackTraceElements) {
                if (stackTraceElement.toString().contains("com.zdkj")) {
                    exceptionClassTotalName = stackTraceElement.toString();
                    break;
                }
            }
            return new ErrorResponseData(code, message);
        } else {
            return new ErrorResponseData(code, message);
        }
    }

    /**
     * 获取请求参数不正确的提示信息
     * <p>
     * 多个信息，拼接成用逗号分隔的形式
     */
    private String getArgNotValidMessage(BindingResult bindingResult) {
        if (bindingResult == null) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();

        //多个错误用逗号分隔
        List<ObjectError> allErrorInfos = bindingResult.getAllErrors();
        for (ObjectError error : allErrorInfos) {
            stringBuilder.append(",").append(error.getDefaultMessage());
        }

        //最终把首部的逗号去掉
        return StrUtil.removePrefix(stringBuilder.toString(), ",");
    }
}
