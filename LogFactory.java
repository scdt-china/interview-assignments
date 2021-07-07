package com.zdkj.handler.log.factory;

import cn.hutool.core.date.DateTime;
import com.zdkj.handler.annotion.ShortUrlLog;
import org.aspectj.lang.JoinPoint;

import java.util.Arrays;
import java.util.Map;

/**
 * @author bihuiwen
 * @version 1.0
 * @description: TODO
 * @date 2021/7/4 下午11:02
 */
public class LogFactory {

    /**
     * 创建操作日志
     */
    static void createSysOperationLog(Map sysOpLog, ShortUrlLog shortUrlLog, JoinPoint joinPoint, String result) {
        fillCommonSysOpLog(sysOpLog, shortUrlLog, joinPoint);
        sysOpLog.put("code","Y");
        sysOpLog.put("result",result);
    }

    /**
     * 创建异常日志
     */
    static void createSysExceptionLog(Map sysOpLog, ShortUrlLog shortUrlLog, JoinPoint joinPoint, Exception exception) {
        fillCommonSysOpLog(sysOpLog, shortUrlLog, joinPoint);
        sysOpLog.put("code","N");
        sysOpLog.put("msg", Arrays.toString(exception.getStackTrace()));
    }

    /**
     * 生成通用操作日志字段
     */
    private static void fillCommonSysOpLog(Map sysOpLog, ShortUrlLog businessLog, JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        sysOpLog.put("title", businessLog.title());
        sysOpLog.put("className", className);
        sysOpLog.put("methodName", methodName);
        sysOpLog.put("time", DateTime.now());

    }
}
