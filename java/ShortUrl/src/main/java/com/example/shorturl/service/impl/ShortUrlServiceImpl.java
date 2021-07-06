package com.example.shorturl.service.impl;

import com.example.shorturl.common.ShortUrlGenerator;
import com.example.shorturl.service.ShortUrlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Zhu Lingfang
 * @version v1.0
 * @since 2021/06/05
 */
@Slf4j
@Service
public class ShortUrlServiceImpl implements ShortUrlService {
    private static ConcurrentHashMap<String, String> urlMapper = new ConcurrentHashMap<>();

    @Value("${domain}")
    private String domain;

    @Override
    public String generateShortUrl(String originUrl) {
        String shortUrl = getShortUrl(originUrl);
        return shortUrl;
    }

    @Override
    public String queryOriginUrl(String shortUrl) {
        return urlMapper.get(shortUrl);
    }

    private synchronized String getShortUrl(String url) {
        String[] prefixes = ShortUrlGenerator.shortUrls(url);
        for (String prefix : prefixes) {
            String shortUrl = domain + "/" + prefix;
            if (urlMapper.containsKey(shortUrl)) {
                if (urlMapper.get(shortUrl).equals(url)) return shortUrl;
                continue;
            } else {
                urlMapper.put(shortUrl, url);
                return shortUrl;
            }
        }
        log.error("Conflict in Short URL Generator, origin url: {}", url);
        throw new RuntimeException("Short URL Conflict, Please retry");
    }
}
