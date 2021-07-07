package com.sequoia.shorturl.service;

import com.sequoia.shorturl.common.ResponseResult;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
/**
 *
 * 短域名服务实现接口单元测试
 *
 * @Author xj
 *
 * @Date 2021/6/27
 *
 * @Author xj
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShortUrlServiceImplTest {
    @Autowired
    ShortUrlService shortUrlService;

    /**
     * 测试正常的 长域名转换为短域名
     * 然后根据短域名查找原长域名
     */
    @Test
    public void createShortUrlAndGetOriginalUrlTest() {
        String originUrl="https://zhuanlan.zhihu.com/p/194199097";
        ResponseResult response = shortUrlService.createShortUrl(originUrl);
        System.out.println(response.getData().toString());
        String shortUrl=response.getData().toString();
        ResponseResult response2=shortUrlService.getOriginalUrl(shortUrl);
        String originUrl2=response2.getData().toString();
        assertEquals(originUrl,originUrl2);
    }

    /***
     * 测试 不传 参数
     */
    @Test
    public void createShortUrlTest() {
        ResponseResult response=shortUrlService.createShortUrl(null);
        assertEquals(response.getData(),null);
    }
    /***
     * 测试 不传 参数
     */
    @Test
    public void getOriginalUrlTest(){
        ResponseResult response=shortUrlService.getOriginalUrl(null);
        assertEquals(response.getData(),null);
    }

}
