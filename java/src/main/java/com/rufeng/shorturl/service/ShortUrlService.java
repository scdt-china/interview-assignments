package com.rufeng.shorturl.service;

/**
 * @author qujingguo
 * @version 1.0.0
 * @date 2021/5/27 11:22 上午
 * @description 短域名服务service
 */
public interface ShortUrlService {


    /**
     * 接受长域名信息，返回短域名信息
     *
     * @param longUrl 长域名
     * @return 短域名
     */
    String longToShort(String longUrl);

    /**
     * 接受短域名信息，返回长域名信息
     *
     * @param shortUrl 短域名
     * @return 长域名
     */
    String shortToLong(String shortUrl);


}
