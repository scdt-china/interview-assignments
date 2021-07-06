package com.example.shorturl.service;


public interface ShortUrlService {
    public String generateShortUrl(String originUrl);

    public String queryOriginUrl(String shortUrl);
}
