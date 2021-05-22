package com.example.shorturl.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("10<->62进制转换工具类测试")
class ConversionUtilTest {
	private static final Random RANDOM = new Random();

	@Test
	@DisplayName("10->62进制转换测试")
	void encode() {
		assertNull(ConversionUtil.encode(-1));
		assertEquals(ConversionUtil.encode(0), "0");
		assertEquals(ConversionUtil.encode(Long.MAX_VALUE), "AzL8n0Y58m7");

		for (int i = 0; i < 10; i++) {
			long num = RANDOM.nextLong();
			if (num < 0) {
				assertNull(ConversionUtil.encode(num));
			} else {
				assertNotNull(ConversionUtil.encode(num));
			}
		}
	}

	@Test
	@DisplayName("62->10进制转换测试")
	void decode() {
		assertEquals(ConversionUtil.decode(null), -1);
		assertEquals(ConversionUtil.decode("0"), 0);
		assertEquals(ConversionUtil.decode("AzL8n0Y58m7"), Long.MAX_VALUE);
		assertEquals(ConversionUtil.decode("#dfafd43$#"), -1);
	}
}
