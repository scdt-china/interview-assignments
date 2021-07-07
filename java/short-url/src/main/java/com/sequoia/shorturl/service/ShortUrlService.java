package com.sequoia.shorturl.service;

import com.sequoia.shorturl.common.ResponseResult;

/**
 *
 * 短域名服务业务转换接口
 *
 * @Author qq
 *
 * @Date 2021/6/27 16:18
 *
 * @version v1.0.0
 *
 */
public interface ShortUrlService {
    /**
     * 根据原域名返回短域名
     * @param originalUrl 原域名url
     * @return
     */
    public ResponseResult createShortUrl(String originalUrl);
    /**
     * 根据短域名返回原域名
     * @param shortUrl 短域名url
     * @return
     */
    public ResponseResult getOriginalUrl(String shortUrl);

}
