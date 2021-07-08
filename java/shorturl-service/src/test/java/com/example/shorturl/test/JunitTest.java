package com.example.shorturl.test;

import com.alibaba.fastjson.JSONObject;
import com.example.shorturl.service.UrlService;
import org.junit.Test;

/**
 * @author : shenhc
 * @date : 2021/7/6
 * desc:
 */
public class JunitTest {

    private UrlService urlService = new UrlService();

    @Test
    public void testShortUrl(){

        String shortUrlStr = urlService.getShorturl("www.baidu.com");
        System.out.println(shortUrlStr);
    }

    @Test
    public void testLongUrl(){
        String shortUrlStr = urlService.getLongurl("oIpU0");
        System.out.println(shortUrlStr);
    }
}
