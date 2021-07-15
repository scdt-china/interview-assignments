
package com.shorturl.domain;

import java.util.Date;

/**
 * 数据库对应字段
 *
 * @author penghang
 * @created 7/8/21
 */
public class OriginalUrlPo {

    private Long uniqId;
    private String url;
    /** 数据库中可以考虑根据最后访问时间来确认是否删除 **/
    private Date lastAccessTime;
    private Date addTime;
    private Date updateTime;

    public Long getUniqId() {
        return uniqId;
    }

    public void setUniqId(Long uniqId) {
        this.uniqId = uniqId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(Date lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}