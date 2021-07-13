package com.tzg157.demo.test.controller;

import com.tzg157.demo.DemoTestBase;
import com.tzg157.demo.controller.DomainController;
import com.tzg157.demo.model.Request;
import com.tzg157.demo.model.Response;
import com.tzg157.demo.model.UrlResult;
import com.tzg157.demo.service.DomainConvertService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.annotation.Resource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class DomainControllerTest extends DemoTestBase {
    @Resource
    private DomainController domainController;

    @Resource
    private MockMvc mockMvc;

    @MockBean
    private DomainConvertService domainConvertService;

    /**
     * 结果校验
     */
    @Test
    public void testLongDomainToShortDomain() throws Exception {
        Request request = new Request();
        request.setUrl(LONG_REQUEST_URL);

        Response<UrlResult> response = new Response<>();
        response.setCode(Response.ResponseCode.SUCCESSOR.getCode());
        response.setMessage("OK");
        response.setResult(new UrlResult(SHORT_REQUEST_URL));

        String content = convertToJsonString(request);
        Mockito.when(domainConvertService.convertToShort(LONG_REQUEST_URL)).thenReturn(response);
        MvcResult result = this.mockMvc.perform(post("/domain/long2Short")
                .contentType(MediaType.APPLICATION_JSON).content(content))
                .andDo(print())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        Assertions.assertEquals("{\"code\":0,\"message\":\"OK\",\"result\":{\"url\":\"https://scdt.cn/1\"}}",responseContent);
    }

    @Test
    public void testLongDomainToShortDomainFail() throws Exception {
        Request request = new Request();
        request.setUrl(LONG_REQUEST_URL);

        String content = convertToJsonString(request);
        Mockito.when(domainConvertService.convertToShort(LONG_REQUEST_URL)).thenThrow(new RuntimeException("test throw exception"));
        MvcResult result = this.mockMvc.perform(post("/domain/long2Short")
                .contentType(MediaType.APPLICATION_JSON).content(content))
                .andDo(print())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        Assertions.assertEquals("{\"code\":1,\"message\":\"Fail\",\"result\":null}",responseContent);
    }

    /**
     * 请求参数校验 400
     */
    @Test
    public void testLongDomainToShortDomainBadRequest() throws Exception {
        String content = "";
        MvcResult result = this.mockMvc.perform(post("/domain/long2Short")
                .contentType("application/json").content(content))
            .andDo(print())
            .andExpect(status().is4xxClientError())
            .andReturn();
        Assertions.assertEquals(400,result.getResponse().getStatus());
    }

    @Test
    public void testShortDomainToLongDomain() throws Exception {
        Request request = new Request();
        request.setUrl(SHORT_REQUEST_URL);

        Response<UrlResult> response = new Response<>();
        response.setCode(Response.ResponseCode.SUCCESSOR.getCode());
        response.setMessage("OK");
        response.setResult(new UrlResult(LONG_REQUEST_URL));

        String content = convertToJsonString(request);
        Mockito.when(domainConvertService.convertToLong(SHORT_REQUEST_URL)).thenReturn(response);
        MvcResult result = this.mockMvc.perform(post("/domain/short2Long")
                .contentType(MediaType.APPLICATION_JSON).content(content))
                .andDo(print())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        Assertions.assertEquals("{\"code\":0,\"message\":\"OK\",\"result\":{\"url\":\"https://www.baidu.com\"}}",responseContent);
    }

    @Test
    public void testShortDomainToLongDomainFail() throws Exception {
        Request request = new Request();
        request.setUrl(SHORT_REQUEST_URL);

        String content = convertToJsonString(request);
        Mockito.when(domainConvertService.convertToLong(SHORT_REQUEST_URL)).thenThrow(new RuntimeException("test throw exception"));
        MvcResult result = this.mockMvc.perform(post("/domain/short2Long")
                .contentType(MediaType.APPLICATION_JSON).content(content))
                .andDo(print())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        Assertions.assertEquals("{\"code\":1,\"message\":\"Fail\",\"result\":null}",responseContent);
    }

    @Test
    public void testShortDomainToLongDomainBadRequest() throws Exception {
        String content = "";
        MvcResult result = this.mockMvc.perform(post("/domain/short2Long")
                .contentType("application/json").content(content))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andReturn();
        Assertions.assertEquals(400,result.getResponse().getStatus());
    }
}
