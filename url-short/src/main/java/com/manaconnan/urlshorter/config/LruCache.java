package com.manaconnan.urlshorter.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Author mazexiang
 * @CreateDate 2021/7/3
 * @Version 1.0
 */
public class LruCache<K, V> {

    private static final int DEFAULT_CAPACITY = 1000;

    private final Map<K, V> map;

    private final int maxMemorySize;

    private int memorySize;

    public LruCache() {
        this(DEFAULT_CAPACITY);
    }

    public LruCache(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity <= 0");
        }
        this.map = new LruHashMap<>(capacity);
        maxMemorySize = capacity ;
    }

    public final V get(K key) {
        Objects.requireNonNull(key, "key == null");
        synchronized (this) {
            V value = map.get(key);
            if (value != null) {
                return value;
            }
        }
        return null;
    }

    public final V put(K key, V value) {
        Objects.requireNonNull(key, "key == null");
        Objects.requireNonNull(value, "value == null");
        V previous;
        synchronized (this) {
            previous = map.put(key, value);
            memorySize ++;
            if (previous != null) {
                memorySize --;
            }
            trimToSize(maxMemorySize);
        }
        return previous;
    }

    public final V remove(K key) {
        Objects.requireNonNull(key, "key == null");
        V previous;
        synchronized (this) {
            previous = map.remove(key);
            if (previous != null) {
                memorySize --;
            }
        }
        return previous;
    }

    public synchronized final void clear() {
        map.clear();
    }

    public synchronized final int getMaxMemorySize() {
        return maxMemorySize;
    }

    public synchronized final int getMemorySize() {
        return memorySize;
    }

    private void trimToSize(int maxSize) {
        while (true) {
            if (memorySize <= maxSize || map.isEmpty()) {
                break;
            }
            Map.Entry<K, V> toRemove = map.entrySet().iterator().next();
            map.remove(toRemove.getKey());
            memorySize --;
        }
    }

    public static void main(String[] args) {
        Map<String,String> ma =new HashMap<>();
        ma.put("a","a");
        ma.put("b","b");
        ma.put("c","c");
        Map.Entry<String, String> next = ma.entrySet().iterator().next();
        System.out.println(next.getKey());
    }



}
