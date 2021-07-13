package com.tzg157.demo.service;

import com.tzg157.demo.model.Response;
import com.tzg157.demo.model.UrlResult;

public interface DomainConvertService {

    /**
     * 长域名转换服务
     * @param url
     * @return
     */
    Response<UrlResult> convertToLong(String url);

    /**
     * 短域名转换服务
     * @param url
     * @return
     */
    Response<UrlResult> convertToShort(String url);
}
