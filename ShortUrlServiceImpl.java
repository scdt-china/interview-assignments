package com.zdkj.modler.shorturl.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.zdkj.handler.cache.ShortUrlCache;
import com.zdkj.handler.error.shorturl.ShortUrlExp;
import com.zdkj.modler.shorturl.enums.ShortUrlExceptionEnum;
import com.zdkj.modler.shorturl.param.ShortUrlReadParam;
import com.zdkj.modler.shorturl.param.ShortUrlSaveParam;
import com.zdkj.modler.shorturl.service.ShortUrlService;
import com.zdkj.util.IdUtil;
import com.zdkj.util.MUtil;
import com.zdkj.util.MultiBaseConvertUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author bihuiwen
 * @version 1.0
 * @description: TODO
 * @date 2021/7/5 下午10:10
 */
@Service
public class ShortUrlServiceImpl implements ShortUrlService {

    /**
     * 短链接域名
     */
    @Value("${user-config.url.domain1}")
    private String domain;

    /**
     * 内存阈值
     */
    @Value("${user-config.memory.threshold}")
    private double threshold;

    /**
     * 设置有效期短阈值
     */
    @Resource
    private ShortUrlCache shortUrlCache;

    @Override
    public String shortUrlSave(ShortUrlSaveParam shortUrlSaveParam) {
        //判断使用内存是否超过阈值，超过阈值停止生成
        if (MUtil.remainingMemory() < threshold)
            throw new ShortUrlExp(ShortUrlExceptionEnum.PAUSE_RESOLVE);
        //获取长链接对应短六十二位的数字
        String base62 = generateShortUrl();
        String shortUrl = domain+base62;
        //存储短链接和长链接映射关系
        shortUrlCache.put(shortUrl,shortUrlSaveParam.getLongUrl(),shortUrlSaveParam.getTermOfValidity()*1000);
        return shortUrl;
    }

    private String generateShortUrl() {
        //自增序列生成ID
        long id = IdUtil.nextId();
        //如果ID超过限制，抛异常
        if (id >= IdUtil.MAX)
            throw new ShortUrlExp(ShortUrlExceptionEnum.ID_LIMIT);
        //生成六十二进制的数字
        String base62Id = MultiBaseConvertUtil.numberConvertToDecimal(id, 62);
        return base62Id;
    }

    @Override
    public String shortUrlRead(ShortUrlReadParam shortUrlReadParam) {
        //判断短链接是否存在，否则抛出异常
        if (!shortUrlCache.containsKey(shortUrlReadParam.getShortUrl()))
            throw new ShortUrlExp(ShortUrlExceptionEnum.NOT_EXIST);
        //获取长链接，为方便已到过期时间临时的变量
        String longUrl = shortUrlCache.get(shortUrlReadParam.getShortUrl());
        //判读此时链接是否失效，如失效抛出异常
        if (ObjectUtil.isNull(longUrl))
            throw new ShortUrlExp(ShortUrlExceptionEnum.NOT_EXIST);
        return longUrl;
    }
}
