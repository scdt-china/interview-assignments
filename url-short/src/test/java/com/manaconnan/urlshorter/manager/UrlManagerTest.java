package com.manaconnan.urlshorter.manager;

import com.manaconnan.urlshorter.model.BaseResponse;
import com.manaconnan.urlshorter.model.request.UrlRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author mazexiang
 * @CreateDate 2021/7/4
 * @Version 1.0
 */
@SpringBootTest
class UrlManagerTest {
    @Autowired
    private UrlManager urlManager;


    @Test
    void convertShortUrlSnowFlake()  throws Exception{
        UrlRequest request = new UrlRequest("http://www.baidu.com");
        BaseResponse<String> stringBaseResponse = urlManager.convertShortUrlSnowFlake(request);
        Assert.isTrue(stringBaseResponse.getCode() == 200);
        TimeUnit.SECONDS.sleep(1);

        stringBaseResponse = urlManager.convertShortUrlSnowFlake(request);
        Assert.isTrue(stringBaseResponse.getCode() == 200);
    }

    @Test
    void convertShortUrl() throws Exception {
//
        UrlRequest request = new UrlRequest();
        request.setUrl("http://www.google.com");
        BaseResponse<String> stringBaseResponse = urlManager.convertShortUrl(request);
        Assert.isTrue(stringBaseResponse.getCode() == 200);
        TimeUnit.SECONDS.sleep(1);
        stringBaseResponse = urlManager.convertShortUrl(request);
        Assert.isTrue(stringBaseResponse.getCode() == 200);
    }

    @Test
    void getOriginUrl() {
        UrlRequest request = new UrlRequest("http://www.baidu.com/abc");
        BaseResponse<String> stringBaseResponse = urlManager.getOriginUrl(request);
        Assert.isTrue(stringBaseResponse.getCode() == 200);

         request = new UrlRequest("http://127.0.0.1:80/abc");
         stringBaseResponse = urlManager.getOriginUrl(request);
        Assert.isTrue(stringBaseResponse.getCode() == 200);
    }


}