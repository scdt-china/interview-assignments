package com.rufeng.shorturl.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author qujingguo
 * @version 1.0.0
 * @date 2021/5/27 11:22 上午
 * @description 短域名服务service
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@DisplayName("短域名服务测试")
@Slf4j
public class ShortUrlServiceTest {

    @Resource
    private ShortUrlService shortUrlService;

    @Test
    @DisplayName("接受长域名信息，返回短域名信息")
    public void longToShort() throws InterruptedException {
        String longUrl = "http://www.baidu.com";
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 10000; i++) {
            executorService.execute(() -> {
                String result = shortUrlService.longToShort(longUrl);
                log.info("result：{}", result);
                assertNotEquals(result, null);
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

    }


    @Test
    @DisplayName("接受短域名信息，返回长域名信息")
    public void shortToLong() {
        String longUrl = "http://www.baidu.com";
        String shortUrl = shortUrlService.longToShort(longUrl);
        log.info("shortUrl：{}", shortUrl);
        assertEquals(shortUrlService.shortToLong(shortUrl), longUrl);
        assertNull(shortUrlService.shortToLong("111#"));
    }


}
