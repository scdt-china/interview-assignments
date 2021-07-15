
package com.shorturl.common;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 模拟LRU缓存的map
 *
 * @author penghang
 * @created 7/12/21
 */
public class LruMap<K, V> extends LinkedHashMap<K, V> {

    public final int maxCapacity;

    public LruMap(int maxCapacity) {
        super(maxCapacity, 0.75f, true);
        this.maxCapacity = maxCapacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > maxCapacity;
    }
}