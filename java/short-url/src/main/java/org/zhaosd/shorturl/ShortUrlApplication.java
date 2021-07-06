package org.zhaosd.shorturl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

@EnableOpenApi
@SpringBootApplication
public class ShortUrlApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShortUrlApplication.class, args);
	}

}
