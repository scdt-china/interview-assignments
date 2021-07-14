package com.thuangster.urlshortener;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URL;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UrlshortenerControllerIntegrationTest {
	@LocalServerPort
	private int port;

	private URL base;

	@Autowired
	private TestRestTemplate template;

	@BeforeEach
	public void setUp() throws Exception {
		this.base = new URL("http://localhost:" + port + "/");
	}

	// Test class added ONLY to cover main() invocation.
	@Test
	public void main() {
		UrlshortenerApplication.main(new String[] {});
	}

	@Test
	public void getHello() throws Exception {
		ResponseEntity<String> response = template.getForEntity(base.toString(),
				String.class);
		assertThat(response.getBody())
				.isEqualTo("Welcome to Url shortener service!");
	}

	@Test
	public void testEncode() throws Exception {
		String testUrl = base.toString() + "api/encode";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String longUrl = "https://github.com";
		HttpEntity<String> requestEntity = new HttpEntity<>(longUrl, headers);

		ResponseEntity<String> response = template.postForEntity(testUrl,
				requestEntity, String.class);
		assertThat(response.getBody()).matches("^[a-zA-Z0-9]{8}$");
	}
}
