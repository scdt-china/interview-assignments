package com.example.url.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UrlEntity {

    /**
     * 短链接地址
     */
    private String shortUrl;

    /**
     * 长链接地址
     */
    private UniqueUrl uniqueUrl;
}
