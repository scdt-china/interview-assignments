package com.fh.shorturl.filter;

import cn.hutool.core.util.StrUtil;
import com.fh.shorturl.service.ShortURLService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
@Order(1)
public class ShortURLFilter extends OncePerRequestFilter {
    private static final String SHORT_URL_PREFIX = "/t/";


    private static final Logger log = LoggerFactory.getLogger(ShortURLFilter.class);


    @Autowired
    private ShortURLService shortURLService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String url = uri.substring(contextPath.length());

        if (!url.startsWith(SHORT_URL_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }
        String shorturl = url.substring(url.lastIndexOf("/") + 1);
        String longUrl = shortURLService.queryLongURL(shorturl);
        if (StrUtil.isBlank(longUrl)) {
            log.warn("对应url {},没有找到原链接", url);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.write("{\"flag\":\"error\",\"msg\":\"抱歉，原链接已过期销毁\",\"data\":null}");
            out.flush();
            return;
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.write("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Title</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<script>\n" +
                "    window.location.href = '" + longUrl + "'" +
                "</script>\n" +
                "\n" +
                "</body>\n" +
                "</html>");
        out.flush();
        return;
    }
}
