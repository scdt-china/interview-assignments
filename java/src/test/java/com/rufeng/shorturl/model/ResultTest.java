package com.rufeng.shorturl.model;

import com.rufeng.shorturl.enums.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("短域名工具类测试")
@Slf4j
public class ResultTest {


    @Test
    @DisplayName("error方法测试")
    public void error() {
        Result result = Result.error(ErrorCode.NOT_FOUND);
        assertEquals(result.getCode(), ErrorCode.NOT_FOUND.getCode());
        assertFalse(result.isSuccess());
    }

    @Test
    @DisplayName("errorByEnum方法测试")
    public void errorByEnum() {
        Result result = Result.error("0", "error");
        assertEquals(result.getCode(), "0");
        assertFalse(result.isSuccess());
    }

    @Test
    @DisplayName("ok方法测试")
    public void ok() {
        Result result = Result.ok("data");
        assertEquals(result.getCode(), ErrorCode.SUCCESS.getCode());
        assertTrue(result.isSuccess());
    }

}
