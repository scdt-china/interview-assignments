package com.xiong.urlservice.boot.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: create by xiong
 * @version: v1.0
 * @description:
 * @date:2021/6/25 8:34 下午
 */
@Data
@NoArgsConstructor
public class BusinessException extends RuntimeException{
    private static final long serialVersionUID = 1L;



    public BusinessException(String message) {
        super(message);
    }
}
