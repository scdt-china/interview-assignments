package com.example.demo.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Swagger配置类
 */
@Configuration
@EnableOpenApi
public class SwaggerConfig {
	@Bean
	public Docket docket() {
		return new Docket(DocumentationType.OAS_30).apiInfo(apiInfo()).enable(true).select()
				.apis(RequestHandlerSelectors.basePackage("com.example"))
				.paths(PathSelectors.any()).build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("短链接项目接口文档").description("短域名服务")
				.contact(new Contact("张智", "", "9873199@qq.com")).version("1.0").build();
	}
}
