package com.cyz.shorturl.service;

/**
 * @ClassName ShortUrlService
 * @Description //短域名服务Service接口
 * @Author CYZ
 * @Date 2021/07/04 0017 上午 13:11
 **/
public interface  ShortUrlService {

    /**
     * 把长域名转换成短域名
     * @param longUrl 长域名
     * @return 短域名,如果长域名为空刚返回空
     */
    String shortenUrl(String longUrl);

    /**
     * 根据短域名查找长域名
     * @param shortUrl 短域名
     * @return 长域名,如不存在,返回空
     */
    String originalUrl(String shortUrl);

}
