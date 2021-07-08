package com.example.shorturl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author shenhc
 */
@Configuration
@EnableSwagger2
public class Swagger2 {
    @Value("${swaggerHost}")
    private String host;
    @Value("${swaggerShow}")
    private boolean show;
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .host(host)
                .enable(show)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.shorturl.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("短域名服务")
                .description("提供短域名服务")
                .termsOfServiceUrl("http://www.hopson.com.cn/")
                .contact(new Contact("shenhc", "", "xxxxx@qq.com"))
                .version("1.0")
                .build();
    }
}
