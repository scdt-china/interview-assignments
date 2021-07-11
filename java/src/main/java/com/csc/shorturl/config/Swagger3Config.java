package com.csc.shorturl.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class Swagger3Config {


    @Bean
    public Docket createRestApi(Environment environment) {
        // 配置 default/dev/test 环境可用，其他环境不可用
        Profiles profile = Profiles.of("default", "dev", "test");
        boolean flag = environment.acceptsProfiles(profile);

        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .enable(flag)                             // 是否启用
                .groupName("weilichao")                       // 组别，区分接口提供者，默认default
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.csc.shorturl.controller"))   // 根据包名扫描
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("短链接接口")                      // 设置文档的标题
                .description("短链接接口，长链接")             // 设置文档的描述
                .version("V1.0")                           // 设置文档的版本信息
                .termsOfServiceUrl("http://www.baidu.com") // 设置文档的License信息->1.3 License information
                .build();
    }

}
