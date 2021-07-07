package com.zdkj.config;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import com.zdkj.handler.cache.ShortUrlCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author bihuiwen
 * @version 1.0
 * @description: 缓存配置
 * @date 2021/7/4 下午11:40
 */
@Configuration
public class CacheConfig {

    @Bean
    public ShortUrlCache mappingCache() {
        TimedCache<String, String> timedCache =
                CacheUtil.newTimedCache(Integer.MAX_VALUE);
        //取消定时清理
        timedCache.cancelPruneSchedule();
        return new ShortUrlCache(timedCache);
    }
}

