package org.zhaosd.shorturl.domain;

/**
 * 超链接聚合根仓库类
 */
public interface UrlRepository {

    /**
     * 保存超连接对象
     * @param aUrl
     */
    void save(Url aUrl);

    /**
     * 通过id获取超链接对象
     * @param id
     * @return
     */
    Url get(String id);

    /**
     * 通过短链接获取超链接对象
     * @param shortUrl
     * @return
     */
    Url getByShortUrl(String shortUrl);

    /**
     * 通过长连接获取超链接对象
     * @param srcUrl
     * @return
     */
    Url getBySrcUrl(String srcUrl);

    /**
     * 获取超链接对象总条目数
     * @return
     */
    Integer count();
}
