package com.zdkj.handler.aop;

import com.alibaba.fastjson.JSON;
import com.zdkj.handler.annotion.ShortUrlLog;
import com.zdkj.handler.log.LogManager;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import java.lang.reflect.Method;

/**
 * @author bihuiwen
 * @version 1.0
 * @description: 业务日志url注解
 * @date 2021/7/4 下午 10:24
 */
@Aspect
public class LogAop {

    @Pointcut("@annotation(com.zdkj.handler.annotion.ShortUrlLog)")
    private void getLogPointCut() {
    }

    /**
     * 操作成功返回结果记录日志
     */
    @AfterReturning(pointcut = "getLogPointCut()", returning = "result")
    public void doAfterReturning(JoinPoint joinPoint, Object result) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        ShortUrlLog shortUrlLog = method.getAnnotation(ShortUrlLog.class);

        //异步记录日志
        LogManager.myLog().executeOperationLog(
                shortUrlLog, joinPoint, JSON.toJSONString(result));
    }

    /**
     * 操作发生异常记录日志
     */
    @AfterThrowing(pointcut = "getLogPointCut()", throwing = "exception")
    public void doAfterThrowing(JoinPoint joinPoint, Exception exception) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        ShortUrlLog shortUrlLog = method.getAnnotation(ShortUrlLog.class);

        //异步记录日志
        LogManager.myLog().executeExceptionLog(
                shortUrlLog, joinPoint, exception);
    }
}