package com.creolophus.liuyi.common.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;

/**
 * 朝辞白帝彩云间 千行代码一日还 两岸领导啼不住 地铁已到回龙观
 *
 * @author magicnana
 * @date 2019/9/18 下午2:05
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Api {

    String SCOPE_INTER = "inter";

    String HEADER_INTER_KEY = "X-LiuYi-Inter";
    String HEADER_INTER_VAL = "BMW525LIBENZGLE4504MATICPANAMERA";

    @AliasFor("value") String scope() default SCOPE_INTER;

    @AliasFor("scope") String value() default SCOPE_INTER;


}