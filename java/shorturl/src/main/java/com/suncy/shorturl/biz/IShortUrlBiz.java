package com.suncy.shorturl.biz;

public interface IShortUrlBiz {
    String toShortUrl(String fullUrl);

    String findFullUrl(String shorUrl);
}
