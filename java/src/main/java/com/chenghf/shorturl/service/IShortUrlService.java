package com.chenghf.shorturl.service;

public interface IShortUrlService {
    String longToShort( String longUrl);

    String shortToLong( String shortUrl);
}
