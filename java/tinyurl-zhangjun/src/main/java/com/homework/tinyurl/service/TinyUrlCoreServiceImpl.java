package com.homework.tinyurl.service;

import com.google.common.hash.Hashing;
import com.homework.tinyurl.config.ServerConfig;
import com.homework.tinyurl.enums.TinyUrlExceptionCodeEnum;
import com.homework.tinyurl.model.exception.TinyUrlException;
import com.homework.tinyurl.utils.Base62EncodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Deacription <p>
 * 映射信息维护在本地jvm 采用caffeine作为本地存储
 * <p/>
 * @Author zhangjun
 * @Date 2021/7/17 10:20 下午
 **/
@Service
@Slf4j
public class TinyUrlCoreServiceImpl implements TinyUrlCoreService {

    @Autowired
    private CacheManager cacheManager;

    /**
     * 缓存key
     */
    private static final String SHORT_URL_CACHE_KEY = "shortUrlMapping";

    /**
     * hash碰撞时,加固定前缀
     */
    private static final String PREFIX_SALT = "_";
    /**
     * 统计hash冲突
     */
    private static AtomicLong hashCollisionCounter = new AtomicLong();


    /**
     * 1.生成短地址
     * 2.处理hash冲突
     * 3.存储到缓存
     *
     * @param longUrl
     * @return
     */
    @Override
    public String generateShortUrl(String longUrl) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        longUrl = longUrl.toLowerCase();
        //1.生成短地址,hash冲突会递归增加随机字符
        String encode62ShortUrl = generate62ShortUrl(longUrl);
        //2.处理hash冲突
        encode62ShortUrl = processHashCollision(encode62ShortUrl, longUrl);
        //3.存储关系
        cacheManager.getCache(SHORT_URL_CACHE_KEY).put(encode62ShortUrl, formatLongUrl(longUrl));
        stopWatch.stop();
        return ServerConfig.SHORT_DOMAIN_PATH + encode62ShortUrl;
    }


    /**
     * 根据短地址获取长地址
     *
     * @param shortUrl
     * @return
     */
    @Override
    public String getLongUrl(String shortUrl) {
        shortUrl = shortUrl.replace(ServerConfig.SHORT_DOMAIN_PATH, "");
        Cache cache = cacheManager.getCache(SHORT_URL_CACHE_KEY);
        if (cache == null || cache.get(shortUrl) == null) {
            throw new TinyUrlException(TinyUrlExceptionCodeEnum.NOT_FOUND);
        }
        String longUrl = cache.get(shortUrl).get().toString();
        //处理hash冲突前面加的字符
        return formatLongUrl(longUrl);
    }

    @Override
    public Long getHashCollisionCount() {
        return hashCollisionCounter.get();
    }


    /**
     * 生成62位的短地址
     * <p>
     * 1.先通过Hashing.murmur3_32 计算
     * 2.再把Long转成62进制变得更短
     * </p>
     *
     * @param longUrl
     * @return
     */
    private String generate62ShortUrl(String longUrl) {
        //1.hash值
        Long shortUrlId = Hashing.murmur3_32().hashString(longUrl, StandardCharsets.UTF_8).padToLong();
        //2.把id转成62进制,把地址变得更短
        String encode62ShortUrl = Base62EncodeUtil.encode(shortUrlId);
        return encode62ShortUrl;
    }

    /**
     * 处理hash冲突,递归处理,最坏打算循环26次
     *
     * @param encode62ShortUrl
     * @param longUrl
     * @return
     */
    private String processHashCollision(String encode62ShortUrl, String longUrl) {
        //不存在
        Cache cache = cacheManager.getCache(SHORT_URL_CACHE_KEY);
        if (cache != null && cache.get(encode62ShortUrl) != null) {
            String cacheLongUrl = cache.get(encode62ShortUrl).get().toString();
            //产生hash冲突
            if (!longUrl.equals(cacheLongUrl.toLowerCase())) {
                hashCollisionCounter.incrementAndGet();
                log.info("hash冲突 encode62ShortUrl={}, longUrl={}", encode62ShortUrl, longUrl);
                longUrl = formatLongUrl(longUrl);
                //产生了hash碰撞,加一位随机值
                longUrl = (char) (Math.random() * 26 + 'a') + PREFIX_SALT + longUrl;
                encode62ShortUrl = generate62ShortUrl(longUrl);
                return this.processHashCollision(encode62ShortUrl, longUrl);
            }
        }
        return encode62ShortUrl;
    }

    /**
     * 如果长地址包含特殊前缀,
     *
     * @param longUrl
     * @return
     */
    public String formatLongUrl(String longUrl) {
        if (longUrl.indexOf(PREFIX_SALT) == 1) {
            longUrl = longUrl.substring(2, longUrl.length());
        }
        return longUrl;
    }

}
