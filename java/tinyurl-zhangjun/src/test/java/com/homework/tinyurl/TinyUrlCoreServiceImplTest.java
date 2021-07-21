package com.homework.tinyurl;

import com.homework.tinyurl.service.TinyUrlCoreServiceImpl;
import com.homework.tinyurl.utils.JvmUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.util.StopWatch;

/**
 * @Deacription
 * @Author zhangjun
 * @Date 2021/7/19 9:51 下午
 **/
@Slf4j
@SpringBootTest
public class TinyUrlCoreServiceImplTest {

    @Autowired
    private TinyUrlCoreServiceImpl tinyUrlCoreService;

    @Autowired
    private CacheManager cacheManager;


    @BeforeEach
    public void beforePrintSystemInfo() {
        JvmUtils.printCurrentSystemInfo();
    }

    @AfterEach
    public void afterPrintSystemInfo() {
        JvmUtils.printCurrentSystemInfo();
    }


    /**
     * 生成短地址
     */
    @Test
    void generateShortUrlTest() {
        StopWatch watch = new StopWatch();
        watch.start();
        String longUrl = "http://www.baidu.com/fgksgsgfsg/" + RandomStringUtils.randomAlphanumeric(10);
        String shortUrl = tinyUrlCoreService.generateShortUrl(longUrl);
        Assert.assertTrue("生成短链测试失败", StringUtils.isNotBlank(shortUrl));
        watch.stop();
        log.info("****************总耗时={},hash碰撞统计={}", watch.getTotalTimeMillis(), tinyUrlCoreService.getHashCollisionCount());
        // log.info("缓存占用内存大小={}", RamUsageEstimator.sizeOf(cacheManager.getCache("shortUrlMapping")) / 1024 / 1024);
    }

    /**
     * 批量测试
     */
    @Test
    void batchGenerateShortUrlTest() {
        StopWatch watch = new StopWatch();
        watch.start();
        for (int i = 0; i < 10000; i++) {
            String longUrl = "http://www.baidu.com/fgksgsgfsg/" + RandomStringUtils.randomAlphanumeric(10);
            tinyUrlCoreService.generateShortUrl(longUrl);
        }
        watch.stop();
        log.info("****************总耗时={},hash碰撞统计={}", watch.getTotalTimeMillis(), tinyUrlCoreService.getHashCollisionCount());
        //log.info("缓存占用内存大小={}", RamUsageEstimator.sizeOf(cacheManager.getCache("shortUrlMapping")) / 1024 / 1024);
    }

    /**
     * hash 碰撞case
     */
    @Test
    void testProcessHashCollision() {
        cacheManager.getCache("shortUrlMapping").put("436aHd", "www.test.com");
        String longUrl = "a_http://www.baidu.com/test/aaa";
        tinyUrlCoreService.generateShortUrl(longUrl);
    }


    /**
     * 获取长地址
     */
    @Test
    void getLongUrlTest() {
        String longUrl = "http://www.baidu.com/fgksgsgfsg/aaaaa";
        String shortUrl = tinyUrlCoreService.generateShortUrl(longUrl);
        String cacheLongUrl = tinyUrlCoreService.getLongUrl(shortUrl);
        Assert.assertTrue("获取长链测试失败", StringUtils.isNotBlank(cacheLongUrl));
    }

    /**
     * 模拟不存在的短链
     */
    @Test
    void getInValidLongUrlTest() {
        try {
            String cacheLongUrl = tinyUrlCoreService.getLongUrl("kkll");
        } catch (Exception e) {

        }
        //Assert.assertTrue("获取长链测试失败", StringUtils.isNotBlank(cacheLongUrl));
    }


}
