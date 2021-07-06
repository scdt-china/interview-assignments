package com.service;

public interface IDomainNameService {

    /**
     * 根据长地址取短地址
     * @param longUrl
     * @return
     */
    String getShortUrl(String longUrl);

    /**
     * 根据短地址取长地址
     * @param shortUrl
     * @return
     */
    String getLongUrl(String shortUrl);
}
