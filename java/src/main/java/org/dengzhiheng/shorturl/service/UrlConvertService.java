package org.dengzhiheng.shorturl.service;

import org.dengzhiheng.shorturl.Result.Result;

/**
 * @author : When6passBye
 * @date : 2021/7/12 下午4:07
 */
public interface UrlConvertService {

    /**
     * 得到短地址URL
     *
     * @param url
     * @return
     */
    Result<String> convertUrl(String url);

    /**
     * 将短地址URL 转换为正常的地址
     *
     * @param shortUrl
     * @return
     */
    Result<String> revertUrl(String shortUrl);

}
