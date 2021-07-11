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

@Slf4j
@SpringBootTest
@WebAppConfiguration
class UrlControllerTest {

    private MockMvc mockMvc;
    @Resource
    private WebApplicationContext webApplicationContext;
    @Resource
    private ShortUrlServiceImpl shortUrlService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void redirect() throws Exception {
        // 模拟短网址不存在的情况
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/s/1A2D3D")).andReturn().getResponse();
        log.info("状态码：{}", response.getStatus());
        log.info("重定向链接：{}", response.getHeader("Location"));
        // 模拟短网址存在的情况
        String shortUrl = shortUrlService.encode("https://github.com/scdt-china/interview-assignments/tree/master/java");
        response = mockMvc.perform(MockMvcRequestBuilders.get("/s/" + shortUrl)).andReturn().getResponse();
        log.info("状态码：{}", response.getStatus());
        log.info("重定向链接：{}", response.getHeader("Location"));
    }
}