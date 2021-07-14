package com.fh.shorturl.service.impl;

import com.fh.shorturl.ShorturlApplication;
import com.fh.shorturl.service.ShortURLService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShorturlApplication.class)
@WebAppConfiguration // 开启web应用配置
public class ShortURLServiceImplTest {

    @Autowired
    ShortURLService shortURLService;

    @Test
    public void addShortURL() {
        String actual=shortURLService.addShortURL("https://www.qq.com");
        Assert.assertEquals("63i6Zn",actual);
    }

    @Test
    public void zip() {
        String actual=ShortURLServiceImpl.zip("5ff12ba2fefb4b5b5e3228f75dbf00b0");
        Assert.assertEquals("63i6Zn",actual);
    }

    @Test
    public void queryLongURL() {
        String actual=shortURLService.queryLongURL("63i6Zn");
        Assert.assertEquals("https://www.qq.com",actual);
    }
}