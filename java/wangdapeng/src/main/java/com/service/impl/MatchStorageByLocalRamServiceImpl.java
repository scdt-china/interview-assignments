package com.service.impl;

import com.constant.CommonConstants;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.service.IMatchStorageService;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

@Component
public class MatchStorageByLocalRamServiceImpl implements IMatchStorageService {

    private Cache<String, String> shortToLongCache = CacheBuilder.newBuilder()
            .expireAfterWrite(CommonConstants.MAX_CACHE_DAYS, TimeUnit.DAYS)
            .maximumSize(CommonConstants.MAX_CACHE_SIZE)
            .build();

    private Cache<String, String> longToShortCache = CacheBuilder.newBuilder()
            .expireAfterWrite(CommonConstants.MAX_CACHE_DAYS, TimeUnit.DAYS)
            .maximumSize(CommonConstants.MAX_CACHE_SIZE)
            .build();

    @Override
    public Optional<String> getShortUrlByLongUrl(String longUrl) {
        return Optional.ofNullable(longToShortCache.getIfPresent(longUrl));
    }

    @Override
    public Optional<String> getLongUrlByShortUrl(String shortUrl) {
        return Optional.ofNullable(shortToLongCache.getIfPresent(shortUrl));
    }

    @Override
    public void setUrlMatch(String longUrl, String shortUrl) {
        shortToLongCache.put(shortUrl, longUrl);
        longToShortCache.put(longUrl, shortUrl);
    }

    @Override
    public long getCacheSize() {
        return shortToLongCache.size();
    }
}
