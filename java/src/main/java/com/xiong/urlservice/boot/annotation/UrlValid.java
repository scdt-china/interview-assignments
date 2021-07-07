package com.xiong.urlservice.boot.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author: create by xiong
 * @version: v1.0
 * @description:
 * @date:2021/6/24 5:57 下午
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
//指定注解的实现类
@Constraint(validatedBy = UrlValidImpl.class)
public @interface UrlValid {

    String message() default "链接不能为空";


    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
