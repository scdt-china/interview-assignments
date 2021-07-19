package com.wanghui.utils;


import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Objects;

/**
 * @author wanghui
 * @title
 * @Date 2021-07-18 0:21
 * @Description
 */
public class EHCacheUtils<T> {
    /**
     * 设置缓存对象
     * @param cacheManager
     * @param key
     * @param object
     */
    public static void setCache(CacheManager cacheManager, String key, Object object){
        Cache cache = cacheManager.getCache("ShortUrl");
        assert cache != null;
        cache.put(key, object);
    }
    /**
     * 从缓存中取出对象
     * @param cacheManager
     * @param key
     * @return
     */
    public static Object getCache(CacheManager cacheManager, String key){
        Object object = null;
        Cache cache = cacheManager.getCache("ShortUrl");
        assert cache != null;
        if(cache.get(key)!=null && !Objects.equals(cache.get(key), "")){
            object = cache.get(key, Object::new);
        }
        return object;
    }
}
