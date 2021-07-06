package com.service;

import static org.mockito.ArgumentMatchers.anyString;

import com.AbstractTest;
import com.TestData;
import com.exception.SystemException;
import com.service.impl.DomainNameServiceImpl;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

public class DomainNameServiceImplTest extends AbstractTest {

    @InjectMocks
    private DomainNameServiceImpl domainNameService;

    @Mock
    private IMatchStorageService matchStorageService;

    @Test
    public void testGetShortUrlWithCacheSuccess() {

        Mockito.when(matchStorageService.getShortUrlByLongUrl(anyString())).thenReturn(TestData.optionalString);
        String shortUrl = domainNameService.getShortUrl(anyString());
        Assert.assertEquals(shortUrl, TestData.SHORT_URL);
    }

    @Test
    public void testGetShortUrlWithOutCacheSuccess() {
        Mockito.when(matchStorageService.getShortUrlByLongUrl(anyString())).thenReturn(TestData.optionalEmpty);
        Mockito.when(matchStorageService.getCacheSize()).thenReturn(TestData.ONE);
        Mockito.when(matchStorageService.getLongUrlByShortUrl(anyString()))
                .thenReturn(TestData.optionalEmpty)
                .thenReturn(TestData.optionalString);

        String shortUrl = domainNameService.getShortUrl(TestData.LONG_URL);
        Assert.assertEquals(shortUrl, TestData.SHORT_URL_BAIDU);
    }

    @Test(expected = SystemException.class)
    public void testGetShortUrlWithCacheFailed() {
        Mockito.when(matchStorageService.getShortUrlByLongUrl(anyString())).thenReturn(TestData.optionalEmpty);
        Mockito.when(matchStorageService.getCacheSize()).thenReturn(TestData.ONE);
        Mockito.when(matchStorageService.getLongUrlByShortUrl(anyString()))
                .thenReturn(TestData.optionalString)
                .thenReturn(TestData.optionalString)
                .thenReturn(TestData.optionalString);

        domainNameService.getShortUrl(TestData.LONG_URL);
    }

    @Test(expected = SystemException.class)
    public void testGetShortUrlWithCacheFullFailed() {
        Mockito.when(matchStorageService.getShortUrlByLongUrl(anyString())).thenReturn(TestData.optionalEmpty);
        Mockito.when(matchStorageService.getCacheSize()).thenReturn(TestData.BIG_VALUE);
        domainNameService.getShortUrl(TestData.LONG_URL);
    }

    @Test
    public void testGetLongUrlSuccess() {
        Mockito.when(matchStorageService.getLongUrlByShortUrl(anyString())).thenReturn(TestData.optionalString);
        String shortUrl = domainNameService.getLongUrl(TestData.SHORT_URL);
        Assert.assertEquals(shortUrl, TestData.SHORT_URL);
    }

    @Test(expected = SystemException.class)
    public void testGetLongUrlFail() {
        Mockito.when(matchStorageService.getLongUrlByShortUrl(anyString())).thenReturn(TestData.optionalEmpty);
        domainNameService.getLongUrl(TestData.SHORT_URL);
    }
}
