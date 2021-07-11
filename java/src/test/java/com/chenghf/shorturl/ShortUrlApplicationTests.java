package com.chenghf.shorturl;

import com.chenghf.shorturl.controller.ShortUrlController;
import com.chenghf.shorturl.service.IShortUrlService;
import com.chenghf.shorturl.service.impl.ShortUrlServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShortUrlApplicationTests {


    /**
     * 测试长域名转短域名
     * @author chf
     * @date 2021年7月7日
     */
    @Test
    void testLongToShort() {
        IShortUrlService shortUrlService = new ShortUrlServiceImpl();
        String shortUrl = shortUrlService.longToShort("http://isee.xyz/a/60ae1613cfe9924ea139034d");
        System.out.println(shortUrl);
        Assert.assertNotNull(shortUrl);

    }

    /**
     * 测试转域名长短域名
     * @author chf
     * @date 2021年7月7日
     */
    @Test
    void testShortToLong() {
        IShortUrlService shortUrlService = new ShortUrlServiceImpl();
        String longUrl = shortUrlService.shortToLong("http://localhost:8080/shortToLong/aaaaac");
        System.out.println(longUrl);
        Assert.assertNull(longUrl);

    }
}
