package com.sequoia.shorturl.filter;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/***
 *
 * 短域名过滤，前缀不匹配，直接过滤，不调用短域名查询接口
 *
 * @Author xj
 *
 * @Date 2021/06/27
 *
 * @version v1.0.0
 *
 */
@Slf4j
public class ShortUrlFilter extends OncePerRequestFilter {

    @Value("${shorturl.prefix}")
    private String shortUrlPrefix;//短域名前缀

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String url = uri.substring(contextPath.length());
        //使用包装器
        XssFilterWrapper xssFilterWrapper=new XssFilterWrapper(request);

        if (!url.startsWith(shortUrlPrefix)) {
            filterChain.doFilter(xssFilterWrapper, response);
            return;
        }

    }
}
