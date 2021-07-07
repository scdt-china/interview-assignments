package com.zdkj.handler.log;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.zdkj.handler.annotion.ShortUrlLog;
import com.zdkj.handler.error.ServiceException;
import com.zdkj.handler.log.factory.LogTaskFactory;
import com.zdkj.util.HttpServletUtil;
import org.aspectj.lang.JoinPoint;
import org.springframework.scheduling.concurrent.ScheduledExecutorFactoryBean;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author bihuiwen
 * @version 1.0
 * @description: 日志管理类
 * @date 2021/7/4 下午11:02
 */
public class LogManager {
    /**
     * 异步操作记录日志的线程池
     */
    private static final ScheduledThreadPoolExecutor EXECUTOR = new ScheduledThreadPoolExecutor(10, new ScheduledExecutorFactoryBean());

    private LogManager() {
    }

    private static final LogManager LOG_MANAGER = new LogManager();

    public static LogManager myLog() {
        return LOG_MANAGER;
    }


    private void executeLog(TimerTask task) {
        //日志记录操作延时
        int operateDelayTime = 10;
        EXECUTOR.schedule(task, operateDelayTime, TimeUnit.MILLISECONDS);
    }

    /**
     * 操作日志
     */
    public void executeOperationLog(ShortUrlLog shortUrlLog, JoinPoint joinPoint, final String result) {
        Map sysOpLog = this.genBaseSysOpLog();
        TimerTask timerTask = LogTaskFactory.operationLog(sysOpLog,  shortUrlLog, joinPoint, result);
        executeLog(timerTask);
    }

    /**
     * 异常日志
     */
    public void executeExceptionLog(ShortUrlLog shortUrlLog, JoinPoint joinPoint, Exception exception) {
        Map  sysOpLog = this.genBaseSysOpLog();
        TimerTask timerTask = LogTaskFactory.exceptionLog(sysOpLog, shortUrlLog, joinPoint, exception);
        executeLog(timerTask);
    }

    private Map<String,Object> genBaseSysOpLog() {
        HttpServletRequest request = HttpServletUtil.getRequest();
        if (ObjectUtil.isNotNull(request)) {
            String ip = ServletUtil.getClientIP(request);
            String url = request.getRequestURI();
            String method = request.getMethod();
            Map baseSysOpLog = new HashMap();
            baseSysOpLog.put("ip",ip);
            baseSysOpLog.put("url",url);
            baseSysOpLog.put("method",method);
            return baseSysOpLog;
        } else {
            throw new ServiceException(500,"请求数据缺失。");
        }

    }
}
