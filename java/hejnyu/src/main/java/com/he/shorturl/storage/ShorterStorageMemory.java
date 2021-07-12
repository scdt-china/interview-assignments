package com.he.shorturl.storage;

import com.he.shorturl.utils.ShorterGetter;
import com.he.shorturl.utils.ShorterStorage;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ShorterStorageMemory<T extends ShorterGetter> implements ShorterStorage<T> {
    /**
     * 存储shorter,url
     */
    Map<ShorterGetter, String> shorterMap = new ConcurrentHashMap<ShorterGetter, String>();
    /**
     * 存储url,shorter
     */
    Map<String, ShorterGetter> urlMap = new ConcurrentHashMap<String, ShorterGetter>();
    /**
     * 存储shorter.shorter,shorter
     */
    Map<String, ShorterGetter> shorterUrlMap = new ConcurrentHashMap<String, ShorterGetter>();

    public String get(String shorterKey) {
        ShorterGetter shorter = shorterUrlMap.get(shorterKey);
        if (shorter != null) {
            return shorterMap.get(shorter);
        }
        return null;
    }

    public void clean(String url) {
        ShorterGetter shorter = urlMap.get(url);
        if (shorter != null) {
            urlMap.remove(url);
            shorterMap.remove(shorter);
            shorterUrlMap.remove(shorter.getShorter());
        }
    }

    public void cleanShorter(String shorterKey) {
        ShorterGetter shorter = shorterUrlMap.get(shorterKey);
        if (shorter != null) {
            urlMap.remove(shorterMap.get(shorter));
            shorterMap.remove(shorter);
            shorterUrlMap.remove(shorter.getShorter());
        }

    }

    public void save(String url, T shorter) {
        urlMap.put(url, shorter);
        shorterMap.put(shorter, url);
        shorterUrlMap.put(shorter.getShorter(), shorter);
    }

    public void clean() {
        shorterMap.clear();
        shorterUrlMap.clear();
        urlMap.clear();
    }
}
