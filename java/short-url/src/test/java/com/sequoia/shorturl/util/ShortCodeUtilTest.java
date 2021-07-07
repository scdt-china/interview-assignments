package com.sequoia.shorturl.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

/***
 *
 * ShortCodeUtil 短码生成工具类单元测试类
 *
 *@Author xiaojun
 *
 *@Date 2021/6/29 13:44
 *
 *@version v1.0
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShortCodeUtilTest {


    /**
     *
     * 测试是不是生成 8位的短码
     *
     */
    @Test
    public void generateShortCodeTest() {
        String shortCode=ShortCodeUtil.generateShortCode();
        assertEquals(shortCode.length(),8);
        for (int i = 0; i < 100; i++) {
            System.out.println( " 批量8位短网址 " + ShortCodeUtil.generateShortCode());
        }
    }

    /**
     * 测试批量生成是不是1000个，是不是没次都需要生成
     */
    @Test
    public void testGetShortUrlAndGetOriginalUrl() {
        List<String> shortCodelist=ShortCodeUtil.getBatchShortCodeList();
        System.out.println("---短码列表个数:"+shortCodelist.size()); //不包含
        assertEquals(shortCodelist.size(),1000);
        String shortCode=shortCodelist.get(0);
        shortCodelist.remove(0);
        System.out.println("---取一个后短码列表个数:"+shortCodelist.size()); //不包含
        assertEquals(shortCodelist.size(),999);
        System.out.println(shortCodelist.contains(shortCode)); //不包含
    }

}
