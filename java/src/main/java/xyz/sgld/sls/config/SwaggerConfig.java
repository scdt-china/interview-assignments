package xyz.sgld.sls.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import xyz.sgld.sls.ShortLinkRes;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableOpenApi
public class SwaggerConfig {
    @Bean
    public Docket docket() {
        List<Response> responseMessageList = new ArrayList<>();

        responseMessageList.add(new ResponseBuilder().code(String.valueOf(ShortLinkRes.RES_CODE_OK))
                .description(ShortLinkRes.RES_DES_OK).build());
        responseMessageList.add(new ResponseBuilder().code(String.valueOf(ShortLinkRes.RES_CODE_UNKNOWN_ERROR))
                .description(ShortLinkRes.RES_DES_UNKNOWN_ERROR).build());
        responseMessageList.add(new ResponseBuilder().code(String.valueOf(ShortLinkRes.RES_CODE_ARGUMENT_ERROR))
                .description(ShortLinkRes.RES_DES_ARGUMENT_ERROR).build());
        responseMessageList.add(new ResponseBuilder().code(String.valueOf(ShortLinkRes.RES_CODE_TIMEOUT_ERROR))
                .description(ShortLinkRes.RES_DES_TIMEOUT_ERROR).build());
        responseMessageList.add(new ResponseBuilder().code(String.valueOf(ShortLinkRes.RES_CODE_MISSION_PARAMS))
                .description(ShortLinkRes.RES_DES_MISSION_PARAMS).build());
        return new Docket(DocumentationType.OAS_30)
                .globalResponses(HttpMethod.GET, responseMessageList)
                .globalResponses(HttpMethod.POST, responseMessageList)
                .apiInfo(apiInfo()).enable(true)
                .select()
                //apis： 添加swagger接口提取范围
                .apis(RequestHandlerSelectors.basePackage("xyz.sgld"))
                //.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("短链接项目接口文档")
                .description("短链接项目，按照要求提供短链接API")
                .contact(new Contact("白高静", "", "baigaojing@gmail.com"))
                .version("1.0")
                .build();
    }
}
