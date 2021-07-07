package com.zdkj.handler.annotion;

import java.lang.annotation.*;

/**
 * @author bihuiwen
 * @version 1.0
 * @description: 业务日志url注解
 * @date 2021/7/4 下午 9:20
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ShortUrlLog {
    /**
     * 业务的名称,例如:"短链接生成"
     */
    String title() default "";
}
