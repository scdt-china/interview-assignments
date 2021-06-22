package com.rufeng.shorturl.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author qujingguo
 * @version 1.0.0
 * @date 2021/5/27 7:20 下午
 * @description
 */
@DisplayName("短域名工具类测试")
@Slf4j
public class ShortLinkUtilsTest {


    @Test
    @DisplayName("生成短域名")
    public void createShortUrl() {
        String shortUrl = ShortLinkUtils.createShortUrl("http://www.baidu.com");
        log.info(shortUrl);
        assertNotNull(shortUrl);
    }


    @Test
    @DisplayName("将10进制数字转为62进制测试")
    public void base62Encode() {
        String s = ShortLinkUtils.base62Encode(0);
        log.info("10进制数字：{} 转为62进制：{}", 0, s);
        assertEquals(s, "000000");
    }

    @Test
    @DisplayName("62进制字符串转为十进制数字测试")
    public void base62Decode() {
        long s = ShortLinkUtils.base62Decode("000000");
        log.info("62进制：{} 转为10进制：{}", "000000", s);
        assertEquals(s, 0);
    }

}
