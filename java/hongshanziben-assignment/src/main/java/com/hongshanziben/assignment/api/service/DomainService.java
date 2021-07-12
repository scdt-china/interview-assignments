package com.hongshanziben.assignment.api.service;

/**
 * 域名转换接口
 *
 * @author KINGSJ.YUAN@FOXMAIL.COM
 * @date 2021-07-08
 */
public interface DomainService {


    /**
     * 创建短链，并返回
     *
     * @param url
     * @return
     */
    String createShortDomain(String url);

    /**
     * 根据短链得到长链接
     *
     * @param url
     * @return
     */
    String getDomain(String url);
}
