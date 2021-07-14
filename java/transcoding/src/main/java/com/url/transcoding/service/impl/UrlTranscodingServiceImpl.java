package com.url.transcoding.service.impl;

import com.url.transcoding.Component.LocalCache;
import com.url.transcoding.service.UrlTranscodingService;
import com.url.transcoding.util.ShortUrlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UrlTranscodingServiceImpl implements UrlTranscodingService {

    @Autowired
    private LocalCache<Object> cache;

    @Override
    public String longToShort(String longUrl){
        String shortUrl = ShortUrlUtil.shortUrl(longUrl);
        //设置本地缓存
        cache.setLocalCache(shortUrl, longUrl);
        return shortUrl;
    }

    @Override
    public String shortToLong(String shortUrl) {
        return cache.getCache(shortUrl);
    }
}
