package com.example.shortUrl.domain;

import lombok.Data;

@Data
public class LongUrlReq extends BaseReq{
    private String longUrl;
}
