package com.sequoia.shorturl.config;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.github.benmanes.caffeine.cache.Scheduler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;import java.util.concurrent.TimeUnit;

/**
 *
 * 短域名跟原域名映射关系的本地缓存
 * 缓存在caffeine 中
 * @Author xj
 *
 * @Date 2021/6/28
 *
 * @version v1.0。0
 *
 */
@Slf4j
@Configuration
public class CacheConfig {

    /**
     *  缓存 存放 短码跟原域名映射关系
     *  可以根据shortCode 查找对应的 originalUrl
     *  key 为shortCode 为8位短码
     *  value 为原来要转换的域名
     *
     */
    @Bean
    public Cache<String, String> shortCodeCache() {
        return Caffeine.newBuilder()
                // 设置最后一次写入或访问后经过固定时间过期
                //.expireAfterWrite(60, TimeUnit.SECONDS)
                // 初始的缓存空间大小
                .initialCapacity(1000)
                .expireAfterAccess(1, TimeUnit.DAYS) //有效期一天
                // 缓存的最大条数
                .maximumSize(500000)
                .build();
    }
    /**
     *
     *  缓存 存放 原域名跟短码映射关系
     *  可以根据 originalUrl 查找对应的 shortCode
     *  key 为 originalUrl 原域名地址
     *  value 为shortCode 为8位短码
     *
     */
    @Bean
    public Cache<String, String> oriUrlCache() {
        return Caffeine.newBuilder()
                // 设置最后一次写入或访问后经过固定时间过期
                //.expireAfterWrite(60, TimeUnit.SECONDS)
                // 初始的缓存空间大小
                .initialCapacity(1000)
                .expireAfterAccess(1, TimeUnit.DAYS) //有效期一天

                // 缓存的最大条数
                .maximumSize(500000)
                .build();
    }
}
