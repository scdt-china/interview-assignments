package com.sequoiacapital.assignment.service.impl;

import com.sequoiacapital.assignment.service.UrlChangeService;
import com.sequoiacapital.assignment.utils.ShortUrlUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 实现业务类
 *
 * @Author: xin.wu
 * @Date: Created in 2021/6/24 17:35
 * @Version: 1.0
 */
@Service
@Slf4j
public class UrlChangeServiceImpl implements UrlChangeService {

    /**
     * 此处用于存储KV映射，项目书可用Redis存储
     * @author xin.wu
     * @date 2021/6/24
     */
    private static ConcurrentHashMap<String,String> memDbMock = new ConcurrentHashMap<>();

    @Override
    public String getShortUrl(String longUrl) {
        String[] keys = ShortUrlUtils.shortUrl(longUrl);
        //取出第一个短链接作为映射
        memDbMock.put(keys[0],longUrl);
        return keys[0];
    }

    @Override
    public String getLongUrl(String key) {
        return memDbMock.get(key);
    }

}
