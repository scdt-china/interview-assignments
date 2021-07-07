package com.xiong.urlservice.service.impl;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.xiong.urlservice.boot.exception.BusinessException;
import com.xiong.urlservice.boot.request.OriginUrlRequest;
import com.xiong.urlservice.boot.request.ShortUrlRequest;
import com.xiong.urlservice.boot.response.Result;
import com.xiong.urlservice.boot.response.ShortUrlResponse;
import com.xiong.urlservice.service.UrlService;
import com.xiong.urlservice.utils.ShortUrlGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: create by xiong
 * @version: v1.0
 * @description:
 * @date:2021/6/21 1:52 下午
 */
@Slf4j
@Service
public class UrlServiceImpl implements UrlService {

    private static Integer urlLimit = 10000;

    private static BloomFilter<String> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charset.defaultCharset()), 10000000, 0.0001);

    private static ConcurrentHashMap<String,String> myContainer = new ConcurrentHashMap(urlLimit);

    @Value("${url.domain}")
    private String urlDomain;


    /**
     * 生成短链接
     * @param request
     * @return
     */
    @Override
    public Result<ShortUrlResponse> saveShortUrl(ShortUrlRequest request) {
        String originUrl = request.getOriginUrl();

        String shortUrl = sysShortUrlGenerator(originUrl);
        ShortUrlResponse build = ShortUrlResponse.builder()
                .shortUrl(shortUrl)
                .originUrl(originUrl)
                .build();
        return Result.success(build);
    }

    @Override
    public Result<ShortUrlResponse> getOriginUrl(OriginUrlRequest request) {
        //缓存
        String shortUrl = request.getShortUrl();
        if (!bloomFilter.mightContain(shortUrl)) {
            return Result.fail("非本服务短链接~");
        }
        String originUrl = myContainer.get(shortUrl);
        if (Strings.isBlank(originUrl)) {
            return Result.fail("无效的短链接~");
        }
        ShortUrlResponse build = ShortUrlResponse.builder()
                .shortUrl(shortUrl)
                .originUrl(originUrl)
                .build();
        return Result.success(build);
    }

    /**
     * 系统生成短链接
     * synchronized 量非常大时 性能更优
     *
     *
     * @param originUrl
     * @return
     */
    private synchronized String sysShortUrlGenerator(String originUrl) {
        String[] keys = ShortUrlGenerator.shortUrl(originUrl);
        //生成4个key 任选其一  有冲突选下一个
        for (String key : keys) {
            //校验重复originUrl
            String shortUrl = urlDomain + key;
            String url = myContainer.get(shortUrl);
            //如果已存在
            if (Strings.isNotBlank(url)) {
                //originUrl已存储 直接返回短链接
                if (url.equals(originUrl)) {
                    return shortUrl;
                }
                continue;
            }
            // 限制下数量 防止内存溢出 达到限制直接拒绝不淘汰数据
            if (myContainer.size() >= urlLimit) {
                log.error("生成短连接达到限制 限制={} ,原链接={}",urlLimit,originUrl);
                throw new BusinessException("服务器繁忙，请稍后再试~");
            }
            myContainer.put(shortUrl, originUrl);
            bloomFilter.put(shortUrl);
            return shortUrl;
        }
        log.error("生成短连接冲突 原链接={}",originUrl);
        throw new BusinessException("服务器繁忙，请稍后再试~");
    }

}
