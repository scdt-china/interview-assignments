package com.zdkj.handler.log.factory;

import cn.hutool.log.Log;
import com.zdkj.handler.annotion.ShortUrlLog;
import com.zdkj.handler.context.RequestNoContext;
import org.aspectj.lang.JoinPoint;

import java.util.Map;
import java.util.TimerTask;

/**
 * @author bihuiwen
 * @version 1.0
 * @description: TODO
 * @date 2021/7/4 下午11:02
 */
public class LogTaskFactory {
    private static final Log log = Log.get();

    /**
     * 操作日志
     */
    public static TimerTask operationLog(Map sysOpLog, ShortUrlLog shortUrlLog, JoinPoint joinPoint, String result) {
        return new TimerTask() {
            @Override
            public void run() {
                try {
                    LogFactory.createSysOperationLog(sysOpLog,  shortUrlLog, joinPoint, result);
                    //在这可以存储到持久化设备
                    log.info("日志打印：",sysOpLog);
                } catch (Exception e) {
                    log.error(">>> 创建操作日志异常，请求号为：{}，具体信息为：{}", RequestNoContext.get(), e.getMessage());
                }
            }
        };
    }

    /**
     * 异常日志
     */
    public static TimerTask exceptionLog(Map sysOpLog, ShortUrlLog shortUrlLog, JoinPoint joinPoint, Exception exception) {
        return new TimerTask() {
            @Override
            public void run() {
                try {
                    LogFactory.createSysExceptionLog(sysOpLog, shortUrlLog, joinPoint, exception);
                    //在这可以存储到持久化设备
                    log.info("日志打印：",sysOpLog);
                } catch (Exception e) {
                    log.error(">>> 创建异常日志异常，请求号为：{}，具体信息为：{}", RequestNoContext.get(), e.getMessage());
                }
            }
        };
    }
}
