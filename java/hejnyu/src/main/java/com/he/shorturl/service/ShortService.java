package com.he.shorturl.service;

public interface ShortService {
    String create(String url);

    String show(String shortCode);
}
