package com.zdkj.handler.cache;

import cn.hutool.cache.impl.TimedCache;

/**
 * @author bihuiwen
 * @version 1.0
 * @description:  短链接存储缓存，这个可以设置有效期
 * @date 2021/7/4 下午 9:33
 */
public class ShortUrlCache implements MemoryCacheOperator<String>{

    private TimedCache<String, String> timedCache;

    public ShortUrlCache(TimedCache<String,String> timedCache) {
        this.timedCache = timedCache;
    }

    @Override
    public void put(String key, String value, Long timeoutSeconds) {
        timedCache.put( key, value, timeoutSeconds * 1000);
    }

    @Override
    public String get(String key) {
        // 如果用户在超时前调用了get(key)方法，会重头计算起始时间，false的作用就是不从头算
        return timedCache.get( key, false);
    }

    @Override
    public boolean containsKey(String key) {
        return timedCache.containsKey(key);
    }

}
