package com.sequoia.shorturl.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import com.sequoia.shorturl.filter.LogCostFilter;
import com.sequoia.shorturl.filter.ShortUrlFilter;

/**
 *
 * springboot加载过滤器
 *
 * @Author xiaojun
 *
 * @Date 2021/6/27
 *
 * @version v1.0.0
 *
 */
public class FilterConfig {
    @Bean
    public FilterRegistrationBean registFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new LogCostFilter());
        registration.addUrlPatterns("/*");
        registration.setName("LogCostFilter");
        registration.setOrder(2);
        return registration;
    }

    @Bean
    public FilterRegistrationBean registFilter2() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new ShortUrlFilter());
        registration.addUrlPatterns("/*");
        registration.setName("ShortUrlFilter");
        registration.setOrder(1);
        return registration;
    }
}
