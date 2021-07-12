package com.he.shorturl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.he.shorturl"})
public class ShorturlApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShorturlApplication.class, args);
    }
}
