package com.example.url.service.impl;

import com.example.url.entity.UniqueUrl;
import com.example.url.entity.UrlEntity;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;
import org.hashids.Hashids;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

@Slf4j
@SpringBootTest
class ShortUrlServiceImplTest {

    private final Hashids hashids = new Hashids("this is complex salt", 6);
    @Resource
    private ShortUrlServiceImpl shortUrlService;

    @Test
    void encode() {
        // 模拟正确编码的情况
        String shortUrl = shortUrlService.encode("https://github.com/scdt-china/interview-assignments/tree/master/java");
        log.info("编码后的短网址：{}", shortUrl);
        // 模拟布隆过滤器出现误判的情况
        UniqueUrl uniqueUrl = new UniqueUrl("https://github.com/scdt-china/interview-assignments/tree/master/");
        HashCode hashCode = Hashing.murmur3_32().hashString(uniqueUrl.toString(), StandardCharsets.UTF_8);
        String masterShortUrl = hashids.encode(hashCode.padToLong());
        shortUrlService.shortUrlFilter.put(masterShortUrl);
        shortUrl = shortUrlService.encode(uniqueUrl.getLongUrl());
        log.info("编码后的短网址：{}", shortUrl);
        // 模拟短网址在缓存中存在，且长网址不一样，发生了哈希碰撞
        uniqueUrl = new UniqueUrl("https://github.com/scdt-china/interview-assignments");
        hashCode = Hashing.murmur3_32().hashString(uniqueUrl.toString(), StandardCharsets.UTF_8);
        masterShortUrl = hashids.encode(hashCode.padToLong());
        shortUrlService.shortUrlFilter.put(masterShortUrl);
        UrlEntity urlEntity = new UrlEntity();
        urlEntity.setShortUrl(masterShortUrl);
        urlEntity.setUniqueUrl(new UniqueUrl("https://github.com/scdt-china"));
        shortUrlService.shortUrlCache.put(masterShortUrl, urlEntity);
        shortUrl = shortUrlService.encode(uniqueUrl.getLongUrl());
        log.info("编码后的短网址：{}", shortUrl);
    }

    @Test
    void decode() {
        // 模拟已编码长网址的正确情况
        shortUrlService.encode("https://github.com/scdt-china/interview-assignments/tree/master/java");
        String longUrl = shortUrlService.decode("dpE9AYa");
        log.info("解码后的长网址：{}", longUrl);
        // 模拟错误的短网址情况
        longUrl = shortUrlService.decode("1A2S3D");
        log.info("解码后的长网址：{}", longUrl);
        // 模拟错误的短网址，且布隆过滤器出现误判的情况
        shortUrlService.shortUrlFilter.put("2W3E4R");
        longUrl = shortUrlService.decode("2W3E4R");
        log.info("解码后的长网址：{}", longUrl);
    }
}