package com.zdkj;

import com.zdkj.handler.cache.ShortUrlCache;
import com.zdkj.modler.shorturl.param.ShortUrlReadParam;
import com.zdkj.modler.shorturl.param.ShortUrlSaveParam;
import com.zdkj.modler.shorturl.service.ShortUrlService;
import com.zdkj.util.IdUtil;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShortUrlApplicationTest {


    @Autowired
    ShortUrlService shortUrlService;

    @Autowired
    ShortUrlCache shortUrlCache;


    @Test
    public void shortUrlSave() {
        ShortUrlSaveParam shortUrlSaveParam = new ShortUrlSaveParam();
        shortUrlSaveParam.setLongUrl("http://test.com/etst/sksh");
        shortUrlSaveParam.setTermOfValidity(Long.valueOf(1000));
        String res = shortUrlService.shortUrlSave(shortUrlSaveParam);
        Assert.assertEquals( "http://t.com/2qq2U",res);
    }



    @Test
    public void shortUrlRead() {
        shortUrlCache.put("http://t.com/2qqq2","https://test.com/etst/sksh",1000L);
        ShortUrlReadParam shortUrlReadParam = new ShortUrlReadParam();
        shortUrlReadParam.setShortUrl("http://t.com/2qqq2");
        String res = shortUrlService.shortUrlRead(shortUrlReadParam);
        Assert.assertEquals("https://test.com/etst/sksh",res);
    }

    @SneakyThrows
    @Test
    public void testId() {

        final Integer nThreads = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(nThreads);
        ConcurrentHashMap.KeySetView<Object, Boolean> beanSet = ConcurrentHashMap.newKeySet();
        CountDownLatch latch = new CountDownLatch(nThreads);
        for (int i = 0; i < nThreads; i++) {
            executorService.submit(() -> {
                try {
                    beanSet.add(IdUtil.nextId());
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        assertEquals("并发获取的id不等于并发数", nThreads.longValue(), beanSet.size());
    }


}
