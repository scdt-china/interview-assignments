package com.example.shorturl.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger2配置类
 * 访问地址: http://127.0.0.1:8080/swagger-ui/index.html
 * @author hcq
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {
	/**
	 * 当前版本
	 */
	@Value("${spring.application.version}")
	private String version;

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.example.shorturl.controller"))
				.paths(PathSelectors.any())
				.build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("短域名服务 RESTful APIs")
				.description("短域名服务")
				.termsOfServiceUrl("http://localhost:8080/")
				.version(version)
				.build();
	}
}
