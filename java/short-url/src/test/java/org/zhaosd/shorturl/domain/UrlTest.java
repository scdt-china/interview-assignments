package org.zhaosd.shorturl.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import static org.junit.jupiter.api.Assertions.*;

class UrlTest {

    private static Url url;

    @BeforeAll
    static void initData() {
        url = new Url("https://www.baidu.com/s?wd=redis%E5%AE%9E%E6%97%B6%E5%90%8C%E6%AD%A5mysql%E6%95%B0%E6%8D%AE%20binlog&rsv_spt=1&rsv_iqid=0x94ae7a1b001c5052&issp=1&f=8&rsv_bp=1&rsv_idx=2&ie=utf-8&rqlang=cn&tn=baiduhome_pg&rsv_enter=1&rsv_dl=tb&oq=redis%25E5%25AE%259E%25E6%2597%25B6%25E5%2590%258C%25E6%25AD%25A5mysql%25E6%2595%25B0%25E6%258D%25AE&rsv_btype=t&inputT=2819&rsv_t=9080BdeS04uopjDDch7EnRph6ZHSrHpwafs7WS%2Bc%2BalMHc3DFwDlTJDBCeHVLR9vnKQR&rsv_pq=d7566d27000025de&rsv_sug3=155&rsv_sug1=106&rsv_sug7=100&rsv_sug2=0&rsv_sug4=3957", "http://s.cn");
    }

    @Test
    void toShortUrl() {
        String shortUrl = url.toShortUrl();
        System.out.println(shortUrl);
        assertNotNull(shortUrl);
        assertEquals(shortUrl, url.getShortUrl());
    }
}