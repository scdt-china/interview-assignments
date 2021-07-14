package com.fh.shorturl.service;

import java.io.UnsupportedEncodingException;

public interface ShortURLService {

    /**
     * @param longurl 长链接
     * @return
     */
    String addShortURL(String longurl);

    /**
     * @param shorturl 短链接
     * @return
     */
    String queryLongURL(String shorturl);
}
