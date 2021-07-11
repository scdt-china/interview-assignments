package com.example.url.service;

public interface IShortUrlService {

    /**
     * 将长网址编码为短网址
     *
     * @param longUrl 长网址
     * @return 短网址
     */
    String encode(String longUrl);

    /**
     * 将短网址解码为长网址
     *
     * @param shortUrl 短网址
     * @return 长网址
     */
    String decode(String shortUrl);
}
