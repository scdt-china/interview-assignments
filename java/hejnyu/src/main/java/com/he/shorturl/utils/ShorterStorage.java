package com.he.shorturl.utils;


public interface ShorterStorage<T extends ShorterGetter> {

    String get(String shorter);

    void clean(String url);

    void cleanShorter(String shorter);

    void save(String url, T shorter);

    void clean();

}
