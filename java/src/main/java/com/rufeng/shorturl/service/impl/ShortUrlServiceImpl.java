package com.rufeng.shorturl.service.impl;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.rufeng.shorturl.constant.Constants;
import com.rufeng.shorturl.dao.UrlDao;
import com.rufeng.shorturl.service.ShortUrlService;
import com.rufeng.shorturl.utils.ShortLinkUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author qujingguo
 * @version 1.0.0
 * @date 2021/5/27 12:11 下午
 * @description
 */
@Service
public class ShortUrlServiceImpl implements ShortUrlService {


    @Resource
    private UrlDao urlDao;

    /**
     * 布隆过滤器
     */
    private final BloomFilter<String> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), Constants.EXPECTED_INSERTIONS, Constants.FPP);


    @Override
    @CacheEvict(cacheNames = "SHORT_URL", key = "#result")
    public String longToShort(String longUrl) {
        String shortUrl = urlDao.getShortUrl(longUrl);
        if (StringUtils.isBlank(shortUrl)) {
            shortUrl = ShortLinkUtils.createShortUrl(longUrl);
            shortUrl = urlDao.putUrl(shortUrl, longUrl);
            //增加到布隆过滤器，用来过滤重复的长滤名转换申请
            bloomFilter.put(shortUrl);
        }
        return Constants.SHORT_URL_PREFIX + shortUrl;
    }


    @Override
    @Cacheable(cacheNames = "LONG_URL", key = "#shortUrl", unless = "#result==null")
    public String shortToLong(String shortUrl) {
        if (!shortUrl.startsWith(Constants.SHORT_URL_PREFIX)) {
            return null;
        }
        String shortHashNum = shortUrl.replace(Constants.SHORT_URL_PREFIX, "");

        if (!bloomFilter.mightContain(shortHashNum)) {
            return null;
        }
        return urlDao.getLongUrl(shortHashNum);
    }
}
