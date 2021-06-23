package com.example.shortUrl.service;

/**
 * @author sting
 * @date 2021/5/20
 */

public interface ShortUrlService {

    String generate(String url);

    String resolve(String shortUrl);
}
