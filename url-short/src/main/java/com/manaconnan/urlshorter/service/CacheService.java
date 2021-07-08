package com.manaconnan.urlshorter.service;

import com.manaconnan.urlshorter.config.LruCache;
import com.manaconnan.urlshorter.utils.SystemMonitor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/**
 * @Author mazexiang
 * @CreateDate 2021/7/4
 * @Version 1.0
 */
@Service
@Slf4j
public class CacheService  implements InitializingBean{
    private static final int BASE_URL_LENGTH = 500;
    private static final double MEMERY_USE_RATIO = 0.8;
    private LruCache<String,String> lruCache;
    @Override
    public void afterPropertiesSet() throws Exception {
        long maxMemorySize = SystemMonitor.getMaxMemorySize();
        lruCache = new LruCache<>((int)(maxMemorySize/BASE_URL_LENGTH * MEMERY_USE_RATIO));
        log.info(" ============= lruCache initiate size = {}=============",lruCache.getMaxMemorySize());
    }

    public String put(String key,String value){
        return lruCache.put(key,value);
    }

    public String get(String key){
        return lruCache.get(key);
    }

}
