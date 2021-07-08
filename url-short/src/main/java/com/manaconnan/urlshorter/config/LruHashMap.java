package com.manaconnan.urlshorter.config;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author mazexiang
 * @CreateDate 2021/7/3
 * @Version 1.0
 */
final class LruHashMap<K, V> extends LinkedHashMap<K, V> {

    private final int capacity;

    public LruHashMap(int capacity) {
        super(capacity, 0.75f, true);
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry entry) {
        return size() > capacity;
    }

}
