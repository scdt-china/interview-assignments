package com.cyz.shorturl.config;

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
 * @ClassName SwaggerConfig
 * @Description //SwaggerConfig配置类
 * @Author CYZ
 * @Date 2021/07/04 0017 上午 11:11
 **/
@Configuration
@EnableSwagger2
public class SwaggerConfig {


    @Bean
    public Docket creatRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(this.apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("短域名服务(技术架构Spring Boot+Swagger)")
                .description("实现短域名服务，两个API接口:\n" +
                        "短域名存储接口：接受长域名信息，返回短域名信息\n" +
                        "短域名读取接口：接受短域名信息，返回长域名信息。")
                .version("1.0.1")
                .build();
    }

}

