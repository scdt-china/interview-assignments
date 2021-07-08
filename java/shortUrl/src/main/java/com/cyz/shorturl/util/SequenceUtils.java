package com.cyz.shorturl.util;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @ClassName SequenceUtils
 * @Description 自增序列生成工具
 * @Author CYZ
 * @Date 2021/07/04 0017 上午 13:11
 */
public class SequenceUtils {

    private static AtomicLong num = new AtomicLong(0L);

    public static long getSequence() {
        return num.getAndIncrement();
    }

}
