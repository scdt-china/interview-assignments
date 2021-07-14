package com.fh.shorturl.controller;



import com.fh.shorturl.ShorturlApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static java.net.URLEncoder.encode;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShorturlApplication.class)
@WebAppConfiguration // 开启web应用配置
@AutoConfigureMockMvc
public class ShortURLControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        //MockMvcBuilders.webAppContextSetup(WebApplicationContext context)：指定WebApplicationContext，将会从该上下文获取相应的控制器并得到相应的MockMvc；
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    @Test
    public void addShortURL() throws Exception {
        // 将普通字符创转换成application/x-www-from-urlencoded字符串
        String urlString = encode("https://www.baidu.com", "utf-8");  //输出%E4%BD%A0%E5%A5%BD
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/url/" + urlString)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(MockMvcResultMatchers.status().isOk()); // 用于判断返回的期望值
//                .andExpect(MockMvcResultMatchers.content().string(equalTo("{\\\"success\\\":true,\\\"data\\\":\\\"MFfIju\\\",\\\"errCode\\\":8200,\\\"message\\\":\\\"操作成功\\\"}")));
    }
}