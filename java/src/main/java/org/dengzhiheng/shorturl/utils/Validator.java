package org.dengzhiheng.shorturl.utils;

import org.dengzhiheng.shorturl.service.impl.UrlConverterServiceImpl;

import java.util.regex.Pattern;

/**
 * 验证工具类
 * @author : When6passBye
 * @date : 2021/7/20 下午6:15
 */
public class Validator {

    private final static String URL_REGEX = "(ftp|http|https):\\/\\/(\\w+:{0,1}\\w*@)?(\\S+)(:[0-9]+)?(\\/|\\/([\\w#!:.?+=&%@!\\-\\/]))?";
    private final static String URL_SHORT_REGEX = "^[a-z0-9A-Z]+$";

    /**
     * 验证URL地址
     *
     * @param url
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkUrl(String url) {
        return Pattern.matches(URL_REGEX, url);
    }

    /**
     * 验证短域名地址
     *
     * @param shortUrl
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkShortUrl(String shortUrl) {
        if(null == shortUrl || shortUrl.length() > UrlConverterServiceImpl.Max_Len){
            return false;
        }
        return Pattern.matches(URL_SHORT_REGEX, shortUrl);
    }
}
