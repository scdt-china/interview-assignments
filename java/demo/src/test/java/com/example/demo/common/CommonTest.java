package com.example.demo.common;

import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommonTest {

	@Test
	public void urlMatchTest() {
		String testUrl = "news.cctv.com/2021/07/01/ARTIzRVUFD9ycNjuaGjJFEv8210701.shtml";
		Assert.assertTrue(Pattern.matches(Constants.URL_ROLE, testUrl));
	}

	@Test
	public void vueUrlMatchTest() {
		String testUrl = "http://localhost:3000/#/homePage";
		Assert.assertTrue(Pattern.matches(Constants.URL_ROLE, testUrl));
	}

	@Test
	public void urlNotContainHttpTest() {
		String testUrl = "localhost:3000/#/homePage";
		Assert.assertTrue(Pattern.matches(Constants.URL_ROLE, testUrl));
	}
}
