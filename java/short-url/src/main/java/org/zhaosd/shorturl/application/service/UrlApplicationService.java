package org.zhaosd.shorturl.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zhaosd.shorturl.domain.Url;
import org.zhaosd.shorturl.domain.UrlRepository;

/**
 * 超链接查询应用服务类
 * @author mvt-zhaosandong-mac
 */
@Service
public class UrlApplicationService {

    @Autowired
    UrlRepository urlRepository;

    /**
     * 通过短链接查询连接实体对象
     * @param shortUrl
     * @return
     */
    public Url getByShortUrl(String shortUrl) {
        Url url = urlRepository.getByShortUrl(shortUrl);
        if (url == null) {
            throw new IllegalStateException("仓库中没有该短链接：" + shortUrl);
        }
        return url;
    }

    /**
     * 获取仓库中已保存的超链接实体对象数量
     * @return
     */
    public Integer count() {
        return urlRepository.count();
    }

}
