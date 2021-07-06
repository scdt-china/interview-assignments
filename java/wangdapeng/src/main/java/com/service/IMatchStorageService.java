package com.service;

import java.util.Optional;

public interface IMatchStorageService {

    /**
     * 根据短地址取长地址
     * @param longUrl
     * @return
     */
    Optional<String> getShortUrlByLongUrl(String longUrl);

    /**
     * 根据长地址取短地址
     * @param shortUrl
     * @return
     */
    Optional<String> getLongUrlByShortUrl(String shortUrl);

    /**
     * 返回存储的配对数量
     * @return
     */
    long getCacheSize();

    /**
     * 设置长短地址的对应关系
     * @param longUrl
     * @param shortUrl
     */
    void setUrlMatch(String longUrl, String shortUrl);
}
