package com.wanghui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author wanghui
 * @title  短域名服务启动类
 * @Date 2021-07-17 15:36
 * @Description
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.wanghui"})
public class ShortUrlServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShortUrlServiceApplication.class, args);
	}

}
