package com.assignment.domain.kv;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * // 为了防止内存溢出，需要设定size大小，假如10W
 * @Author: zhangruiqi03
 * @Date: 2021/6/28 10:44 PM
 */
public class LRUMap <K,V> extends LinkedHashMap<K,V> {
    private static final long serialVersionUID = 1L;
    private volatile static LRUMap singleton;
    private final Lock lock = new ReentrantLock();
    //定义缓存的容量
    private int capacity;

    public LRUMap(int capacity){
        //调用LinkedHashMap的构造器，传入以下参数
        super(16,0.75f,true);
        this.capacity=capacity;
    }

    //如果map里面的元素个数大于了缓存最大容量，则删除链表的顶端元素
    @Override
    public boolean removeEldestEntry(Map.Entry<K, V> eldest){
        return size()>capacity;
    }

    @Override
    public V get(Object key) {
        lock.lock();
        try {
            return super.get(key);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public V put(K key, V value) {
        lock.lock();
        try {
            return super.put(key, value);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public V remove(Object key) {
        lock.lock();
        try {
            return super.remove(key);
        } finally {
            lock.unlock();
        }
    }

}
