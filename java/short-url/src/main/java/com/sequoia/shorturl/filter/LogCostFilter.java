package com.sequoia.shorturl.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 *
 * 过滤器来实现记录请求执行时间的功能
 *
 * @Author xj
 *
 * @Date 2021/06/27
 *
 * @version v1.0.0
 *
 */
@Slf4j
public class LogCostFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        long start = System.currentTimeMillis();
        filterChain.doFilter(servletRequest,servletResponse);
        System.out.println("Execute cost="+(System.currentTimeMillis()-start));
    }

    @Override
    public void destroy() {

    }
}
