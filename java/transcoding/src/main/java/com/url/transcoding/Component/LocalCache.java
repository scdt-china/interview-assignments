package com.url.transcoding.Component;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@Component
public class LocalCache<T> {

    private Cache<String,T> localCache = null;

    @PostConstruct
    private void init(){
        localCache = CacheBuilder.newBuilder()
                //设置本地缓存容器的初始容量
                .initialCapacity(10)
                //设置本地缓存的最大容量
                .maximumSize(50)
                //设置写缓存后多少秒过期
                .expireAfterWrite(60, TimeUnit.MINUTES).build();
    }

    public void setLocalCache(String key,T object){
        localCache.put(key,object);
    }

    /***
     * 返回值 如果不存在返回null
     * @param key
     * @param <T>
     * @return
     */
    public <T> T getCache(String key){
       return (T) localCache.getIfPresent(key);
    }

}