package com.creolophus.liuyi.common.api;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ApiInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(ApiInterceptor.class);
    @Resource
    protected ApiContextValidator apiContextValidator;
    @Autowired
    private List<ApiAnnoHandler> apiAnnoHandlers;

    protected void afterCompletion(HttpServletRequest request) {

    }

    protected void authenticate(HttpServletRequest request, Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
            Api api = hm.getMethodAnnotation(Api.class);
            if (api == null) {
                return;
            }

            for (ApiAnnoHandler apiAnnoHandler : apiAnnoHandlers) {
                if (apiAnnoHandler.allow(api)) {
                    apiAnnoHandler.handle(request, api);
                }
            }
        } else {
            throw new RuntimeException("不是HandlerMethod类型,无法继续运行");
        }

    }

    public String getPathPatterns() {
        return "/liuyi/**";
    }

    protected void preHandle(HttpServletRequest request) {
        apiContextValidator.initContext(request);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) {

        // 此处配置的是允许任意域名跨域请求，可根据需求指定
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods",
            "GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS");
        response.setHeader("Access-Control-Max-Age", "86400");
        response.setHeader("Access-Control-Allow-Headers", "*");

        // 如果是OPTIONS则结束请求
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            response.setStatus(HttpStatus.OK.value());
            return false;
        }

        preHandle(request);
        authenticate(request, handler);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
        Object handler, Exception ex) {
        afterCompletion(request);
    }
}

