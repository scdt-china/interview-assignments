package com.suncy.shorturl.biz.impl;

import com.suncy.shorturl.biz.IShortUrlBiz;
import com.suncy.shorturl.biz.utils.ShortUrlUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShortUrlBizImpl implements IShortUrlBiz {

    private static final Map<String, String> SHORT_FULL_MAP = new HashMap<String, String>();

    private static final Map<String, String> FULL_SHORT_MAP = new HashMap<String, String>();

    @Override
    public String toShortUrl(String fullUrl) {
        if (StringUtils.isEmpty(fullUrl)) {
            throw new IllegalArgumentException("参数错误");
        }
        String urlMd5 = DigestUtils.md5DigestAsHex(fullUrl.getBytes());
        String shortUrl = FULL_SHORT_MAP.get(urlMd5);
        if (StringUtils.isNotBlank(shortUrl)) {
            return shortUrl;
        }
        // 生成短链
        List<String> shortUrlList = ShortUrlUtil.shortUrl(urlMd5, 8);
        shortUrl = shortUrlList.get(0);
        // 放入缓存
        SHORT_FULL_MAP.put(shortUrl, fullUrl);
        FULL_SHORT_MAP.put(urlMd5, shortUrl);

        return shortUrl;
    }

    @Override
    public String findFullUrl(String shorUrl) {
        return SHORT_FULL_MAP.get(shorUrl);
    }

}
