package com.suncy.shorturl.biz;

import com.suncy.shorturl.ShorturlApplication;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShorturlApplication.class)
public class ShortUrlTest {
    @Autowired
    @Qualifier("shortUrlBizImpl")
    private IShortUrlBiz shortUrlBiz;

    @Test
    public void toShortUrlTest() {
        String url = "https://www.baidu.com/";// jUvYbm63
        String shortUrl = shortUrlBiz.toShortUrl(url);
        System.out.println(shortUrl);
        Assert.assertEquals("jUvYbm63", shortUrl);
    }

    @Test
    public void toShortUrlExistTest() {
        String url = "https://www.baidu.com/";
        String shortUrl = shortUrlBiz.toShortUrl(url);
        shortUrl = shortUrlBiz.toShortUrl(url);
        System.out.println(shortUrl);
        Assert.assertEquals("jUvYbm63", shortUrl);
    }

    @Test
    public void findFullUrlTest() {
        String url = "https://www.baidu.com/";
        String shortUrl = shortUrlBiz.toShortUrl(url);
        String fullUrl = shortUrlBiz.findFullUrl(shortUrl);
        Assert.assertEquals(url, fullUrl);
    }

    @Test
    public void findFullUrlNotExistTest() {
        String url = "https://www.baidu.com/";
        String fullUrl = shortUrlBiz.findFullUrl("jUvYbm67");
        Assert.assertNull(fullUrl);
    }
}

