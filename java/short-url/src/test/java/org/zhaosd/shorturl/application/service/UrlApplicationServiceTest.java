package org.zhaosd.shorturl.application.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zhaosd.shorturl.domain.Url;
import org.zhaosd.shorturl.domain.UrlDomainService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UrlApplicationServiceTest {

    private String shortUrl;

    @Autowired
    UrlDomainService urlDomainService;

    @Autowired
    UrlApplicationService urlApplicationService;

    @Test
    void testGetByShortUrl() {
        String srcUrl = "http://just-test.com";
        Url url = urlDomainService.toShortUrl(srcUrl);
        shortUrl = url.getShortUrl();

        Url newUrl = urlApplicationService.getByShortUrl(shortUrl);
        assertNotNull(newUrl);
        assertEquals(18, newUrl.getShortUrl().length());
        assertEquals("http://s.cn/viy6fq", newUrl.getShortUrl());
        assertEquals(1, urlApplicationService.count());

        String exceptionShortUrl = "http://just.test.exception.com";
        try {
            urlApplicationService.getByShortUrl(exceptionShortUrl);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalStateException);
            assertEquals("仓库中没有该短链接：" + exceptionShortUrl, e.getMessage());
        }
    }

}