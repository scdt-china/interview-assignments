package com.he.shorturl.utils;

public interface UrlShorterGenerator<T extends ShorterGetter> {


    /**
     * 产生一个短链接对象
     *
     * @param url
     * @return
     */
    T generate(String url);

}
