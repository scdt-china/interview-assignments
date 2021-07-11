package com.example.url.service.impl;

import com.example.url.entity.UniqueUrl;
import com.example.url.entity.UrlEntity;
import com.example.url.service.IShortUrlService;
import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import org.hashids.Hashids;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ShortUrlServiceImpl implements IShortUrlService {

    public final ConcurrentHashMap<String, UrlEntity> shortUrlCache = new ConcurrentHashMap<>(1024);
    private final Hashids hashids = new Hashids("this is complex salt", 5);
    /**
     * 长网址布隆过滤器
     */
    public final BloomFilter<String> shortUrlFilter = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), 1000000, 1E-7);

    private String addShortUrl(String shortUrl, UniqueUrl uniqueUrl) {
        UrlEntity urlEntity = new UrlEntity();
        urlEntity.setShortUrl(shortUrl);
        urlEntity.setUniqueUrl(uniqueUrl);
        shortUrlCache.put(shortUrl, urlEntity);
        shortUrlFilter.put(shortUrl);
        return shortUrl;
    }

    private String getShortUrl(UniqueUrl uniqueUrl) {
        String newUniqueUrl = uniqueUrl.toString();
        HashCode hashCode = Hashing.murmur3_32().hashString(newUniqueUrl, StandardCharsets.UTF_8);
        String shortUrl = hashids.encode(hashCode.padToLong());
        synchronized (shortUrl.intern()) {
            boolean isExists = shortUrlFilter.mightContain(shortUrl);
            if (isExists) {
                // 可能存在，拿着这个短网址去缓存中确认是否存在
                UrlEntity urlEntity = shortUrlCache.get(shortUrl);
                if (urlEntity == null) {
                    // 新生成的短网址不存在，可以直接使用（防止误判情况）
                    return addShortUrl(shortUrl, uniqueUrl);
                } else {
                    // 新生成的短网址已经存在，判断存在的长网址是否相同
                    if (uniqueUrl.getLongUrl().equals(urlEntity.getUniqueUrl().getLongUrl())) {
                        // 与缓存中的一样，之前生成过，直接返回短网址
                        return shortUrl;
                    } else {
                        // 与缓存中的不一样，发生了哈希碰撞，重新计算哈希
                        uniqueUrl.setCode(uniqueUrl.getCode() + 88);
                        return getShortUrl(uniqueUrl);
                    }
                }
            } else {
                // 不存在
                return addShortUrl(shortUrl, uniqueUrl);
            }
        }
    }

    @Override
    public String encode(String longUrl) {
        return getShortUrl(new UniqueUrl(longUrl));
    }

    @Override
    public String decode(String shortUrl) {
        boolean isExists = shortUrlFilter.mightContain(shortUrl);
        if (isExists) {
            UrlEntity urlEntity = shortUrlCache.get(shortUrl);
            if (urlEntity == null) {
                return null;
            } else {
                return urlEntity.getUniqueUrl().getLongUrl();
            }
        } else {
            return null;
        }
    }
}
