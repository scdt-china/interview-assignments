package com.sequoia.shorturl.controller;


import com.sequoia.shorturl.common.ResponseResult;import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.junit.Before;
import org.junit.Test;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.Assert.assertEquals;

/**
 *
 * ShortUrlController 测试类
 *
 * @Author xj
 *
 * @Date 2021/6/30
 *
 * @version v1.0.0
 *
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ShortUrlControllerTest  {

    @Autowired
    private ShortUrlController shortUrlController;
    private MockMvc mockMvc;

    @Before
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(shortUrlController).build();
    }

     /**
     *
     * 根据传入请求url，返回对应的短域名
     * RESTful接口
     */
    @Test
    public void shortUrlTest() throws Exception
    {
       // 测试 转换成短域名接口
        String originUrl="https://zhuanlan.zhihu.com/p/194199097";

        MultiValueMap<String, String> params = new LinkedMultiValueMap();
        params.add("originalUrl",originUrl);
        MockHttpServletRequestBuilder params1 = MockMvcRequestBuilders.post("/createShortUrl").params(params);
        MvcResult mvcResult = mockMvc.perform(params1)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        System.out.println(contentAsString);
        ResponseResult respons=new ObjectMapper().readValue(contentAsString,ResponseResult.class);
        //测试 根据短域名获取 原域名接口
        MultiValueMap<String, String> params2 = new LinkedMultiValueMap();
        params2.add("shortUrl",respons.getData().toString());
        MockHttpServletRequestBuilder params3 = MockMvcRequestBuilders.get("/getOriginalUrl").params(params2);
        MvcResult mvcResult1 = mockMvc.perform(params3)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        String contentAsString1 = mvcResult1.getResponse().getContentAsString();
        System.out.println(contentAsString1);
        ResponseResult respons2=new ObjectMapper().readValue(contentAsString1,ResponseResult.class);
        //是否一致
        assertEquals(originUrl,respons2.getData().toString());

    }




}
