package com.example.shorturl.controller;

import com.example.shorturl.service.ShortUrlService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.UnsupportedEncodingException;

@WebFluxTest(controllers = ShortUrlController.class)
@DisplayName("短域名controller测试")
class ShortUrlControllerTest {
	@Autowired
	WebTestClient webTestClient;

	@MockBean
	ShortUrlService shortUrlService;

	@Test
	void shortToLang() throws UnsupportedEncodingException {
		String longUrl = "http://www.baidu.com/xxx/yyy.html";
		String shortUrl = "http://localhost:8080/Tfx2Fx3";
		Mockito.when(shortUrlService.shortToLong(shortUrl)).thenReturn(longUrl);

		webTestClient.post()
				.uri("/api/url/stl/{shortUrl}", shortUrl)
				.contentType(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk();
		Mockito.verify(shortUrlService).shortToLong(shortUrl);
	}

	@Test
	void longToShort() {
		String longUrl = "http://www.baidu.com/xxx/yyy.html";
		String shortUrl = "http://localhost:8080/Tfx2Fx3";
		Mockito.when(shortUrlService.longToShort(longUrl)).thenReturn(shortUrl);

		webTestClient.post()
				.uri("/api/url/lts/{longUrl}", longUrl)
				.contentType(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk();
		Mockito.verify(shortUrlService).longToShort(longUrl);
	}
}
