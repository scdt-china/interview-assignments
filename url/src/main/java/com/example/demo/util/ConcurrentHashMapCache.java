package com.example.demo.util;


import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ConcurrentHashMapCache {

    //缓存使用记录
    private static LinkedList<Object> cacheUseRecord = new LinkedList<>();

    //可缓存最大数量
    private static Integer MAX_CACHE_SIZE = 1000;

    //重入读写锁
    private static ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    private static Lock writeLock = reentrantReadWriteLock.writeLock();
    private static Lock readLock = reentrantReadWriteLock.readLock();
    private final static Map<String, String> cacheEntryMap = new ConcurrentHashMap<>();

    public String get(Object key) {

        readLock.lock();
        String entry = null;
        try {
            entry = cacheEntryMap.get(key);
        } finally {
            readLock.unlock();
        }
        if (null == entry)
            return null;
        //更新使用记录
        touchUseRecord(key);
        return entry ;
    }

    //更新缓存使用记录
    public static void touchUseRecord(Object key) {
        writeLock.lock();
        try {
            //删除使用记录
            cacheUseRecord.remove(key);
            //新增使用记录到首位
            cacheUseRecord.add(0, key);
        } finally {
            writeLock.unlock();
        }
    }
    public Object put(String key, String value) throws Exception {

        //判断缓存大小是否够用，否则根据LRU删除最久未使用的元素
        if (cacheEntryMap.size() > MAX_CACHE_SIZE) {
            deleteLRU();
        }
        if (cacheEntryMap.size() > MAX_CACHE_SIZE) {
            throw new Exception("缓存大小超出限制");
        }
        writeLock.lock();
        try {
            cacheEntryMap.put(key, value);
            cacheUseRecord.add(0, key);
        } finally {
            writeLock.unlock();
        }
        return value;
    }
    /**
     * 删除最近最久未使用的缓存
     */
    public static void deleteLRU() {
        writeLock.lock();
        try {
            Object remove = cacheUseRecord.remove(cacheUseRecord.size() - 1);
            cacheEntryMap.remove(remove);
        } finally {
            writeLock.unlock();
        }
    }
}
