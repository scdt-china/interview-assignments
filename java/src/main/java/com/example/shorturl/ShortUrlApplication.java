package com.example.shorturl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * spring应用入口
 * @author hcq
 */
@EnableCaching
@SpringBootApplication
public class ShortUrlApplication {
	public static void main(String[] args) {
		SpringApplication.run(ShortUrlApplication.class, args);
	}
}
