package com.panx.modules.urlswitch.utils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * @author PanX
 * @date 2021-07-01
 * 长短域名存储类
 *
 */
public class UrlCacheUtils {

    private static Cache<String, String> shortUrlMatchCache = CacheBuilder.newBuilder().build();

    private static Cache<String, String> longUrlMatchCache = CacheBuilder.newBuilder().build();


    public static String getShortUrlMatch(String url) {
        synchronized (UrlCacheUtils.class) {
            return longUrlMatchCache.getIfPresent(url);
        }
    }

    public static String getLongUrlMatch(String url) {
        synchronized (UrlCacheUtils.class) {
            return shortUrlMatchCache.getIfPresent(url);
        }
    }

    public static void setUrlMatch(String longUrl, String shortUrl) {
        synchronized (UrlCacheUtils.class) {
            shortUrlMatchCache.put(shortUrl, longUrl);
            longUrlMatchCache.put(longUrl, shortUrl);
        }
    }
}
