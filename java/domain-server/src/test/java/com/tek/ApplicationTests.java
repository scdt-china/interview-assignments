package com.tek;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.tek.rsp.JsonRsp;

@SpringBootTest
class ApplicationTests {

	private static final String getShortDomainUrl = "http://127.0.0.1:8080/domain/getShortDomain";

	private static final String getLongDomainUrl = "http://127.0.0.1:8080/domain/getLongDomain";

	public RestTemplate restTemplate = new RestTemplate();

	@Test
	void testGetShortDomain() throws Exception {

		for (int i = 2; i < 502; i++) {
			HttpHeaders header = new HttpHeaders();
			header.setContentType(MediaType.APPLICATION_JSON);
			HttpMethod method = HttpMethod.POST;

			Map<String, String> body = new HashMap<String, String>();
			body.put("domain", "http://www.baidu" + i + ".com");
			HttpEntity<Map<String, String>> httpEntity = new HttpEntity<Map<String, String>>(body, header);

			ResponseEntity<JsonRsp> sdResponse = restTemplate.postForEntity(getShortDomainUrl, httpEntity,
					JsonRsp.class);

			assertEquals("100000", sdResponse.getBody().getCode());
			System.out.println(sdResponse.getBody().getData());
		}
	}

	@Test
	void testGetLongDomain() throws Exception {

		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		HttpMethod method = HttpMethod.POST;

		Map<String, String> body = new HashMap<String, String>();
		body.put("domain", "e");
		HttpEntity<Map<String, String>> httpEntity = new HttpEntity<Map<String, String>>(body, header);

		ResponseEntity<JsonRsp> sdResponse = restTemplate.postForEntity(getLongDomainUrl, httpEntity, JsonRsp.class);

		assertEquals("100000", sdResponse.getBody().getCode());
		
		System.out.println(sdResponse.getBody().getData());
	}

}
