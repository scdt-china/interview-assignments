package com.tzg157.demo.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
public class CacheConfig extends CachingConfigurerSupport {

    @Bean("caffeineCacheManager")
    @Override
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();

        cacheManager.setCaffeine(Caffeine.newBuilder()
                .softValues()
                .expireAfterAccess(1, TimeUnit.HOURS)//1小时过期
                .initialCapacity(100)//初始100
                .maximumSize(1000));//最大1000

        return cacheManager;
    }
}
