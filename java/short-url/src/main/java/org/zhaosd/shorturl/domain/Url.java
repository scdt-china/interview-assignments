package org.zhaosd.shorturl.domain;

import org.zhaosd.shorturl.infrastructure.util.ShortCodeUtil;

import java.util.UUID;

/**
 * 超链接聚合根领域对象
 */
public class Url {

    String id;        // 聚合根id，唯一确定聚合根对象
    String shortDomain; // 短链接域名
    String shortCode;   // 短链接编码
    String shortUrl;    // 转码后的短链接
    String srcUrl;      // 转码前的原始连接

    public Url(String aSrcUrl, String aShortDomain) {
        this.id = UUID.randomUUID().toString();
        this.srcUrl = aSrcUrl;
        this.shortDomain = aShortDomain;
    }

    public String getId() {
        return id;
    }

    public void setId(String anId) {
        this.id = anId;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String aShortUrl) {
        this.shortUrl = aShortUrl;
        int lastSlashIndex = this.shortUrl.lastIndexOf('/');
        this.shortDomain = this.shortUrl.substring(0, lastSlashIndex);
        this.shortCode = this.shortUrl.substring(lastSlashIndex + 1, this.shortUrl.length());
    }

    public String getShortDomain() {
        return shortDomain;
    }

    public void setShortDomain(String shortDomain) {
        this.shortDomain = shortDomain;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public String getSrcUrl() {
        return srcUrl;
    }

    public void setSrcUrl(String srcUrl) {
        this.srcUrl = srcUrl;
    }

    /**
     * 转码为短链接
     * @return
     */
    String toShortUrl() {
        if (srcUrl == null) {
            throw new IllegalArgumentException("未设置原始连接srcUrl，无法转码为短链接！");
        }
        if (shortDomain == null) {
            throw new IllegalArgumentException("未设置短连接域名shortDomain，无法转码为短链接！");
        }
        if (shortUrl == null) {
            shortCode = ShortCodeUtil.encode(srcUrl)[0];
            shortUrl = new StringBuilder(shortDomain)
                    .append("/")
                    .append(shortCode).toString();
        }
        return shortUrl;
    }

}
