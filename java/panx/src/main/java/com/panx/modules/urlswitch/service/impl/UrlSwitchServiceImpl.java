package com.panx.modules.urlswitch.service.impl;

import com.panx.exceptions.SystemException;
import com.panx.modules.urlswitch.utils.UrlCacheUtils;
import com.panx.modules.urlswitch.service.UrlSwitchService;
import com.panx.modules.urlswitch.entity.UrlVo;
import com.panx.modules.urlswitch.utils.UrlSwitchUtils;
import org.springframework.stereotype.Service;

/**
 * @author PanX
 * @date 2021-07-01
 * 长短域名转化Service实现类
 *
 */
@Service
public class UrlSwitchServiceImpl implements UrlSwitchService {
    /**
     * 短域名转为长域名
     * @Param 包含长域名的UrlVo urlInfo
     * @return 包含结果信息及接口运行信息的Result
     */
    public UrlVo getLongUrl(UrlVo urlInfo) throws SystemException {
        String longUrl = urlInfo.getLongUrl();
        String shortUrl = urlInfo.getShortUrl();
        if (null == longUrl && null != shortUrl) {
            String urlHead = UrlSwitchUtils.getUrlHead(shortUrl);
            longUrl = UrlCacheUtils.getLongUrlMatch(shortUrl.replace(urlHead,""));
            if (null == longUrl){
                throw new SystemException("暂无匹配数据");
            }
            longUrl = urlHead + longUrl;
            urlInfo.setLongUrl(longUrl);
            return urlInfo;
        }
        else {
            throw new SystemException("请输入长域名或短域名");
        }
    }

    /**
     * 短域名转为长域名
     * @Param 包含短域名的UrlVo urlInfo
     * @return 包含结果信息及接口运行信息的Result
     */
    public UrlVo getShortUrl(UrlVo urlInfo) throws SystemException {
        String longUrl = urlInfo.getLongUrl();
        String shortUrl = urlInfo.getShortUrl();
        if (null == shortUrl && null != longUrl) {
            String urlHead = UrlSwitchUtils.getUrlHead(longUrl);
            shortUrl = UrlCacheUtils.getShortUrlMatch(longUrl.replace(urlHead,""));
            if (null == shortUrl){
                shortUrl = UrlSwitchUtils.getShortUrlCode();
                UrlCacheUtils.setUrlMatch(longUrl.replace(urlHead,""),shortUrl);
            }
            shortUrl = urlHead + shortUrl;
            urlInfo.setShortUrl(shortUrl);
            return urlInfo;
        }
        else {
            throw new SystemException("请输入长域名或短域名");
        }
    }
}
