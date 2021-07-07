package com.xiong.urlservice.boot.aop;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Objects;

@Slf4j
@Aspect
@Component
public class ControllerAopLog {

    private static final String START_TIME = "request-start";

    /**
     * 切入点
     * com.ttjb.settle.order.bapi.controller
     *
     */
    @Pointcut("execution(public * com.xiong.urlservice.controller..*Controller.*(..))")
    public void cutePoint() {

    }

    /**
     * 前置操作
     *
     * @param point 切入点
     */
    @Before("cutePoint()")
    public void beforeLog(JoinPoint point) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null){
            HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();

            log.info("请求地址:{} ,请求body参数:{} ",request.getRequestURI(), JSON.toJSONString(Arrays.toString(point.getArgs())));

            Long start = System.currentTimeMillis();
            request.setAttribute(START_TIME, start);
        }
    }

    /**
     * 环绕操作
     *
     * @param point 切入点
     * @return 原方法返回值
     * @throws Throwable 异常信息
     */
    @Around("cutePoint()")
    public Object aroundLog(ProceedingJoinPoint point) throws Throwable {
        Object result = point.proceed();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null){
            HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
            Long start = (Long) request.getAttribute(START_TIME);
            Long end = System.currentTimeMillis();
            log.info("请求地址:{} ,请求body参数:{} 相应结果:{} ,耗时:{}",request.getRequestURI(), JSON.toJSONString(Arrays.toString(point.getArgs())),JSON.toJSONString(result),end-start);
        }
        return result;
    }
}
