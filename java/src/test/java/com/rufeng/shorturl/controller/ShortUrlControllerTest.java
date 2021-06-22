package com.rufeng.shorturl.controller;

import com.alibaba.fastjson.JSONPath;
import com.rufeng.shorturl.enums.ErrorCode;
import com.rufeng.shorturl.service.ShortUrlService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author qujingguo
 * @version 1.0.0
 * @date 2021/5/27 11:18 上午
 * @description 短域名服务controller
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest(ShortUrlController.class)
@DisplayName("controller测试")
@Slf4j
public class ShortUrlControllerTest {
    @Autowired
    private MockMvc mvc;
    @Resource
    private ShortUrlService shortUrlService;

    @Test
    @DisplayName("短域名存储接口")
    public void longToShort() throws Exception {
        //准备请求url
        String longUrl = "http://www.baidu.com";
        String url = "/api/url/longToShort?longUrl=" + longUrl;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(url)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value("http://rf.cn/3Spuec"))
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        log.info(result);

    }


    @Test
    @DisplayName("短域名读取接口测试")
    public void shortToLang() throws Exception {
        String longUrl = "http://www.baidu.com";
        String shortUrl = "http://rf.cn/3Spuec";
        shortUrlService.longToShort(longUrl);
        //准备请求url
        String url = "/api/url/shortToLang?shortUrl=" + shortUrl;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(url)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        log.info(mvcResult.getResponse().getContentAsString());
    }


    @Test
    @DisplayName("测试限流")
    public void longToShortLimit() throws InterruptedException {
        String longUrl = "http://www.baidu.com";
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        long[] idArray = new long[10000];
        for (int i = 0; i < 10000; i++) {
            final int num = i;
            executorService.execute(() -> {
                //准备请求url
                String url = "/api/url/longToShort?longUrl=" + longUrl;
                try {
                    MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(url)
                            .accept(MediaType.APPLICATION_JSON))
                            .andExpect(status().isOk())
                            .andReturn();
                    String body = mvcResult.getResponse().getContentAsString();
                    Object code = JSONPath.eval(body, "$.code");
                    log.info("code：{}", code);
                    if (Objects.equals(code, ErrorCode.LIMIT.getCode())) {
                        idArray[num] = num;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        int limitNum = 0;
        for (long l : idArray) {
            if (l > 0) {
                limitNum = limitNum + 1;
            }
        }
        log.info("限流数：{}", limitNum);

    }


}
