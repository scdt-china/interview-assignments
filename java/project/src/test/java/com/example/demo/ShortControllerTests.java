package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShortController.class)
public class ShortControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ShortService shortService;

	@Test
	public void testWrite() throws Exception {
		when(shortService.shortName(anyString())).thenReturn("short");
		mockMvc.perform(get("/w/long")).andExpect(status().isOk())
			.andExpect(content().string(containsString("short")));
	}

	@Test
	public void testRead() throws Exception {
		when(shortService.longName(anyString())).thenReturn("long");
		mockMvc.perform(get("/r/short")).andExpect(status().isOk())
			.andExpect(content().string(containsString("long")));
	}
}