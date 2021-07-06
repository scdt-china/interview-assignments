package org.zhaosd.shorturl.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UrlDomainServiceTest {

    @Autowired
    UrlDomainService urlDomainService;

    @Autowired
    UrlApplicationService urlApplicationService;

    @Test
    void testToShortUrl() {
        String srcUrl = "http://just-test.com";
        Url url = urlDomainService.toShortUrl(srcUrl);
        assertUrl(url);

        Url anotherUrl = urlDomainService.toShortUrl(srcUrl);
        assertUrl(anotherUrl);
    }

    @Test
    void testOutOfMemory() {
        String baseUrl = "http://just-test.com";
        for (int i = 0; i < 1000 * 10000; i++) {
            if (i % 10000 == 0) {
                String msg = new StringBuilder("After save url count: ").append(i)
                        .append(", real url count in memory: ").append(urlApplicationService.count())
                        .append(" total memory is ")
                        .append(Runtime.getRuntime().totalMemory()/(1024*1024)).append("M").toString();
                System.out.println(msg);
            }
            String srcUrl = new StringBuilder(baseUrl).append("/link_").append(i).toString();
            urlDomainService.toShortUrl(srcUrl);
        }
    }

    private void assertUrl(Url url) {
        assertNotNull(url);
        assertEquals(18, url.getShortUrl().length());
        assertEquals("http://s.cn/viy6fq", url.getShortUrl());
        assertEquals(1, urlApplicationService.count());
    }
}