package com.sequoia.shorturl;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
/**
 *
 * 短域名服务启动类
 *
 * @Author xiaojun
 *
 * @Date 2021/6/27
 *
 * @version v1.0.0
 *
 */

@EnableSwagger2Doc
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SpringBootShortUrlApplication {

    public static void main(String[] args) {

        SpringApplication.run(SpringBootShortUrlApplication.class, args);

    }
}
