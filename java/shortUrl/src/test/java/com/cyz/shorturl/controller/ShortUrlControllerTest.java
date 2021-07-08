package com.cyz.shorturl.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class) //spring环境
@SpringBootTest  //springboot项目测试标签
@AutoConfigureMockMvc
class ShortUrlControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shorten() throws Exception {
        MvcResult mvcResult =
                 mockMvc.perform(MockMvcRequestBuilders.get("/url/shorten")
                .param("longUrl", "http://www.163.com"))
                .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        int status = response.getStatus();
        String contentAsString = response.getContentAsString();
        System.err.println(status);
        System.err.println(contentAsString);
    }

    @Test
    void original() throws Exception {
        MvcResult mvcResultShort =
                mockMvc.perform(MockMvcRequestBuilders.get("/url/shorten")
                        .param("longUrl", "http://www.163.com"))
                        .andReturn();
        MockHttpServletResponse responseShort = mvcResultShort.getResponse();
        String shortUrl = responseShort.getContentAsString();

        MvcResult mvcResultLong =
                mockMvc.perform(MockMvcRequestBuilders.get("/url/original")
                        .param("shortUrl", shortUrl))
                        .andReturn();
        MockHttpServletResponse responseLong = mvcResultLong.getResponse();
        int status = responseLong.getStatus();
        String contentAsString = responseLong.getContentAsString();
        System.err.println(status);
        System.err.println(contentAsString);
    }
}