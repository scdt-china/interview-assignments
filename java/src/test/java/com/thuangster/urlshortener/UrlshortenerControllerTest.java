package com.thuangster.urlshortener;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesRegex;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.thuangster.urlshortener.services.UrlshortenerService;

@SpringBootTest
@AutoConfigureMockMvc
public class UrlshortenerControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private UrlshortenerService urlshortenerService;

	@Test
	public void getHello() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content()
						.string(equalTo("Welcome to Url shortener service!")));
	}

	@Test
	public void testEncodeCreated() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.post("/api/encode")
				.accept(MediaType.APPLICATION_JSON)
				.content("https://github.com")
				.contentType(MediaType.APPLICATION_JSON);
		mvc.perform(request).andExpect(status().isCreated()).andReturn();
	}

	@Test
	public void testEncodeResponseSize() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.post("/api/encode")
				.accept(MediaType.APPLICATION_JSON)
				.content("https://github.com")
				.contentType(MediaType.APPLICATION_JSON);
		mvc.perform(request).andExpect(status().isCreated())
				.andExpect(content().string(matchesRegex("^[a-zA-Z0-9]{8}$")));
	}

	@Test
	public void testEncodeEmpty() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.post("/api/encode")
				.accept(MediaType.APPLICATION_JSON).content("");
		mvc.perform(request).andExpect(status().isBadRequest()).andReturn();
	}

	@Test
	public void testDecodeBadCode() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/api/{shortUrl}", "abc")
				.accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isUnprocessableEntity());
	}

	@Test
	public void testDecodeNotFound() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/api/{shortUrl}", "abcdefgh")
				.accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isNotFound());
	}

	@Test
	public void testDecode() throws Exception {
		String longUrl = "https://github.com";
		String shortUrl = urlshortenerService.encode(longUrl);

		mvc.perform(MockMvcRequestBuilders.get("/api/{shortUrl}", shortUrl)
				.accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(equalTo(longUrl)));

	}
}
