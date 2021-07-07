package com.yang.shorturl.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.Objects;

/**
 * 短地址服务单元测试
 */
@Slf4j
@SpringBootTest
public class ShortUrlServiceTest {

    private ShortUrlService shortUrlService;

    @Autowired
    public void setShortUrlService(ShortUrlService shortUrlService) {
        this.shortUrlService = shortUrlService;
    }

    @Test
    void testShortUrlService() {
        for (int i = 0; i < 2000; i++) {
            String testUrl = "http://www.baidu.com/" + i;
            String shortUrl = shortUrlService.getShortUrl(testUrl);
            String urlFromShortUrl = shortUrlService.getUrlFromShortUrl(shortUrl);
            log.info("长地址:{}对应的短地址{}", testUrl, shortUrl);
            log.info("短地址:{}反查的长地址{}", shortUrl, urlFromShortUrl);
            Assert.isTrue(testUrl.equals(urlFromShortUrl), "长地址和短地址不能正确对应");
            String shortUrl1 = shortUrlService.getShortUrl(null);
            Assert.isTrue(Objects.isNull(shortUrl1), "长地址为空的时候返回了结果");
            String urlFromShortUrl1 = shortUrlService.getUrlFromShortUrl(null);
            Assert.isTrue(Objects.isNull(urlFromShortUrl1), "短地址为空的时候返回了结果");
        }
    }
}
