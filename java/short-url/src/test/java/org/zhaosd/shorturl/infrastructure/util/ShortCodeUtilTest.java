package org.zhaosd.shorturl.infrastructure.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShortCodeUtilTest {

    @Test
    void testEncode() {
        String sLongUrl = "http://s.cn/just-test-a-long-url"; // 长链接
        String[] aResult = ShortCodeUtil.encode(sLongUrl);
        assertEquals(4, aResult.length);
        // 打印出结果
        for (int i = 0; i < aResult.length; i++) {
            System.out.println("[" + i + "]:::" + aResult[i]);
            assertNotNull(aResult[i]);
        }
    }
}