
package com.shorturl.common;

/**
 * 在这里编写类的功能描述
 *
 * @author penghang
 * @created 7/8/21
 */
public class UrlConvertUtils {

    private static final int URL_RADIX = 34;

    public static String getShortUrlByUniqId(Long uniqId) {
        // 10进制转34进制
        return Long.toString(uniqId, URL_RADIX);
    }

    public static Long getUniqIdByShortUrl(String shortUrl) {
        // 34进制转10进制
        return Long.parseLong(shortUrl, URL_RADIX);
    }
}