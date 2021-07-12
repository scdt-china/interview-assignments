package com.sequoiacapital.assignment.service;

/**
 * URL转换服务类
 *
 * @Author: xin.wu
 * @Date: Created in 2021/6/24 17:33
 * @Version: 1.0
 */
public interface UrlChangeService {

    /**
     * 长链接转换成短链接
     * @author xin.wu
     * @date 2021/6/24
     * @param longUrl
     * @return java.lang.String
     */
    String getShortUrl(String longUrl);

    /**
     * 短链接转换为长链接
     * @author xin.wu
     * @date 2021/6/24
     * @param key
     * @return java.lang.String
     */
    String getLongUrl(String key);

}
