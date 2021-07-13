package com.tzg157.demo.util;

import org.apache.commons.validator.routines.UrlValidator;

public class HttpSchemaUtil {

    private static final String[] schemas = {"http","https"};

    public static boolean isLegal(String url){
        return isLegal(url,schemas);
    }

    /**
     * 校验url schema是否合法
     * @param url
     * @return
     */
    public static boolean isLegal(String url,String[] schemas){
        UrlValidator validator = new UrlValidator(schemas);
        return validator.isValid(url);
    }
}
