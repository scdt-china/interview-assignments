package com.yang.shorturl.service;

/**
 *
 *
 * @author yangyiping1
 */
public interface ShortUrlService {

    /**
     * 把长地址转换成短地址
     * @param url 长地址
     * @return 短地址,如果长地址为空刚返回空
     */
    String getShortUrl(String url);

    /**
     * 根据短地址查找长地址
     * @param shortUrl 短地址
     * @return 长地址,如不存在,返回空
     */
    String getUrlFromShortUrl(String shortUrl);
}
