package com.assignment.domain.api;

import com.assignment.domain.api.controller.DomainServiceApi;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class DomainServiceApiApplicationTests {
    String longDomain = "https://github.com/scdt-china/interview-assignments";
    String shortDomain = "https://s.domain/fitptc";

    @Autowired
    DomainServiceApi domainServiceApi;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contextLoads() throws Exception {
        assertThat(domainServiceApi).isNotNull();
        givenLongDomainSaveAndReplyThenReplyCorrected();
    }

    @Test
    void givenLongDomainSaveAndReplyThenReplyCorrected() throws Exception {
        mockMvc.perform(put("/domain/v1").content(longDomain).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.msg").value("OK"))
                .andExpect(jsonPath("$.payload").value(shortDomain));
    }

    @Test
    void givenShortDomainReadLongDomainThenReplyCorrected() throws Exception {
        mockMvc.perform(post("/domain/v1/long").content(shortDomain))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.payload").value(longDomain));
    }

    @Test
    void givenNoExistsShortDomainReadThenReplyFailure() throws Exception {
        mockMvc.perform(post("/domain/v1/long").content(shortDomain + "wr"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false));
    }

}
