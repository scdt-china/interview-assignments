package com.service;

import com.AbstractTest;
import com.TestData;
import com.service.impl.DomainNameServiceImpl;
import com.service.impl.MatchStorageByLocalRamServiceImpl;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;

public class MatchStorageByLocalRamServiceImplTest extends AbstractTest {

    @InjectMocks
    private MatchStorageByLocalRamServiceImpl matchStorageByLocalRamService;

    @Test
    public void testGetCacheSize() {
        matchStorageByLocalRamService.setUrlMatch(TestData.SHORT_URL, TestData.LONG_URL);
        long size = matchStorageByLocalRamService.getCacheSize();
        Assert.assertEquals(1L, size);
    }

    @Test
    public void testGetShortUrlByLongUrl() {
        matchStorageByLocalRamService.setUrlMatch(TestData.LONG_URL, TestData.SHORT_URL);
        Optional<String> longUrl = matchStorageByLocalRamService.getLongUrlByShortUrl(TestData.SHORT_URL);
        Assert.assertEquals(longUrl.get(), TestData.LONG_URL);
    }

    @Test
    public void testGetLongUrlByShortUrl() {
        matchStorageByLocalRamService.setUrlMatch(TestData.LONG_URL, TestData.SHORT_URL);
        Optional<String> shortUrl = matchStorageByLocalRamService.getShortUrlByLongUrl(TestData.LONG_URL);
        Assert.assertEquals(shortUrl.get(), TestData.SHORT_URL);
    }
}
