package com.homework.tinyurl.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @Deacription
 * @Author zhangjun
 * @Date 2021/7/17 10:24 下午
 **/
@Configuration
@Slf4j
public class CacheConfig {

    /**
     * 初始容量
     */
    @Value("${localCache.initialCapacity:100000}")
    private Integer initialCapacity;
    /**
     * 最大容量,超过进行lru回收 1024*10000
     */
    @Value("${localCache.maximumSize:5000000}")
    private Integer maximumSize;
    /**
     * 有效期30天
     */
    @Value("${localCache.expire:30}")
    private Integer expireDay;
    /*
    @Bean
    public Cache<String, String> caffeineCache() {
        return Caffeine.newBuilder()
                //超过时间进行 lru
                .expireAfterAccess(expireDay,TimeUnit.DAYS)
                // 初始的缓存空间大小
                .initialCapacity(initialCapacity)
                // 缓存的最大条数
                .maximumSize(maximumSize)
                .removalListener((RemovalListener<String, String>) (key, value, cause)
                        -> log.info("key:" + key + ", value:" + value + ", 删除原因:" + cause.toString()))
                .build();
    }*/


    //配置CacheManager
    @Bean(name = "caffeineCacheManager")
    public CacheManager cacheManagerWithCaffeine() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        Caffeine caffeine = Caffeine.newBuilder()
                //超过时间进行 lru
                .expireAfterAccess(expireDay, TimeUnit.DAYS)
                // 初始的缓存空间大小
                .initialCapacity(initialCapacity)
                .removalListener((RemovalListener<String, String>) (key, value, cause)
                        -> log.info("开始进行lru回收 key:" + key + ", value:" + value + ", 删除原因:" + cause.toString()))
                // 缓存的最大条数
                .maximumSize(maximumSize);
        cacheManager.setCaffeine(caffeine);
        cacheManager.setAllowNullValues(false);
        return cacheManager;
    }

}
