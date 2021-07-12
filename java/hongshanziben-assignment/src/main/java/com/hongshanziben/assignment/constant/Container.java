package com.hongshanziben.assignment.constant;

import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;
import com.googlecode.concurrentlinkedhashmap.Weighers;

/**
 * @author SJYUAN:KINGSJ.YUAN@FOXMAIL.COM
 * @date 2021-07-08.
 */
public class Container {

    public static final Long CAPACITY = 100000L;

    public static ConcurrentLinkedHashMap<String, String> container = new ConcurrentLinkedHashMap.Builder<String, String>()
            .maximumWeightedCapacity(CAPACITY).weigher(Weighers.singleton()).build();

    public static ConcurrentLinkedHashMap<String, String> checkContainer = new ConcurrentLinkedHashMap.Builder<String, String>()
            .maximumWeightedCapacity(CAPACITY).weigher(Weighers.singleton()).build();
}
