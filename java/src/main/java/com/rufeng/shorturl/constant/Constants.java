package com.rufeng.shorturl.constant;

/**
 * @author qujingguo
 * @version 1.0.0
 * @date 2021/5/27 12:11 下午
 * @description 常量信息
 */
public class Constants {
    /**
     * 资源映射路径 前缀
     */
    public static final String SHORT_URL_PREFIX = "http://rf.cn/";
    /**
     * 期望插入的元素总个数
     */
    public static final long EXPECTED_INSERTIONS = 1000000;
    /**
     * 假阳性概率
     */
    public static final double FPP = 0.0001;
    public static final String LONG_TO_SHORT_KEY = "longToShort";

    public static final String SHORT_TO_LONG_KEY = "shortToLang";
}
