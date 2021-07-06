package com.example.shorturl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
class ShortUrlApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGenerateShortUrl() throws Exception {
        String originUrl = "https://www.google.com";
        this.mockMvc
                .perform(post("/shorturl/generate").param("originUrl", originUrl))
                .andExpect(status().isOk());
    }

    @Test
    void testQueryByShortUrl() throws Exception {
        String originUrl = "https://www.baidu.com";
        MvcResult generateResult = this.mockMvc
                .perform(post("/shorturl/generate").param("originUrl", originUrl))
                .andReturn();
        String shortUrlGenerated = generateResult.getResponse().getContentAsString();
        MvcResult queryResult = this.mockMvc
                .perform(get("/shorturl/query").param("shortUrl", shortUrlGenerated))
                .andReturn();
        assertEquals(originUrl, queryResult.getResponse().getContentAsString());
    }
}
