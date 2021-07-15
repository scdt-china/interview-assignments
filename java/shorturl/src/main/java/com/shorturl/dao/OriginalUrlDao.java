
package com.shorturl.dao;

import com.shorturl.common.LruMap;
import com.shorturl.domain.OriginalUrlPo;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 原始长链接Dao层
 *
 * @author penghang
 * @created 7/8/21
 */
@Repository
public class OriginalUrlDao {

    /**
     * 对应数据库original_url_idx查询索引
     */
    static final Map<String, OriginalUrlPo> originalUrlMap = new LruMap<>(1000);
    /**
     * 对应数据库uniq_id_idx查询索引
     */
    static final Map<Long, OriginalUrlPo> uniqIdMap = new LruMap<>(1000);

    public boolean saveOriginalUrl(long uniqId, String originalUrl) {
        if (!StringUtils.hasText(originalUrl) || uniqId < 0) {
            return false;
        }
        final Date date = new Date();
        OriginalUrlPo originalUrlPo = new OriginalUrlPo();
        originalUrlPo.setUniqId(uniqId);
        originalUrlPo.setUrl(originalUrl);
        originalUrlPo.setAddTime(date);
        originalUrlPo.setLastAccessTime(date);
        originalUrlPo.setUpdateTime(date);
        originalUrlMap.put(originalUrl, originalUrlPo);
        uniqIdMap.put(uniqId, originalUrlPo);
        return true;
    }

    public String getOriginalUrl(Long uniqId) {
        if (uniqId == null) {
            return null;
        }
        final OriginalUrlPo originalUrlPo = uniqIdMap.get(uniqId);
        if (originalUrlPo != null) {
            return originalUrlPo.getUrl();
        }
        return null;
    }

    public OriginalUrlPo getByOriginalUrl(String originalUrl) {
        if (!StringUtils.hasText(originalUrl)) {
            return null;
        }
        final OriginalUrlPo originalUrlPo = originalUrlMap.get(originalUrl);
        if (originalUrlPo != null) {
            // 如果是数据库，更新lastAccessTime，根据lastAccessTime清理数据
            originalUrlPo.setLastAccessTime(new Date());
        }
        return originalUrlPo;
    }
}