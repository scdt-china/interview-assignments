package com.tzg157.demo.test.service;

import com.tzg157.demo.DemoTestBase;
import com.tzg157.demo.constant.CacheKeyConstant;
import com.tzg157.demo.model.Response;
import com.tzg157.demo.model.UrlResult;
import com.tzg157.demo.service.DomainConvertService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;

import javax.annotation.Resource;

@SpringBootTest
public class DomainConvertServiceCatchTest extends DemoTestBase {


    @Resource
    private DomainConvertService domainConvertService;

    @MockBean
    private CacheManager cacheManager;

    @Test
    public void testConvertToShortThrowable(){
        Mockito.when(cacheManager.getCache(CacheKeyConstant.DOMAIN_CACHE_KEY)).thenReturn(null);
        Response<UrlResult> response = domainConvertService.convertToShort(LONG_REQUEST_URL);
        Assertions.assertEquals(1,response.getCode());
    }

    @Test
    public void testConvertToLongThrowable(){
        Mockito.when(cacheManager.getCache(CacheKeyConstant.DOMAIN_CACHE_KEY)).thenReturn(null);
        Response<UrlResult> response = domainConvertService.convertToLong(SHORT_REQUEST_URL);
        Assertions.assertEquals(1,response.getCode());
    }
}
