package com.wanghui.service.impl;

import com.wanghui.model.ShortUrl;
import com.wanghui.service.DomainService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DomainServiceImplTest {

    @Autowired
    DomainService domainService;
    @Test
    void short2LongUrl() {
        ShortUrl shortUrl = domainService.long2ShortUrl("http://www.baidu.com/123");
        domainService.short2LongUrl(shortUrl.getShortUrl());
        domainService.short2LongUrl(shortUrl.getShortUrl() + "111");
    }

    @Test
    void long2ShortUrl() {
        String longUrl = "http://www.baidu.com/123" + new Random().nextInt();
        domainService.long2ShortUrl(longUrl);
        domainService.long2ShortUrl(longUrl);
        domainService.long2ShortUrl("http://www.wanghui.com/123" + new Random().nextInt());
        Assert.assertThrows(RuntimeException.class,() ->domainService.long2ShortUrl("wanghui.com/123"));
    }
}