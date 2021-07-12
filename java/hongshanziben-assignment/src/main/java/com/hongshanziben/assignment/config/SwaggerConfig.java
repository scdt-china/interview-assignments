package com.hongshanziben.assignment.config;

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
 * @author SJYUAN:KINGSJ.YUAN@FOXMAIL.COM
 * @date 2021-07-08.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createPcRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("assignment")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.hongshanziben.assignment"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("作业接口文档")
                .description("JAVA作业接口文档")
                .termsOfServiceUrl("http://localhost:6060/assignment")
                .contact(new Contact("SJYUAN", "", "KINGSJ.YUAN@FOXMAIL.COM"))
                .version("1.0.0")
                .build();
    }

}
