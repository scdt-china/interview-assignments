package com.example.shorturl.service.impl;

import com.example.shorturl.service.ShortUrlService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
@DisplayName("短域名服务测试")
class ShortUrlServiceImplTest {
	@Autowired
	private ShortUrlService shortUrlService;

	/**
	 * 同一个地址，并发重复，结果应该一致
	 */
	@Test
	@DisplayName("接受长域名信息，返回短域名信息测试")
	void longToShort() throws InterruptedException {
		String longUrl = "http://www.baidu.com/xdfa/dew.html";
		int testNum = 100000;
		String[] shortUrlArray = new String[testNum];
		ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10, 0L,
				TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

		for (int i = 0; i < testNum; i++) {
			final int num = i;
			executor.execute(() -> {
				String result = shortUrlService.longToShort(longUrl);
				assertNotEquals(result, null);
				shortUrlArray[num] = result;
			});
		}
		executor.shutdown();
		executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		for (int i = 1; i < shortUrlArray.length; i++) {
			assertEquals(shortUrlArray[i], shortUrlArray[i - 1]);
		}
	}

	@Test
	@DisplayName("接受短域名信息，返回长域名信息测试")
	void shortToLong() {
		String longUrl = "http://www.baidu.com/dafds/dts.html";
		String shortUrl = shortUrlService.longToShort(longUrl);
		assertEquals(shortUrlService.shortToLong(shortUrl), longUrl);

		assertEquals(shortUrlService.shortToLong("432dfa789#"), null);
	}
}
