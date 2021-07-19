package com.wanghui.service.impl;

import com.wanghui.model.ShortUrl;
import com.wanghui.service.RepositoryService;
import com.wanghui.utils.CommonConstants;
import com.wanghui.utils.EHCacheUtils;
import com.wanghui.utils.IDMaker;
import net.sf.ehcache.management.Cache;
import org.apache.curator.shaded.com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wanghui
 * @title 模拟持久化业务逻辑现类
 * @Date 2021-07-17 23:25
 * @Description
 */
@Component
public class RepositoryServiceImpl implements RepositoryService {

    @Autowired
    private CacheManager cacheManager;

    @Override
    public boolean saveShorAndLongtUrl(String shortUrl, String longUrl) {
        if(getLongUrlByShortUrl(shortUrl) != null || getShortUrlByLongUrl(longUrl) !=null) {
            throw new RuntimeException("域名发生碰撞");
        }
        EHCacheUtils.setCache(cacheManager, longUrl, shortUrl);
        EHCacheUtils.setCache(cacheManager, shortUrl, longUrl);

        return true;
    }

    @Override
    public String getShortUrlByLongUrl(String longUrl) {

        Object object = EHCacheUtils.getCache(cacheManager, longUrl);
        return object == null ? null : object.toString();
    }

    @Override
    public String getLongUrlByShortUrl(String shortUrl) {
        Object object = EHCacheUtils.getCache(cacheManager, shortUrl);
        return object == null ? null : object.toString();
    }

}
