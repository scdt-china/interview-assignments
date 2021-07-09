package com.wangxb.convert.service;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class UrlConvertServiceTest {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void convertUrl() {

        UrlConvertService urlConvertService = new UrlConvertService();
        List<String> listShortUrl = Lists.newArrayList();
        int i = 0;
        int urlSize = 1000000;
        while(i<urlSize) {
            String longUrl = "http://www.sina.com.cn/" + i;
            String shortUrl = urlConvertService.getShortUrl(longUrl);
            logger.info("请求长链接:{} 返回的短链接:{}", longUrl, shortUrl);
            i++;
            listShortUrl.add(shortUrl);
        }

        for(String shortUrl : listShortUrl){
            String longUrl = urlConvertService.getLongUrl(shortUrl);
            logger.info("请求短链接:{} 返回的长链接:{}", shortUrl,longUrl);
        }

        logger.info("处理结束");
    }
}
