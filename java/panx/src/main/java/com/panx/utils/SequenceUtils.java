package com.panx.utils;

/**
 * @author PanX
 * @date 2021-07-01
 * id生成器
 */
public class SequenceUtils {

    private final static long START_STMP = 1480166465631L;//起始时间戳

    private final static long SEQUENCE_BIT = 10; //序列号占用的位数

    private final static long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);//序列号最大值

    private long sequence = 0L; //序列号
    private long lastStmp = -1L;//上一次时间戳

    public SequenceUtils() {}

    /**
     * 产生下一个ID
     */
    public synchronized long nextId() {
        long currStmp = getNewStmp();
        if (currStmp < lastStmp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }

        if (currStmp == lastStmp) {
            //相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            //同一毫秒的序列数已经达到最大4096
            if (sequence == 0L) {
                currStmp = getNextMill();
            }
        } else {
            //不同毫秒内，序列号置为0
            sequence = 0L;
        }

        lastStmp = currStmp;
        return (currStmp - START_STMP) << SEQUENCE_BIT | sequence;
    }

    private long getNextMill() {
        long mill = getNewStmp();
        while (mill <= lastStmp) {
            mill = getNewStmp();
        }
        return mill;
    }

    private long getNewStmp() {
        return System.currentTimeMillis();
    }
}
