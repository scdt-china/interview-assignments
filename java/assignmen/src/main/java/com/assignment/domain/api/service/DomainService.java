package com.assignment.domain.api.service;

import java.util.Optional;

public interface DomainService {
    /**
     * 保存并返回短域名
     *
     * @param longDomainAddress 长域名
     * @return 返回短域名
     */
    String saveAndReply(String longDomainAddress);

    /**
     * 读取短域名对应的长域名
     *
     * @param shortDomainAddress 短域名
     * @return 返回对应长域名
     */
    Optional<String> read(String shortDomainAddress);
}
