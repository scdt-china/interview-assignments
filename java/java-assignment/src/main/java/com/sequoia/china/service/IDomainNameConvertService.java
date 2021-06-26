package com.sequoia.china.service;

/**
 * @Description 长短域名转换接口类
 * @Author helichao
 * @Date 2021/6/24 6:48 下午
 */
public interface IDomainNameConvertService {

    /**
     * 长域名转短域名
     * @param longDomainName 长域名
     * @return 短域名
     */
    String longToShort(String longDomainName);

    /**
     * 短域名转长域名
     * @param shortDomainName 短域名
     * @return 长域名
     */
    String shortToLong(String shortDomainName);

}
