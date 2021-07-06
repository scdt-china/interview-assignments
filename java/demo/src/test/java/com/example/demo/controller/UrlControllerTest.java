package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.common.CommonUtil;
import com.example.demo.common.Constants;
import com.example.demo.service.UrlService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UrlControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;
	@Autowired
	private UrlService urlService;

	@Test
	public void urlTransferToShortHttpsRight() {
		String testUrl = "https://news.cctv.com/2021/07/01/ARTIzRVUFD9ycNjuaGjJFEv8210701.shtml";
		String key = CommonUtil.createKey(testUrl);

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("url", testUrl);
		ResponseEntity<String> result = restTemplate.getForEntity("/toShort?url={url}", String.class, paramMap);
		Assert.assertTrue(StringUtils.join(Constants.BASE_DOMAIN, key).equals(result.getBody()));
	}

	@Test
	public void urlTransferToShortNoParams() {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("url", "");
		ResponseEntity<String> result = restTemplate.getForEntity("/toShort?url={url}", String.class, paramMap);
		Assert.assertTrue(Constants.ErrorCode.URL_EMPTY.getMsg().equals(result.getBody()));
	}

	@Test
	public void urlTransferToShortNoParam() {
		ResponseEntity<String> result = restTemplate.getForEntity("/toShort", String.class);
		Assert.assertTrue(Constants.ErrorCode.SYSTEM_ERROR.getMsg().equals(result.getBody()));
	}

	@Test
	public void urlTransferToShortRightUrl() {
		String testUrl = "news.cctv.com/2021/07/01/ARTIzRVUFD9ycNjuaGjJFEv8210701.shtml";
		String key = CommonUtil.createKey(testUrl);

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("url", testUrl);
		ResponseEntity<String> result = restTemplate.getForEntity("/toShort?url={url}", String.class, paramMap);
		Assert.assertTrue(StringUtils.join(Constants.BASE_DOMAIN, key).equals(result.getBody()));
	}

	@Test
	public void urlTransferToShortErrorUrl() {
		String testUrl = "你好.com/2021/07/01/ARTIzRVUFD9ycNjuaGjJFEv8210701.shtml";

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("url", testUrl);
		ResponseEntity<String> result = restTemplate.getForEntity("/toShort?url={url}", String.class, paramMap);
		Assert.assertTrue(Constants.ErrorCode.URL_NOT_FORMAT.getMsg().equals(result.getBody()));
	}


	// 短链接换长链接
	@Test
	public void urlTransferToNormalTest() {
		String url = "news.cctv.com/2021/07/01/ARTIzRVUFD9ycNjuaGjJFEv8210701.shtml";
		String shortUrl = urlService.getShortUrl(url);

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("url", shortUrl);
		ResponseEntity<String> result = restTemplate.getForEntity("/toNormal?url={url}", String.class, paramMap);
		Assert.assertTrue(url.equals(result.getBody()));
	}

	@Test
	public void urlTransferToNormalNotExistTest() {
		String url = "https://www.t.tt/fnl14jch";

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("url", url);
		ResponseEntity<String> result = restTemplate.getForEntity("/toNormal?url={url}", String.class, paramMap);
		Assert.assertTrue(Constants.ErrorCode.URL_MAPPING_NOT_EXIST.getMsg().equals(result.getBody()));
	}

	@Test
	public void urlTransferToNormalKeyLengthTest() {
		String url = "https://www.t.tt/fnl14jchracasgrvd";

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("url", url);
		ResponseEntity<String> result = restTemplate.getForEntity("/toNormal?url={url}", String.class, paramMap);
		Assert.assertTrue(Constants.ErrorCode.URL_NOT_FORMAT.getMsg().equals(result.getBody()));
	}

	@Test
	public void urlTransferToNormalDomainTest() {
		String url = "https://www.baidu.com/fnl14jch";

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("url", url);
		ResponseEntity<String> result = restTemplate.getForEntity("/toNormal?url={url}", String.class, paramMap);
		Assert.assertTrue(Constants.ErrorCode.URL_NOT_FORMAT.getMsg().equals(result.getBody()));
	}

}
