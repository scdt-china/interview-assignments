package com.homework.tinyurl.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @Deacription
 * @Author zhangjun
 * @Date 2021/7/18 9:48 下午
 **/
@Component
public class ServerConfig implements ApplicationRunner {

    @Autowired
    private ApplicationContext applicationContext;
    /**
     * 服务路径
     */
    public static String SHORT_DOMAIN_PATH = "http://localhost:";
    /**
     * 路径前缀
     */
    private static final String SHORT_PATH = "/s/";

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Environment environment = applicationContext.getBean(Environment.class);
        SHORT_DOMAIN_PATH += environment.getProperty("server.port") + SHORT_PATH;
    }
}
