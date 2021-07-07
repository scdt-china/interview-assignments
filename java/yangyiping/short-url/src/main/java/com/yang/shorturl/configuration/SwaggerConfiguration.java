package com.yang.shorturl.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 *
 * @author yangyiping1
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    @Bean
    public Docket docker(){
        // 构造函数传入初始化规范，这是swagger2规范
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("yangyiping")
                .enable(true)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.yang.shorturl.controller"))
                .build();
    }

    private ApiInfo apiInfo(){
        Contact contact = new Contact("yangyiping", "http://www.baidu.com/", "yangyiping02@163.com");
        return new ApiInfo(
                "短地址服务",
                "短地址服务",
                "v1.0",
                "http://www.baidu.com/",
                contact,
                "Apache 2.0 ",
                "许可链接：XXX",
                new ArrayList<>()
        );
    }
}
