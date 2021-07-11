package com.csc.shorturl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

@SpringBootApplication
@EnableOpenApi
public class ShorturlApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShorturlApplication.class, args);
    }

}
