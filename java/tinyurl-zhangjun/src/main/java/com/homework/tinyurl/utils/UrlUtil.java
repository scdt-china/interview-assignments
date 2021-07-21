package com.homework.tinyurl.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * @Deacription
 * @Author zhangjun
 * @Date 2021/7/18 8:44 下午
 **/
public class UrlUtil {

    static Pattern pattern = Pattern
            .compile("^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\\\\\\\/])+$");

    /**
     * @param url
     * @return
     */
    public static boolean isValidationUrl(String url) {
        if (StringUtils.isBlank(url)) {
            return false;
        }
        return pattern.matcher(url).matches();
    }

}
