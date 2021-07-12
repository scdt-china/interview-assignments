package com.hongshanziben.assignment;

import com.hongshanziben.assignment.api.service.DomainService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * @author SJYUAN:KINGSJ.YUAN@FOXMAIL.COM
 * @date 2021-07-08.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShortDomainTest {

    @Autowired
    private DomainService domainService;

    private CountDownLatch count = new CountDownLatch(10);

    @Test
    public void createShortDomain() {
        String url = "https://www.baidu.com/?tn=64075107_1_dg";
        String s = domainService.createShortDomain(url);
        Assert.assertNotNull(s);
        String longUrl = domainService.getDomain(s);
        Assert.assertEquals(url, longUrl);
    }

    @Test
    public void mutiCreate() {
        String url = "https://www.baidu.com/?tn=64075107_1_dg";
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                String shortDomain = domainService.createShortDomain(url);
                map.put(shortDomain, url);
                count.countDown();
            }).start();
        }
        try {
            count.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(1, map.size());
    }

}
