package org.zhaosd.shorturl.infrastructure.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CMyEncryptTest {

    @Test
    void testMd5() {
        String md5Str = CMyEncrypt.md5("http://s.cn/");
        System.out.println(md5Str);
        assertEquals(32, md5Str.length());
    }
}