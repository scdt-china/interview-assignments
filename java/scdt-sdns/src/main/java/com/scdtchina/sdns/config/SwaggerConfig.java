package com.scdtchina.sdns.config;


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

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket getUserDocket() {
        Contact contact = new Contact("许恩泽", "", "xuenze19910521@126.com");

        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("短网址服务")
                .description("生成短网址，并实现根据短网址查询原网址")
                .version("0.0.1-SNAPSHOT")
                .contact(contact)
                .build();

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.scdtchina.sdns.rest"))
                .paths(PathSelectors.any())
                .build();
    }
}