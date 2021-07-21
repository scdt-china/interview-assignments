package org.dengzhiheng.shorturl.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * 配置摘要信息
 * @Author: When6passBye
 * @Date: 2021-07-19 09:49
 **/
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.dengzhiheng.shorturl.controller"))
                .build();
    }

    /**
     * 创建该API的基本信息
     * 访问地址：http://localhost:8081/swagger-ui.html
     * @return
     */
    private ApiInfo apiInfo() {
        Contact contact = new Contact(
                "邓智恒",
                "http://localhost:8081/",
                "540442568@qq.com");

        return new ApiInfoBuilder()
                .contact(contact)
                .title(" 短网址服务，RESTful APIs by Swagger2")
                .description("interview for 红杉中国")
                .version("1.0")
                .build();
    }
}
