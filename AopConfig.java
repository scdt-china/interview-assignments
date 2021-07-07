package com.zdkj.config;

import com.zdkj.handler.aop.LogAop;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author bihuiwen
 * @version 1.0
 * @description: AOP配置
 * @date 2021/7/4 下午11:40
 */
@Configuration
public class AopConfig {

    @Bean
    public LogAop logAop() {
        return new LogAop();
    }


}
