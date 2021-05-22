package com.example.shorturl.repository;

import com.example.shorturl.utils.CommonUtils;
import com.example.shorturl.utils.ConversionUtil;
import com.example.shorturl.utils.SnowFlake;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DisplayName("UrlRepository test")
class UrlRepositoryTest {
	@Autowired
	private UrlRepository urlRepository;

	@Test
	@DisplayName("从短地址查询长地址测试")
	void getLongUrl() throws Exception {
		String longUrl = "http://www.baiud.com/dsaf/dcfads.html";
		int shareId = CommonUtils.getShareIdByLongUrl(longUrl);
		long urlId = new SnowFlake(shareId).genId();
		String shortUrl = ConversionUtil.encode(urlId);
		urlRepository.putUrl(shareId, shortUrl, longUrl);
		assertEquals(urlRepository.getLongUrl(shortUrl), longUrl);
	}

	@Test
	@DisplayName("保存短地址和长地址的对应关系测试")
	void putUrl() throws Exception {
		String longUrl = "http://www.baiud.com/dsafads.html";
		int shareId = CommonUtils.getShareIdByLongUrl(longUrl);
		long urlId = new SnowFlake(shareId).genId();
		String shortUrl = ConversionUtil.encode(urlId);
		urlRepository.putUrl(shareId, shortUrl, longUrl);
		assertEquals(urlRepository.getShortUrl(longUrl), shortUrl);
		assertEquals(urlRepository.getLongUrl(shortUrl), longUrl);
	}

	@Test
	@DisplayName("从长地址查询短地址测试")
	void getShortUrl() throws Exception {
		String longUrl = "http://www.baiud.com/test.html";

		assertEquals(urlRepository.getShortUrl(longUrl), null);

		int shareId = CommonUtils.getShareIdByLongUrl(longUrl);
		long urlId = new SnowFlake(shareId).genId();
		String shortUrl = ConversionUtil.encode(urlId);
		urlRepository.putUrl(shareId, shortUrl, longUrl);
		assertEquals(urlRepository.getShortUrl(longUrl), shortUrl);
	}
}
