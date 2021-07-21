package org.dengzhiheng.shorturl.utils;

/**
 * 内存监视器
 * @Author: When6passBye
 * @Date: 2021-07-13 15:45
 **/
public class RamMonitor {

    private static final Runtime RUNTIME = Runtime.getRuntime();

    private static final long TOTAL = RUNTIME.totalMemory();

    private static final long MAX = RUNTIME.maxMemory();

    private static double getFreeMemoryPercentage() {

        long free = RUNTIME.freeMemory();

        double v = (MAX - free) / (MAX + 0.0);
        return v;
    }

    public static boolean isThreshold(){
        double THRESHOLD = 0.99D;
        return getFreeMemoryPercentage() >= THRESHOLD;
    }
}
