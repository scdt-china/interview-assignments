package com.rufeng.shorturl.dao;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Repository;

/**
 * @author qujingguo
 * @version 1.0.0
 * @date 2021/5/27 11:26 上午
 * @description url数据存储
 */
@Repository
public final class UrlDao {
    /**
     * 存储源
     * key->longUrl, value->shortUrl
     */
    private final BiMap<String, String> urlMap = Maps.synchronizedBiMap(HashBiMap.create(100));


    private UrlDao() {
    }

    /**
     * 根据短域名获取长域名地址
     *
     * @param shortUrl 短域名地址，用来获取长域名地址
     * @return 长域名地址
     */
    public String getLongUrl(String shortUrl) {
        return urlMap.inverse().get(shortUrl);
    }

    /**
     * 保存短域名和长域名的对应关系
     *
     * @param shortUrl 短域名URL
     * @param longUrl  长域名URL
     * @return 返回shortUrl
     */
    public String putUrl(String shortUrl, String longUrl) {
        String result = urlMap.putIfAbsent(longUrl, shortUrl);
        if (result != null) {
            return result;
        }
        return urlMap.get(longUrl);
    }

    /**
     * 获取短连接
     *
     * @param longUrl 长链接
     * @return 短连接
     */
    public String getShortUrl(String longUrl) {
        return urlMap.get(longUrl);
    }
}
