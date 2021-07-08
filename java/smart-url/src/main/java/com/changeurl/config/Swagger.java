package com.changeurl.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger配置类
 */
@Configurable
@EnableSwagger2
public class Swagger {

    /**
     * 创建API应用
     * appinfo()增加API相关信息
     * @return
     */
    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                        .apiInfo(apiInfo())
                        .select()
                        .apis(RequestHandlerSelectors.basePackage("com.dc.iop"))
                        .paths(PathSelectors.any()).build();
    }

    @Bean
    public Docket restApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .paths(PathSelectors.any())
                .build();
    }
    /**
     * 创建API的基本信息(基本信息会展示在文档页面中)
     * 访问地址：http://项目地址/swagger-ui.html
     * @return
     */
    public ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("Restful APIs")
                .description("")
                .termsOfServiceUrl("")
                .version("1.0")
                .build();
    }
}
