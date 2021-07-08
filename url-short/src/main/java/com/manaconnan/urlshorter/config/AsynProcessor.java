package com.manaconnan.urlshorter.config;

import com.manaconnan.urlshorter.service.BloomFilterService;
import com.manaconnan.urlshorter.service.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;


/**
 * @Author mazexiang
 * @CreateDate 2021/7/4
 * @Version 1.0
 */
@EnableAsync
@Component
@Slf4j
public class AsynProcessor {
    @Autowired
    private BloomFilterService bloomFilterService;
    @Autowired
    private CacheService cacheService;
    @Bean
    public AsyncTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("bloom-Executor-");
        int thread = Runtime.getRuntime().availableProcessors() * 2;
        executor.setMaxPoolSize(thread);
        executor.setCorePoolSize(thread >> 1);
        executor.setKeepAliveSeconds(5);
        executor.setQueueCapacity(100000);
        return executor;
    }



    @Async
    public void add2BloomFilterAndCacheAndDb(String key, String value) {
        log.info("add [shortCut]={},[url]={} to bloom filer , cache and db ....",key,value);
        //  step1: save to local cache and bloom filter
        cacheService.put(key,value);

        // step2 : save to bloom filter
        bloomFilterService.put(value);

        // todo
        // step3: save to db
    }
}
