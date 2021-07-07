package com.zdkj.handler.error.shorturl;

import com.zdkj.handler.error.emun.AbstractBaseExceptionEnum;
import lombok.Getter;

/**
 * @author bihuiwen
 * @version 1.0
 * @description: TODO
 * @date 2021/7/4 下午10:58
 */
@Getter
public class ShortUrlExp extends RuntimeException{

    private final Integer code;

    private final String errorMessage;

    public ShortUrlExp(AbstractBaseExceptionEnum exception) {
        super(exception.getMessage());
        this.code = exception.getCode();
        this.errorMessage = exception.getMessage();
    }
}
