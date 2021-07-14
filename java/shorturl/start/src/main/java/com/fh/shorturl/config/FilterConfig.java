package com.fh.shorturl.config;

import com.fh.shorturl.filter.ShortURLFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Autowired
    private ShortURLFilter shortURLFilter;

    @Bean
    public FilterRegistrationBean registerAuthFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(shortURLFilter);
        registration.addUrlPatterns("/*");
        registration.setName("shortURLFilter");
        registration.setOrder(0);  //值越小，Filter越靠前。
        return registration;
    }
    //追加filter方式：public FilterRegistrationBean registerOtherFilter(){...}
}
