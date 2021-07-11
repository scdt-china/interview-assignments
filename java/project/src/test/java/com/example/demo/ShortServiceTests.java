package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ContextConfiguration(classes = ShortService.class)
public class ShortServiceTests {

	@Autowired
	private ShortService shortService;

	@Test
	public void testShortNameNull() throws Exception {
		assertThat(shortService.shortName(null)).isEqualTo("");
	}

	@Test
	public void testShortNameEmpty() throws Exception {
		assertThat(shortService.shortName("")).isEqualTo("");
	}

	@Test
	public void testShortNameValid() throws Exception {
		// new
		String first = shortService.shortName("test");
		assertThat(first).isNotNull();
		assertThat(first).isNotEqualTo("");
		// existing
		String second = shortService.shortName("test");
		assertThat(second).isEqualTo(first);
	}

	@Test
	public void testLongNameNull() throws Exception {
		assertThat(shortService.longName(null)).isEqualTo("");
	}

	@Test
	public void testLongNameEmpty() throws Exception {
		assertThat(shortService.longName("")).isEqualTo("");
	}

	@Test
	public void testLongNameValid() throws Exception {
		assertThat(shortService.longName("test")).isNotNull();
	}
}