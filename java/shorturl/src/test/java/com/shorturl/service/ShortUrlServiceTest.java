package com.shorturl.service;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ShortUrlServiceTest {

    @Resource
    ShortUrlService shortUrlService;

    private static final String originalUrl = "https://www.baidu.com/";
    private static final String shortUrl = "http://heiqiuren.com/26s96h";


    @Test
    void getAOriginalUrl() {
        final String originalUrlRes = shortUrlService.getOriginalUrl(shortUrl);
        Assert.assertEquals(null, originalUrlRes);
    }

    @Test
    void getBShortUrl() {
        final String shortUrlRes = shortUrlService.getShortUrl(originalUrl);
        Assert.assertEquals(shortUrl, shortUrlRes);
    }

    @Test
    void getCShortUrl() {
        final String shortUrlRes = shortUrlService.getShortUrl(originalUrl);
        Assert.assertEquals(shortUrl, shortUrlRes);
    }

    @Test
    void getDOriginalUrl() {
        final String originalUrlRes = shortUrlService.getOriginalUrl(shortUrl);
        Assert.assertEquals(originalUrl, originalUrlRes);
    }


}