package com.xiong.urlservice.boot.annotation;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author: create by xiong
 * @version: v1.0
 * @description:
 * @date:2021/6/24 6:01 下午
 */
@Component
public class UrlValidImpl implements ConstraintValidator<UrlValid,String> {

    private String urlStart = "http";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (Strings.isBlank(value)) {
            return false;
        }
        if (!value.startsWith(urlStart)) {
            //禁用默认提示信息
            context.disableDefaultConstraintViolation();
            //设置提示语
            context.buildConstraintViolationWithTemplate("链接格式不正确").addConstraintViolation();
            return false;
        }
        return true;
    }
}
