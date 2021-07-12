package com.panx.modules.urlswitch.service;

import com.panx.exceptions.SystemException;
import com.panx.modules.urlswitch.entity.UrlVo;

/**
 * @author PanX
 * @date 2021-07-01
 * 长短域名转化Service
 *
 */
public interface UrlSwitchService {
    /**
     * 短域名转为长域名
     * @Param 包含长域名的UrlVo urlInfo
     * @return 包含结果信息及接口运行信息的Result
     */
    UrlVo getLongUrl(UrlVo urlInfo) throws SystemException;

    /**
     * 短域名转为长域名
     * @Param 包含短域名的UrlVo urlInfo
     * @return 包含结果信息及接口运行信息的Result
     */
    UrlVo getShortUrl(UrlVo urlInfo) throws SystemException;
}
