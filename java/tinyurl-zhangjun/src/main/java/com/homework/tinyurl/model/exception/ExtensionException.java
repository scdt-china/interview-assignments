package com.homework.tinyurl.model.exception;

import com.homework.tinyurl.model.common.Result;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * @Deacription
 * @Author zhangjun
 * @Date 2021/7/18 11:40 上午
 **/
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExtensionException extends RuntimeException {

    /**
     * 业务异常码 ( 详情参加文档说明 )
     */
    private Long code;

    /**
     * 业务异常信息
     */
    private String message;


    /**
     * cause
     */
    private Throwable cause;

    /**
     * 业务域标识自动取当前服务
     *
     * @param code    code
     * @param message message
     */
    public ExtensionException(Long code, String message) {
        this.code = code;
        this.message = message;
    }

    @Deprecated
    public ExtensionException(Long code, String message, Object object, Throwable throwable) {
        this.code = code;
        this.message = message;
        this.cause = throwable;
    }

}
