package com.homework.tinyurl.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.util.List;

/**
 * @Deacription
 * @Author zhangjun
 * @Date 2021/7/20 2:04 下午
 **/
@Slf4j
public class JvmUtils {

    public static void printCurrentSystemInfo() {
        MemoryMXBean memorymbean = ManagementFactory.getMemoryMXBean();
        log.info("堆内存信息: " + memorymbean.getHeapMemoryUsage());

        List<String> inputArgs = ManagementFactory.getRuntimeMXBean().getInputArguments();
        log.info("\n#####################运行时设置的JVM参数#######################");
        log.info(inputArgs.toString());

        log.info("\n#####################运行时内存情况#######################");
        long totle = Runtime.getRuntime().totalMemory();
        log.info("总的内存量 [" + totle + "]");
        long free = Runtime.getRuntime().freeMemory();
        log.info("空闲的内存量 [" + free + "]");
        long max = Runtime.getRuntime().maxMemory();
        log.info("最大的内存量 [" + max + "]");
    }
}
