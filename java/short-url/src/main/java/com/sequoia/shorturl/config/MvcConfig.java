package com.sequoia.shorturl.config;

import com.sequoia.shorturl.config.exception.BasicErrorAttributes;
import com.sequoia.shorturl.config.validator.BeanValidator;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.validation.SmartValidator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Bean
    public ErrorAttributes errorAttributes() {
        return new BasicErrorAttributes();
    }


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addStatusController("", HttpStatus.OK);
    }


    @Bean
    public BeanValidator beanValidator(SmartValidator smartValidator) {
        return new BeanValidator(smartValidator);
    }

}
