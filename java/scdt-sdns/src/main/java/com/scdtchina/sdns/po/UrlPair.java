package com.scdtchina.sdns.po;

import lombok.Data;

import java.io.Serializable;

@Data
public class UrlPair implements Serializable {
    private static final long serialVersionUID = -4255697518999440790L;
    private String normalUrl;
    private String shortUrl;

    public UrlPair(String normalUrl, String shortUrl) {
        this.normalUrl = normalUrl;
        this.shortUrl = shortUrl;
    }
}
