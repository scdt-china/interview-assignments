package org.zhaosd.shorturl.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 超链接领域服务类（执行修改或删除及领域代码服务类）
 * @author mvt-zhaosandong-mac
 */
@Service
public class UrlDomainService {

    @Value("${shorturl.shortdomain:http://s.cn}")
    private String shortDomain;

    @Autowired
    UrlRepository urlRepository;

    public Url toShortUrl(String srcUrl) {
        Url url = urlRepository.getBySrcUrl(srcUrl);
        if (url == null) {
            url = new Url(srcUrl, shortDomain);
            url.toShortUrl();
            urlRepository.save(url);
        }
        return url;
    }

}
