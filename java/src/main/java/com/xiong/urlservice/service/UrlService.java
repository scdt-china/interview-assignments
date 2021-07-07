package com.xiong.urlservice.service;

import com.xiong.urlservice.boot.request.OriginUrlRequest;
import com.xiong.urlservice.boot.request.ShortUrlRequest;
import com.xiong.urlservice.boot.response.Result;
import com.xiong.urlservice.boot.response.ShortUrlResponse;

/**
 * @author: create by xiong
 * @version: v1.0
 * @description:
 * @date:2021/6/21 1:44 下午
 */
public interface UrlService {
    Result<ShortUrlResponse> saveShortUrl(ShortUrlRequest request);

    Result<ShortUrlResponse> getOriginUrl(OriginUrlRequest request);
}

