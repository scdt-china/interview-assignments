package com.cyz.shorturl.service.impl;

import com.cyz.shorturl.service.ShortUrlService;
import com.cyz.shorturl.util.SequenceUtils;
import com.cyz.shorturl.util.ShortUrlUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @ClassName ShortUrlService
 * @Description //短域名服务Service实现类
 * @Author CYZ
 * @Date 2021/07/04 0017 上午 13:11
 **/
@Service
public class ShortUrlServiceImpl implements ShortUrlService {

    @Override
    public String shortenUrl(String longUrl) {
        if (StringUtils.hasLength(longUrl)) {
            //获取唯一序列，真实环境可以使用数据库自增序列来实现
            long sequence = SequenceUtils.getSequence();
            //生成短域名
            String shortUrl = ShortUrlUtils.generateShortUrl(sequence);
            //保存长域名和短域名的关系
            ShortUrlUtils.saveShortUrl(shortUrl,longUrl);
            return shortUrl;
        }
        return null;
    }

    @Override
    public String originalUrl(String shortUrl) {
        return ShortUrlUtils.getLongUrl(shortUrl);
    }
}
