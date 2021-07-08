package com.manaconnan.urlshorter.service;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @Author mazexiang
 * @CreateDate 2021/7/3
 * @Version 1.0
 */
@Service
@Slf4j
public class BloomFilterService implements InitializingBean {
    private static BloomFilter<String> bloomFilter = BloomFilter.create(
            Funnels.stringFunnel(Charset.defaultCharset()), 1000000);

    @Autowired
    private ShortUrlService shortUrlService;

    public boolean mightExist(String hashValue) {
        return bloomFilter.mightContain(hashValue);
    }

    public void put(String hashValue) {
        bloomFilter.put(hashValue);
    }

    @Override
    public void afterPropertiesSet() {
        log.info("===============init BloomFilter, load data from database......==============");
        long startTime = System.currentTimeMillis();
        long count = 0;
        //load data from database to  BloomFilter , may have OOM problem
        List<String> allHashValue = shortUrlService.queryAll();
        for (String hash : allHashValue) {
            bloomFilter.put(hash);
        }
        count = allHashValue.size();
        long costTime = System.currentTimeMillis() - startTime;
        log.info("===============init BloomFilter finished ,costTime:[{}],Count:[{}]==============", costTime, count);
    }
}
