package com.assignments.url.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UrlTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void test() throws Exception {

		String url = "www.baidu.com";
		String json = "{\"longUrl\":\""+url+"\"}";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/url").accept(MediaType.APPLICATION_JSON)
				.content(json).contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String shortUrl = result.getResponse().getContentAsString();
		
		log.info("短域名："+shortUrl);
		
		RequestBuilder get = MockMvcRequestBuilders.get("/url/"+shortUrl);
		result = mockMvc.perform(get).andReturn();
		
		log.info("长域名："+result.getResponse().getContentAsString());
		
	}
}
