package com.assignment.domain.web;

import com.assignment.domain.DomainApplicationTests;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @Author: zhangruiqi03
 * @Date: 2021/6/29 12:47 AM
 */

@AutoConfigureMockMvc
class UrlCtrlTest extends DomainApplicationTests {


    @Test
    public void shortUrl() throws Exception {

        getMockMvc().perform(MockMvcRequestBuilders.get("/short/dfdfucvbdgoerehtrengg").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }


    @Test
    void originUrl() throws Exception {
        getMockMvc().perform(MockMvcRequestBuilders.get("/short/dfdfucvbdgoerehtrengg").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        getMockMvc().perform(MockMvcRequestBuilders.get("/origin/000000G9").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        getMockMvc().perform(MockMvcRequestBuilders.get("/origin/00000009").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}