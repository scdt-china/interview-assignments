package com.url.transcoding;

import com.url.transcoding.service.UrlTranscodingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UrlTranscodingApplicationTests {

    @Autowired
    private UrlTranscodingService urlTranscodingService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void longToShort() throws Exception{
        mockMvc.perform(post("/longToShort?longUrl=http://www.baidu.com/aaa?vvv=ccc")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"code\":200")));
    }

    @Test
    void shortToLong() throws Exception {

        String longUrl = "http://www.baidu.com/aaa?vvv=ccc";
        String shortUrl = urlTranscodingService.longToShort(longUrl);
        assertNotEquals(longUrl, shortUrl);
        assertEquals(longUrl,urlTranscodingService.shortToLong(shortUrl));

        mockMvc.perform(get("/shortToLong/" + shortUrl)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"code\":200")));

        mockMvc.perform(get("/shortToLong/fdsah")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"code\":408")));
    }

}
