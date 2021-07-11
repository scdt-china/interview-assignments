package com.example.url.controller;

import com.example.url.service.impl.ShortUrlServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

@Slf4j
@SpringBootTest
@WebAppConfiguration
class ShortUrlControllerTest {

    private MockMvc mockMvc;
    @Resource
    private WebApplicationContext webApplicationContext;
    @Resource
    private ShortUrlServiceImpl shortUrlService;
    @Resource
    private ShortUrlController shortUrlController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void encode() throws Exception {
        // 模拟参数正确情况
        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.post("/shortUrl/encode").content("https://github.com/scdt-china/interview-assignments/tree/master/java")
        ).andReturn().getResponse();
        log.info("请求结果：{}", response.getContentAsString(StandardCharsets.UTF_8));
        // 模拟参数不是正确的URL情况
        response = mockMvc.perform(
                MockMvcRequestBuilders.post("/shortUrl/encode").content("test")
        ).andReturn().getResponse();
        log.info("请求结果：{}", response.getContentAsString(StandardCharsets.UTF_8));
        // 模拟内存不够的情况
        shortUrlController.memoryAvailable.clear();
        response = mockMvc.perform(
                MockMvcRequestBuilders.post("/shortUrl/encode").content("https://github.com/scdt-china/interview-assignments/tree/master/php")
        ).andReturn().getResponse();
        log.info("请求结果：{}", response.getContentAsString(StandardCharsets.UTF_8));
    }

    @Test
    void decode() throws Exception {
        // 模拟短地址不存在的情况
        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.post("/shortUrl/decode").content("1A2S3D")
        ).andReturn().getResponse();
        log.info("请求结果：{}", response.getContentAsString(StandardCharsets.UTF_8));
        // 模拟短地址存在的情况
        String shortUrl = shortUrlService.encode("https://github.com/scdt-china/interview-assignments/tree/master/java");
        response = mockMvc.perform(
                MockMvcRequestBuilders.post("/shortUrl/decode").content(shortUrl)
        ).andReturn().getResponse();
        log.info("请求结果：{}", response.getContentAsString(StandardCharsets.UTF_8));
    }
}