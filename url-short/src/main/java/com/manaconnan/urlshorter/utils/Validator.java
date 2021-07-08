package com.manaconnan.urlshorter.utils;

import java.util.regex.Pattern;

/**
 * @Author mazexiang
 * @CreateDate 2021/7/3
 * @Version 1.0
 */
public class Validator {

    private final static String URL_REGEX = "(ftp|http|https):\\/\\/(\\w+:{0,1}\\w*@)?(\\S+)(:[0-9]+)?(\\/|\\/([\\w#!:.?+=&%@!\\-\\/]))?";

    /**
     * 验证URL地址
     *
     * @param url
     * @return
     */
    public static boolean checkUrl(String url) {
        return Pattern.matches(URL_REGEX, url);
    }
}
