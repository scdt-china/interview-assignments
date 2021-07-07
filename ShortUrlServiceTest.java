package com.zdkj;

import com.zdkj.modler.shorturl.controller.ShortUrlController;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author bihuiwen
 * @version 1.0
 * @description: TODO
 * @date 2021/7/6 下午4:58
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShortUrlServiceTest {

    @Autowired
    WebApplicationContext wac;//模拟ServletContext环境
    MockMvc mockMvc;//声明MockMvc对象

    @Before
    public void before() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testSaveParam1() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/shortUrl/save")//get请求方法
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)//请求内容类型
                        .param("longUrl","287279")
                        .accept(MediaType.APPLICATION_JSON_UTF8))//参数
                .andExpect(MockMvcResultMatchers.status().isOk())//期望返回状态200
                // .andDo(MockMvcResultHandlers.print())//指定打印信息
                .andReturn();//返回值
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testSaveParam2() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/shortUrl/save")//get请求方法
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)//请求内容类型
                        .param("termOfValidity","10")
                        .accept(MediaType.APPLICATION_JSON_UTF8))//参数
                .andExpect(MockMvcResultMatchers.status().isOk())//期望返回状态200
                //  .andDo(MockMvcResultHandlers.print())//指定打印信息
                .andReturn();//返回值
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testSave() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/shortUrl/save")//get请求方法
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)//请求内容类型
                        .param("termOfValidity","-2")
                        .param("longUrl","http://saashd.com/dajk")
                        .accept(MediaType.APPLICATION_JSON_UTF8))//参数
                .andExpect(MockMvcResultMatchers.status().isOk())//期望返回状态200
                //  .andDo(MockMvcResultHandlers.print())//指定打印信息
                .andReturn();//返回值
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testSave1() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/shortUrl/save")//get请求方法
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)//请求内容类型
                        .param("termOfValidity","1")
                        .param("longUrl","http://saashd.com/dajk")
                        .accept(MediaType.APPLICATION_JSON_UTF8))//参数
                .andExpect(MockMvcResultMatchers.status().isOk())//期望返回状态200
                //  .andDo(MockMvcResultHandlers.print())//指定打印信息
                .andReturn();//返回值
        System.out.println(mvcResult.getResponse().getContentAsString());
    }


    @Test
    public void testRead() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/shortUrl/read")//get请求方法
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)//请求内容类型

                        .param("shortUrl","http://t.com/2qq2U")
                        .accept(MediaType.APPLICATION_JSON_UTF8))//参数
                .andExpect(MockMvcResultMatchers.status().isOk())//期望返回状态200
                //  .andDo(MockMvcResultHandlers.print())//指定打印信息
                .andReturn();//返回值
        System.out.println(mvcResult.getResponse().getContentAsString());
    }


    @Test
    public void testRead1() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/shortUrl/read")//get请求方法
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)//请求内容类型

                        .param("shortUrl","http://st.com/2qq2U")
                        .accept(MediaType.APPLICATION_JSON_UTF8))//参数
                .andExpect(MockMvcResultMatchers.status().isOk())//期望返回状态200
                //  .andDo(MockMvcResultHandlers.print())//指定打印信息
                .andReturn();//返回值
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

}
