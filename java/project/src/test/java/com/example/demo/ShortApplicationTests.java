package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ShortApplicationTests {

	@Autowired
	private ShortController shortController;

	@Autowired
	private ShortService shortService;

	@Test
	public void contextLoads() throws Exception {
		assertThat(shortController).isNotNull();
		assertThat(shortService).isNotNull();
	}
}