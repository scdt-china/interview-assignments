package com.wanghui.service;

import java.util.Optional;

/**
 * @author wanghui
 * @title 模拟持久化业务逻辑类
 * @Date 2021-07-17 23:24
 * @Description 模拟数据持久化以及读写
 */
public interface RepositoryService {

    /**
     * 存储长短域名对照
     * @param shortUrl
     * @param longUrl
     * @return
     */
    boolean saveShorAndLongtUrl(String shortUrl, String longUrl);

    /**
     * 根据长地址获取短地址
     * @param longUrl
     * @return
     */
    String getShortUrlByLongUrl(String longUrl);

    /**
     * 根据短地址获取长地址
     * @param shortUrl
     * @return
     */
    String getLongUrlByShortUrl(String shortUrl);

}
