
package com.shorturl.service;

import com.shorturl.common.*;
import com.shorturl.dao.OriginalUrlDao;
import com.shorturl.domain.OriginalUrlPo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * 在这里编写类的功能描述
 *
 * @author penghang
 * @created 7/8/21
 */
@Service
public class ShortUrlService {

    @Resource
    private OriginalUrlDao originalUrlDao;
    @Resource
    private UniqIdService uniqIdService;

    private static final String SHORT_DOMAIN = "http://heiqiuren.com/";

    /**
     * 长链接置换短链接
     *
     * @param originalUrl
     * @return
     */
    public String getShortUrl(String originalUrl) {
        //先查有没有
        OriginalUrlPo originalUrlPo = originalUrlDao.getByOriginalUrl(originalUrl);
        if (originalUrlPo != null) {
            final Long uniqId = originalUrlPo.getUniqId();
            return SHORT_DOMAIN + UrlConvertUtils.getShortUrlByUniqId(uniqId);
        }

        final Long uniqId = uniqIdService.getUniqId();
        final boolean saveResult = originalUrlDao.saveOriginalUrl(uniqId, originalUrl);
        if (!saveResult) {
            throw new BusinessException(CommonErrorCodeEnum.SAVE_URL_ERROR);
        }
        return SHORT_DOMAIN + UrlConvertUtils.getShortUrlByUniqId(uniqId);
    }

    /**
     * 短链接置换长链接
     *
     * @param shortUrl
     * @return
     */
    public String getOriginalUrl(String shortUrl) {
        final Long uniqId = UrlConvertUtils.getUniqIdByShortUrl(shortUrl.replace(SHORT_DOMAIN, ""));
        return originalUrlDao.getOriginalUrl(uniqId);
    }
}