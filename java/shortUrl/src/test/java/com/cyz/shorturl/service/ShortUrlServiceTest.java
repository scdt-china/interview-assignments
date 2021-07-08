package com.cyz.shorturl.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.util.Objects;

@RunWith(SpringRunner.class) //spring环境
@SpringBootTest  //springboot项目测试标签
class ShortUrlServiceTest {

    @Autowired
    private ShortUrlService shortUrlService;

    @Test
    void shortenUrl() {
        String url = "http://www.163.com";
        String shortUrl = shortUrlService.shortenUrl(url);
        Assert.isTrue(Objects.nonNull(shortUrl), "短域名不为空");
    }

    @Test
    void originalUrl() {
        String url = "http://www.163.com";
        String shortUrl = shortUrlService.shortenUrl(url);
        String longUrl = shortUrlService.originalUrl(shortUrl);
        Assert.isTrue(Objects.nonNull(shortUrl), "短域名为空");
        Assert.isTrue(Objects.nonNull(longUrl), "长域名为空");
        Assert.isTrue(url.equals(longUrl), "短域名和长域名匹配");
    }
}