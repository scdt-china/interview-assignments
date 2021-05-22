package com.example.shorturl.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@DisplayName("ID生成雪花算法测试")
class SnowFlakeTest {

	@Test
	@DisplayName("ID生成测试")
	void genId() throws Exception {
		int testNum = 1000000;
		long[] idArray = new long[testNum];
		SnowFlake[] snowFlakeArray = new SnowFlake[(int) SnowFlake.MAX_SHARED_ID + 1];
		for (int i = 0; i < snowFlakeArray.length; i++) {
			snowFlakeArray[i] = new SnowFlake(i);
		}
		ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10, 0L,
				TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

		for (int i = 0; i < testNum; i++) {
			final int num = i;
			executor.execute(() -> {
				int sharedId = num & (int) SnowFlake.MAX_SHARED_ID;
				SnowFlake snowFlake = snowFlakeArray[sharedId];
				long id = snowFlake.genId();
				assertEquals((int) (id >> SnowFlake.SHARED_ID_SHIFT_COUNT & SnowFlake.MAX_SHARED_ID), sharedId);
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

	@Test
	@DisplayName("SnowFlake异常测试")
	void snowFlackeTest() {
		assertThrows(Exception.class, () -> new SnowFlake(-1));
		assertThrows(Exception.class, () -> new SnowFlake(SnowFlake.MAX_SHARED_ID + 1));
	}
}
