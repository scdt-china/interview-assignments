package com.xiong.urlservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@Slf4j
@SpringBootApplication
public class UrlserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrlserviceApplication.class, args);
		log.info("=========================================短连接服务启动===========================");
	}

}
