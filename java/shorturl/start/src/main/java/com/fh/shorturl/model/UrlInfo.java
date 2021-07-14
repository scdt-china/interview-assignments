package com.fh.shorturl.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * URL映射表(UrlInfo)实体类
 */
@TableName("URL_INFO")
@KeySequence("SEQ")//类注解
public class UrlInfo implements Serializable {
    private static final long serialVersionUID = -41737330147035281L;

    @TableId(value = "ID", type = IdType.INPUT)
    private Integer id;
    
    private String longUrl;
    
    private String shortUrl;
    
    private String sign;
    
    private Date  expireTime;

    public UrlInfo(String sign) {
        this.sign = sign;
    }

    public UrlInfo(Integer id, String longUrl, String shortUrl, String sign, Date expireTime) {
        this.id = id;
        this.longUrl = longUrl;
        this.shortUrl = shortUrl;
        this.sign = sign;
        this.expireTime = expireTime;
    }

    public UrlInfo(String longUrl, String shortUrl, String sign, Date expireTime) {
        this.longUrl = longUrl;
        this.shortUrl = shortUrl;
        this.sign = sign;
        this.expireTime = expireTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }
}