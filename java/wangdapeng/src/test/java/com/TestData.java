package com;

import com.google.common.base.Strings;

import java.util.Optional;

public class TestData {

    public static final String LONG_URL = "http://www.baidu.com/";
    public static final String SHORT_URL = "http://t.cn/ABCDEF";
    public static final String SHORT_URL_BAIDU = "http://t.cn/VJfumq";
    public static final String EMPTY_STRING = "";
    public static final String SHORT_URL_ILLEGAL = "http://sina.cn/ABCDEF";
    public static final String SHORT_URL_ILLEGAL2 = "http://t.cn/ABC/DEF";
    public static final String TOO_LONG_URL = "http//www.baidu.com?key=" + Strings.repeat("A", 2000);
    public static final Optional<String> optionalString = Optional.of("ABCDEF");
    public static final Optional<String> optionalEmpty = Optional.empty();
    public static final Long BIG_VALUE = 99999L;
    public static final Long THREE = 3L;
    public static final Long ONE = 1L;
}
