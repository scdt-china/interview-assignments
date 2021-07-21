package com.homework.tinyurl.service;

/**
 * @Deacription 短地址核心接口
 * @Author zhangjun
 * @Date 2021/7/17 10:10 下午
 **/
public interface TinyUrlCoreService {

    /**
     * 生成短地址
     *
     * @param longUrl
     * @return
     */
    String generateShortUrl(String longUrl);

    /**
     * 获取长地址
     *
     * @param shortUrl
     * @return
     */
    String getLongUrl(String shortUrl);

    /**
     * hash 碰撞统计
     *
     * @return
     */
    Long getHashCollisionCount();
}
