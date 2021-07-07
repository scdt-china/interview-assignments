package com.yang.shorturl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yang.shorturl.dto.ResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.Assert;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class ShortUrlControllerTest {

    private MockMvc mockMvc;

    @Autowired
    public void setMockMvc(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    void testShortUrlController() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String testUrl = "http://www.baidu.com";
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/api/url/getShortUrl")
                        .param("url", testUrl)).
                        andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String content = response.getContentAsString();
        ResultDTO<String> resultDTO = objectMapper.readValue(content, ResultDTO.class);
        Assert.isTrue(resultDTO.getCode() == 200, "请求生成短地址不成功" + resultDTO.getMessage());
        String shortUrl = resultDTO.getData();
        mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/api/url/getLongUrl")
                        .param("shortUrl", shortUrl)).
                        andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        response = mvcResult.getResponse();
        content = response.getContentAsString();
        resultDTO = objectMapper.readValue(content, ResultDTO.class);
        Assert.isTrue(resultDTO.getCode() == 200, "请求短地址反查长地址不成功" + resultDTO.getMessage());
        Assert.isTrue(testUrl.equals(resultDTO.getData()), "短地址反查长地址不匹配");
    }

    @Test
    void testNoResult() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/api/url/getLongUrl")
                        .param("shortUrl", "tete")).
                        andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        response.setCharacterEncoding("UTF-8");
        String content = response.getContentAsString();
        ResultDTO<String> resultDTO = objectMapper.readValue(content, ResultDTO.class);
        Assert.isTrue(resultDTO.getCode() == 500, "请求短地址反查长地址没有返回期望的错误码");
    }
}
