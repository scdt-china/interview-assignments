package com.homework.tinyurl.model.exception;

import com.homework.tinyurl.enums.TinyUrlExceptionCodeEnum;

/**
 * @Deacription
 * @Author zhangjun
 * @Date 2021/7/18 11:42 上午
 **/
public class TinyUrlException extends ExtensionException {


    public TinyUrlException(TinyUrlExceptionCodeEnum tinyUrlException) {
        super(tinyUrlException.getCode(), tinyUrlException.getMessage());
    }
}
