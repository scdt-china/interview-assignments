package com.example.shortUrl;

import com.example.shortUrl.utils.ShortUrlGenerator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@Slf4j
@SpringBootTest(classes = ShortUrlApplication.class)
public class ShortUrlApplicationTests {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext wac;

    @Test
    void contextLoads() {
    }

    @Test
    void testGenerator() {
        String longUrl = "https://github.com/sqskg/interview-assignments";
        log.info("short url = {}", ShortUrlGenerator.shortCodeGenerate(longUrl));
        //short url = VjzMBFIj
        String illegalUrl = "www.baidu.com";
        log.info("short url = {}", ShortUrlGenerator.shortCodeGenerate(illegalUrl));
    }

    @Test
    public void generate() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build(); //初始化MockMvc对象
		String longUrl = "https://github.com/sqskg/interview-assignments";
        String illegalUrl = "www.baidu.com";
		String uri = "/shortUrl/generate";
        ResultActions actions = mvc
                .perform(MockMvcRequestBuilders.post(uri).characterEncoding("UTF-8")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(longUrl));
		actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
		actions.andExpect(
				MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print());

		actions = mvc
                .perform(MockMvcRequestBuilders.post(uri).characterEncoding("UTF-8")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(illegalUrl));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(
                MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void resolve() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build(); //初始化MockMvc对象
        String uri = "/shortUrl/resolve";
        String shortUrl = "https://tk.vip/M72VRb6Z";
        ResultActions actions = mvc
                .perform(MockMvcRequestBuilders.get(uri).characterEncoding("UTF-8")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("shortUrl",shortUrl));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(
                MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
