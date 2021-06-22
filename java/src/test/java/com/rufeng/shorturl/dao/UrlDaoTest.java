package com.rufeng.shorturl.dao;

import com.rufeng.shorturl.utils.ShortLinkUtils;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author qujingguo
 * @version 1.0.0
 * @date 2021/5/27 11:26 上午
 * @description url数据存储
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@DisplayName("短域名服务测试")
public final class UrlDaoTest {

    @Resource
    private UrlDao urlDao;

    @Test
    @DisplayName("获取长链接")
    public void getLongUrl() {
        String url = "http://www.baiud.com";
        String shortUrl = ShortLinkUtils.createShortUrl(url);
        urlDao.putUrl(shortUrl, url);
        assertEquals(urlDao.getLongUrl(shortUrl), url);
    }


    @Test
    @DisplayName("保存对应关系")
    public void putUrl() {
        String url = "http://www.baiud.com";
        String shortUrl = ShortLinkUtils.createShortUrl(url);
        urlDao.putUrl(shortUrl, url);
        assertEquals(urlDao.getShortUrl(url), shortUrl);
        assertEquals(urlDao.getLongUrl(shortUrl), url);
    }

    @Test
    @DisplayName("获取短连接")
    public void getShortUrl() {
        String url = "http://www.baiud.com";
        assertNull(urlDao.getShortUrl(url));
        String shortUrl = ShortLinkUtils.createShortUrl(url);
        urlDao.putUrl(shortUrl, url);
        assertEquals(urlDao.getShortUrl(url), shortUrl);
    }
}
