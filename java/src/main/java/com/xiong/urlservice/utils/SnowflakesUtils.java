package com.xiong.urlservice.utils;

/**
 * @author: create by xiong
 * @version: v1.0
 * @description:
 * @date:2021/6/23 1:50 下午
 */
public class SnowflakesUtils {

    //开始时间截 (2015-01-01)
    private static final long twepoch = 1489111610226L;
    //机器ID所占位置
    private static final long workerIdBits = 5L;
    //数据标识所占位数
    private static final long datacenterIdBits = 5L;
    //支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
    private static final long maxWorkerId = -1L ^ (-1L << workerIdBits);
    //支持的最大数据标识id，结果是31
    private static final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
    //序列在id中占的位数
    private static final long sequenceBits = 12L;
    //机器ID向左移12位
    private static final long workerIdShift = sequenceBits;
    //数据标识id向左移17位(12+5)
    private static final long datacenterIdShift = sequenceBits + workerIdBits;
    //时间截向左移22位(5+5+12)
    private static final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    //生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095)
    private static final long sequenceMask = -1L ^ (-1L << sequenceBits);

    //工作机器ID(0~31)
    private static long workerId = 1L;

    //数据中心ID(0~31)
    private static long datacenterId = 1L;

    //毫秒内序列(0~4095)
    private static long sequence = 0L;

    //上次生成ID的时间截
    private static long lastTimestamp = -1L;

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            long nextId = SnowflakesUtils.nextId();
            System.out.println(nextId);
        }
    }

    /**
     * 获得下一个ID (该方法是线程安全的)
     * @return
     */
    public static synchronized long nextId() {
        long timestamp = timeGen();

        //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format(
                    "Clock moved backwards.  Refusing to generate id for %d milliseconds",
                    lastTimestamp - timestamp));
        }

        //如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            //毫秒内序列溢出
            if (sequence == 0) {
                //阻塞到下一个毫秒,获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {//时间戳改变，毫秒内序列重置
            sequence = 0L;
        }

        //上次生成ID的时间截
        lastTimestamp = timestamp;

        //移位并通过或运算拼到一起组成64位的ID
        return ((timestamp - twepoch) << timestampLeftShift)
                | (datacenterId << datacenterIdShift)
                | (workerId << workerIdShift)
                | sequence;
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     * @param lastTimestamp
     * @return
     */
    protected static long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 返回以毫秒为单位的当前时间
     * @return
     */
    protected static long timeGen() {
        return System.currentTimeMillis();
    }

}
