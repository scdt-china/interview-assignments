package com.domain;

import com.domain.controller.ShortDomainController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * @ClassName:ShortDomainControllerTest
 * @Description:TODO
 * @author: wangkui DJ009697
 * @date:2021/07/12 下午7:51
 * @version:1.0
 */
public class ShortDomainControllerTest extends BaseSpringBootTest {

    @Autowired
    private ShortDomainController shortDomainController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(shortDomainController).build();
    }

    @Test
    public void getShortUrlTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/domain/getShortUrl")
                .accept(MediaType.APPLICATION_JSON).param("longUrl", "http://www.sequoiacap.com"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        logger.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void getShortUrlByErrorLongUrlTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/domain/getShortUrl")
                .accept(MediaType.APPLICATION_JSON).param("longUrl", "www.sequoiacap.com"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        logger.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void getExitShortUrlTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/domain/getShortUrl")
                .accept(MediaType.APPLICATION_JSON).param("longUrl", "http://www.sequoiacap.com"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        logger.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void getLongUrlTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/domain/getShortUrl")
                .accept(MediaType.APPLICATION_JSON).param("longUrl", "www.sequoiacap.com"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/domain/getLongUrl")
                .accept(MediaType.APPLICATION_JSON).param("shortUrl", "http://t.cn/Uv63u2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        logger.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void getEmptyLongUrlTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/domain/getShortUrl")
                .accept(MediaType.APPLICATION_JSON).param("longUrl", "www.sequoiacap.com"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/domain/getLongUrl")
                .accept(MediaType.APPLICATION_JSON).param("shortUrl", "http://t.cn/Uv63sssu2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        logger.info(mvcResult.getResponse().getContentAsString());
    }
}
