package com.zdkj.util;

/**
 * @author bihuiwen
 * @version 1.0
 * @description: 自增序列生成，可以设置起始数值
 * @date 2021/7/5 下午8:05
 */
public class IdUtil {

    //设置偏移量，方便调整参数起始量
    private static long offset =14776336;
    //最大数值，超过此值转换62进制超过8位
    public static long MAX =218340105584895L;

    /**
     * 同步方法生成有序ID
     * @return 生成的结果ID
     */
    public static synchronized long nextId() {
        offset++;
        long next = offset;
        return  next;
    }
}
