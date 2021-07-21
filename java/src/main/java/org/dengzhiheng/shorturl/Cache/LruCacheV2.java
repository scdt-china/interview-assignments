package org.dengzhiheng.shorturl.Cache;


/**
 * LruCache实现，线程安全+高并发
 * @author : When6passBye
 * @date : 2021/7/19 下午5:12
 */
public class LruCacheV2<K, V> {

    private final LruCacheV1<K, V>[] cacheSegments;

    public LruCacheV2(final int maxCapacity) {
        int cores = Runtime.getRuntime().availableProcessors();
        int concurrency = Math.max(cores, 2);
        cacheSegments = new LruCacheV1[concurrency];
        int segmentCapacity = maxCapacity / concurrency;
        if (maxCapacity % concurrency == 1) segmentCapacity++;
        for (int index = 0; index < cacheSegments.length; index++) {
            cacheSegments[index] = new LruCacheV1<>(segmentCapacity);
        }
    }

    public LruCacheV2(final int concurrency, final int maxCapacity) {
        cacheSegments = new LruCacheV1[concurrency];
        int segmentCapacity = maxCapacity / concurrency;
        if (maxCapacity % concurrency == 1) segmentCapacity++;
        for (int index = 0; index < cacheSegments.length; index++) {
            cacheSegments[index] = new LruCacheV1<>(segmentCapacity);
        }
    }

    private int segmentIndex(K key) {
        int hashCode = Math.abs(key.hashCode() * 31);
        return hashCode % cacheSegments.length;
    }

    private LruCacheV1<K, V> cache(K key) {
        return cacheSegments[segmentIndex(key)];
    }

    public void put(K key, V value) {
        cache(key).put(key, value);
    }

    public V get(K key) {
        return cache(key).get(key);
    }

    public int size() {
        int size = 0;
        for (LruCacheV1<K, V> cache : cacheSegments) {
            size += cache.size();
        }
        return size;
    }
}
