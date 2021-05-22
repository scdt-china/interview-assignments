package com.example.shorturl.utils;

import com.example.shorturl.tools.RandomUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("通用工具类测试")
class CommonUtilsTest {
	private static final Random RANDOM = new Random();

	@Test
	@DisplayName("根据短域名地址10进制值获取分片Id测试")
	void getShareIdByUrlId() throws Exception {
		assertEquals(CommonUtils.getShareIdByUrlId(new SnowFlake(0).genId()), 0);
		assertEquals(CommonUtils.getShareIdByUrlId(new SnowFlake(CommonUtils.URL_DB_SHARED_NUM - 1).genId()),
				CommonUtils.URL_DB_SHARED_NUM - 1);
		assertEquals(CommonUtils.getShareIdByUrlId(new SnowFlake(CommonUtils.URL_DB_SHARED_NUM).genId()),
				-1);
		for (int i = 0; i < 10; i++) {
			int sharedId = RANDOM.nextInt(CommonUtils.URL_DB_SHARED_NUM);
			SnowFlake snowFlake = new SnowFlake(sharedId);
			assertEquals(CommonUtils.getShareIdByUrlId(snowFlake.genId()), sharedId);
		}
	}

	/**
	 * 简单跑一下，和getShareIdByUrlId差不多
	 *
	 * @throws Exception
	 */
	@Test
	@DisplayName("根据短域名地址获取分片Id测试")
	void getShareIdByShortUrl() throws Exception {
		for (int i = 0; i < 10; i++) {
			int sharedId = RANDOM.nextInt(CommonUtils.URL_DB_SHARED_NUM);
			SnowFlake snowFlake = new SnowFlake(sharedId);
			String shortUrl = ConversionUtil.encode(snowFlake.genId());
			assertEquals(CommonUtils.getShareIdByShortUrl(shortUrl), sharedId);
		}
	}

	@Test
	@DisplayName("根据url计算分片Id测试")
	void getShareIdByLongUrl() {
		assertEquals(CommonUtils.getShareIdByLongUrl(null), -1);
		for (int i = 0; i < 10000; i++) {
			int sharedId = CommonUtils.getShareIdByLongUrl(
					RandomUtils.generateString(RANDOM.nextInt(256)));
			assertTrue(sharedId >= 0 && sharedId < CommonUtils.URL_DB_SHARED_NUM);
		}
	}

	@Test
	@DisplayName("字符串空测试")
	void isBlank() {
		assertTrue(CommonUtils.isBlank(null));
		assertTrue(CommonUtils.isBlank(""));
		assertTrue(CommonUtils.isBlank(" "));
		assertTrue(!CommonUtils.isBlank("s s"));
		assertTrue(!CommonUtils.isBlank(" ss "));
	}
}
