package com.wanghui.service;

import com.wanghui.model.ShortUrl;

/**
 * @author 10281
 * @title 域名处理业务逻辑层
 * @Date 2021-07-17 20:08
 * @Description 用于域名的读取、转换。存储等操作
 */
public interface DomainService {

    /**
     * 短域名读取接口：接受短域名信息，返回长域名信息。
     * @param shortUrl      短链接
     * @return  ShortUrl
     */
    ShortUrl short2LongUrl(String shortUrl);

    /**
     * 短域名存储接口：接受长域名信息，返回短域名信息
     * @param longUrl
     * @return
     */
    ShortUrl long2ShortUrl(String longUrl);
}
