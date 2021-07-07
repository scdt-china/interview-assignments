package com.zdkj.modler.shorturl.service;

import com.zdkj.modler.shorturl.param.ShortUrlReadParam;
import com.zdkj.modler.shorturl.param.ShortUrlSaveParam;

/**
 * @author bihuiwen
 * @version 1.0
 * @description: TODO
 * @date 2021/7/5 下午10:10
 */
public interface ShortUrlService {

    /**
     * 生成短链接
     * @param shortUrlSaveParam
     * @return 生成短链接
     */
    String shortUrlSave(ShortUrlSaveParam shortUrlSaveParam);

    /**
     * 读取短链接
     * @param shortUrlReadParam
     * @return
     */
    String shortUrlRead(ShortUrlReadParam shortUrlReadParam);

}
