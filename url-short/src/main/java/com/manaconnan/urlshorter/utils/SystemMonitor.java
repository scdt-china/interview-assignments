package com.manaconnan.urlshorter.utils;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

/**
 * @Author mazexiang
 * @CreateDate 2021/7/4
 * @Version 1.0
 */
public class SystemMonitor {
    private static MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();

    private static MemoryUsage memoryUsage = memoryMXBean.getHeapMemoryUsage();

    private static long maxMemorySize = memoryUsage.getMax();

    /**
     * get max available memory size (byte)
     * @return
     */
    public static long getMaxMemorySize(){

        return  maxMemorySize;
    }

}
