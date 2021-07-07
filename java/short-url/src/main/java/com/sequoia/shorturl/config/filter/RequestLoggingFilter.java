package com.sequoia.shorturl.config.filter;

import org.springframework.web.filter.CommonsRequestLoggingFilter;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

/**
 * 请求日志过滤
 *
 * @Author xj
 *
 * @Date 2021/06/27
 *
 * @version v1.0.0
 *
 */
@WebFilter(urlPatterns = "/*", initParams = { @WebInitParam(name = "includeQueryString", value = "true"),
        @WebInitParam(name = "includeClientInfo", value = "true"),
        @WebInitParam(name = "includeHeaders", value = "true"), @WebInitParam(name = "includePayload", value = "true"),
        @WebInitParam(name = "maxPayloadLength", value = "1024") })
public class RequestLoggingFilter extends CommonsRequestLoggingFilter {

    @Override
    protected void initFilterBean() throws ServletException {
        super.initFilterBean();
        FilterConfig filterConfig = getFilterConfig();
        if (filterConfig != null) {
            this.setIncludeQueryString(Boolean.valueOf(filterConfig.getInitParameter("includeQueryString")));
            this.setIncludeClientInfo(Boolean.valueOf(filterConfig.getInitParameter("includeClientInfo")));
            this.setIncludeHeaders(Boolean.valueOf(filterConfig.getInitParameter("includeHeaders")));
            this.setIncludePayload(Boolean.valueOf(filterConfig.getInitParameter("includePayload")));
            String maxPayloadLength = filterConfig.getInitParameter("maxPayloadLength");
            if (maxPayloadLength != null) {
                this.setMaxPayloadLength(Integer.valueOf(filterConfig.getInitParameter("maxPayloadLength")));
            }
        }
    }
}
