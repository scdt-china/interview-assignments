package com.zdkj.util;

/**
 * @author bihuiwen
 * @version 1.0
 * @description: 计算内存比例工具类
 * @date 2021/7/5 下午11:06
 */
public class MUtil {

    /**
     * 计算内存比例
     * @return
     */
    public static double remainingMemory(){
        long freeM = Runtime.getRuntime().freeMemory();
        long totalVmHeap = Runtime.getRuntime().totalMemory();
        return freeM/(totalVmHeap+0.0);
    }
}
