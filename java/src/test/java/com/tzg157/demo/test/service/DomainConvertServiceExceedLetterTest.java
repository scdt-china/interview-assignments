package com.tzg157.demo.test.service;

import com.tzg157.demo.DemoTestBase;
import com.tzg157.demo.constant.CacheKeyConstant;
import com.tzg157.demo.model.Response;
import com.tzg157.demo.model.UrlResult;
import com.tzg157.demo.service.DomainConvertService;
import com.tzg157.demo.service.number.IdNumberService;
import com.tzg157.demo.util.NumericConvertUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import javax.annotation.Resource;

@SpringBootTest
public class DomainConvertServiceExceedLetterTest extends DemoTestBase {

    @Resource
    private DomainConvertService domainConvertService;

    @Resource
    private CacheManager cacheManager;

    private Cache caffeineCache;

    @Resource
    private IdNumberService idNumberService;

    @BeforeEach
    public void initCache(){
        caffeineCache = cacheManager.getCache(CacheKeyConstant.DOMAIN_CACHE_KEY);
    }

    @Test
    public void testConvertToShortTooLong(){
        caffeineCache.clear();
        Long id = idNumberService.getCurrentId();
        try(MockedStatic<NumericConvertUtils> utils = Mockito.mockStatic(NumericConvertUtils.class)) {
            utils.when(()->NumericConvertUtils.toOtherNumberSystem(id,62)).thenReturn("muchMore8Letters");
            Response<UrlResult> response = domainConvertService.convertToShort(LONG_REQUEST_URL_2);
            Assertions.assertEquals(1,response.getCode());
        }
    }
}
