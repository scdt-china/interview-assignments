package com.tzg157.demo.test.service;

import com.tzg157.demo.DemoTestBase;
import com.tzg157.demo.constant.CacheKeyConstant;
import com.tzg157.demo.model.Response;
import com.tzg157.demo.model.UrlResult;
import com.tzg157.demo.service.DomainConvertService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import javax.annotation.Resource;

@SpringBootTest
public class DomainConvertServiceDefaultTest extends DemoTestBase {

    @Resource
    private DomainConvertService domainConvertService;

    @Resource
    private CacheManager cacheManager;

    private Cache caffeineCache;

    @BeforeEach
    public void initCache(){
        caffeineCache = cacheManager.getCache(CacheKeyConstant.DOMAIN_CACHE_KEY);

        caffeineCache.putIfAbsent(SHORT_REQUEST_URL,LONG_REQUEST_URL);
        caffeineCache.putIfAbsent(LONG_REQUEST_URL,SHORT_REQUEST_URL);
    }

    @Test
    public void testConvertToLongIllegalSchema(){
        String schema = "file://com.tzg157.demo.service.DomainConvertServiceTest.java";
        Response<UrlResult> response = domainConvertService.convertToLong(schema);
        Assertions.assertEquals(1,response.getCode());
    }

    @Test
    public void testConvertToLongFromCache(){
        Response<UrlResult> response = domainConvertService.convertToLong(SHORT_REQUEST_URL);
        String url = response.getResult().getUrl();
        Assertions.assertEquals(LONG_REQUEST_URL,url);
    }

    @Test
    public void testConvertToLongCacheMiss(){
        caffeineCache.clear();
        Response<UrlResult> response = domainConvertService.convertToLong(SHORT_REQUEST_URL_2);
        Assertions.assertEquals(1,response.getCode());
    }

    @Test
    public void testConvertToShortIllegalSchema(){
        String schema = "file://com.tzg157.demo.service.DomainConvertServiceTest.java";
        Response<UrlResult> response = domainConvertService.convertToShort(schema);
        Assertions.assertEquals(1,response.getCode());
    }

    @Test
    public void testConvertToShortFromCache(){
        Cache.ValueWrapper value = caffeineCache.get(LONG_REQUEST_URL);
        Response<UrlResult> response = domainConvertService.convertToShort(LONG_REQUEST_URL);
        String url = response.getResult().getUrl();
        Assertions.assertEquals(value.get(),url);
    }

    @Test
    public void testConvertToShortCacheMiss(){
        caffeineCache.clear();
        Cache.ValueWrapper value = caffeineCache.get(LONG_REQUEST_URL_2);
        Assertions.assertNull(value);
        domainConvertService.convertToShort(LONG_REQUEST_URL_2);
        value = caffeineCache.get(LONG_REQUEST_URL_2);
        Assertions.assertNotNull(value);
    }

    @Test
    public void testConvertToShort(){
        caffeineCache.clear();
        Cache.ValueWrapper value = caffeineCache.get(LONG_REQUEST_URL_2);
        Assertions.assertNull(value);
        Response<UrlResult> response = domainConvertService.convertToShort(LONG_REQUEST_URL_2);
        value = caffeineCache.get(LONG_REQUEST_URL_2);
        Assertions.assertNotNull(value);
        Assertions.assertEquals(SHORT_REQUEST_URL,response.getResult().getUrl());
    }
}
