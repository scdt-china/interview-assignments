package com.suncy.shorturl.biz.impl;

import com.suncy.shorturl.biz.IShortUrlBiz;
import com.suncy.shorturl.biz.utils.ShortUrlUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ShortUrlBizV1Impl implements IShortUrlBiz {
    // 两个map 模拟存储

    private static final Map<Long, String> SHORT_FULL_MAP = new HashMap<Long, String>();

    private static final Map<String, String> FULL_SHORT_MAP = new HashMap<String, String>();
    // 短链序号，模拟redis 自增
    private static AtomicLong SHORTURL_IDX = new AtomicLong(9999L);

    @Override
    public String toShortUrl(String fullUrl) {
        if (StringUtils.isEmpty(fullUrl)) {
            throw new IllegalArgumentException("参数错误");
        }
        String urlMd5 = DigestUtils.md5DigestAsHex(fullUrl.getBytes());
        // 防止重复生成，先判断是否存在
        String shortUrl = FULL_SHORT_MAP.get(urlMd5);
        if (StringUtils.isNotBlank(shortUrl)) {
            return shortUrl;
        }
        // 生成短链
        long urlId = SHORTURL_IDX.getAndIncrement();
        shortUrl = ShortUrlUtil.encodeString(urlId);
        // 放入缓存, 模拟存入db
        SHORT_FULL_MAP.put(urlId, fullUrl);
        FULL_SHORT_MAP.put(urlMd5, shortUrl);

        return shortUrl;
    }

    @Override
    public String findFullUrl(String shorUrl) {
        long urlId = ShortUrlUtil.decodeString(shorUrl);
        // 模拟从DB种按ID取长链接,要用缓存
        return SHORT_FULL_MAP.get(urlId);
    }

}
