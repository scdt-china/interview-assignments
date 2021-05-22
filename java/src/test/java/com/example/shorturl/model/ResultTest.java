package com.example.shorturl.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("返回结果类测试")
class ResultTest {

	@Test
	@DisplayName("成功测试")
	void wrapSuccess() {
		Result result = Result.wrapSuccess("ss");
		assertEquals(result.getCode(), Result.SUCCESS_CODE);
		assertEquals(result.getData(), "ss");
		assertNull(result.getMessage());
	}

	@Test
	@DisplayName("失败测试")
	void wrapError() {
		Result result = Result.wrapError(Result.EXCEPTION_CODE, "error");
		assertEquals(result.getCode(), Result.EXCEPTION_CODE);
		assertEquals(result.getMessage(), "error");
		assertNull(result.getData());
	}
}
