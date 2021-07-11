package com.example.url.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
public class UniqueUrl {

    @Setter
    private int code;

    private final String longUrl;

    public UniqueUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    @Override
    public String toString() {
        return "@Code=" + code + ";@LongUrl=" + longUrl;
    }
}
