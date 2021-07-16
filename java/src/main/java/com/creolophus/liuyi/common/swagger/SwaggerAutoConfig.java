package com.creolophus.liuyi.common.swagger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author magicnana
 * @date 2019/3/1 上午12:36
 */

@Configuration
@ConditionalOnClass(Docket.class)
@Profile({"dev", "test", "local"})
public class SwaggerAutoConfig {

  private static final Logger logger = LoggerFactory.getLogger(SwaggerAutoConfig.class);

  @Bean
  @ConditionalOnMissingBean
  public ApiInfo apiInfo() {
    logger.info("start Swagger ApiInfo");
    return new ApiInfoBuilder()
        .title("LiuYi API")// API 标题
        .description("我是谁？我在那里？")// API描述
        .contact(new Contact("magicnana", "xxx", "magicnana999@gmail.com"))// 联系人
        .version("1.0.0")// 版本号
        .build();
  }

  @Bean
  @ConditionalOnMissingBean
  public Docket docket(ApiInfo apiInfo) {
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo)
        .select()
        .apis(RequestHandlerSelectors
            .basePackage("com.creolophus"))// 扫描该包下的所有需要在Swagger中展示的API，@ApiIgnore注解标注的除外
        .paths(PathSelectors.any())
        .build();
  }

}
