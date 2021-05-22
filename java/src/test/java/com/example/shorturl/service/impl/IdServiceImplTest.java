package com.example.shorturl.service.impl;

import com.example.shorturl.utils.CommonUtils;
import com.example.shorturl.utils.SnowFlake;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DisplayName("ID生成服务测试")
class IdServiceImplTest {
	@Autowired
	private IdServiceImpl idService;

	/**
	 * 并发大量，生成ID不能重复
	 */
	@Test
	@DisplayName("ID多线程分片生成测试")
	void genId() throws InterruptedException {
		int testNum = 1000000;
		long[] idArray = new long[testNum];
		ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10, 0L,
				TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

		for (int i = 0; i < testNum; i++) {
			final int num = i;
			executor.execute(() -> {
				int sharedId = num & (CommonUtils.URL_DB_SHARED_NUM - 1);
				long id = idService.genId(sharedId);
				assertEquals((int) (id >> SnowFlake.SHARED_ID_SHIFT_COUNT & (CommonUtils.URL_DB_SHARED_NUM - 1)),
						sharedId);
				idArray[num] = id;
			});
		}
		executor.shutdown();
		executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		//验证有没有重复
		HashSet<Long> set = new HashSet<>(idArray.length);
		for (int i = 0; i < idArray.length; i++) {
			set.add(idArray[i]);
		}
		assertEquals(set.size(), idArray.length);
	}
}
