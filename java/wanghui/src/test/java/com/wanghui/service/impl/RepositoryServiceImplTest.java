package com.wanghui.service.impl;

import com.wanghui.service.RepositoryService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

@SpringBootTest
public class RepositoryServiceImplTest{

    @Autowired
    RepositoryService repositoryService;

    @Test
    void getShortUrlByLongUrl() {
        repositoryService.getShortUrlByLongUrl("http://www.baidu.com/123"+ new Random().nextInt());
    }


    @Test
    void saveShorAndLongtUrl() {
        repositoryService.saveShorAndLongtUrl("baidu.com/123"+ new Random().nextInt(),"http://www.baidu.com/123" + new Random().nextInt());
        Assert.assertThrows(RuntimeException.class,() -> repositoryService.saveShorAndLongtUrl("baidu.com/123","http://www.baidu.com/123"));
    }

    @Test
    void getLongUrlByShortUrl() {
        repositoryService.getLongUrlByShortUrl("baidu.com/123");
    }
}