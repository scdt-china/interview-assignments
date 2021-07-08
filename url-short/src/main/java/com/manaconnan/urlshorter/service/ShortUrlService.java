package com.manaconnan.urlshorter.service;

import com.manaconnan.urlshorter.config.AsynProcessor;
import com.manaconnan.urlshorter.config.LruCache;
import com.manaconnan.urlshorter.exception.ShortUrlException;
import com.manaconnan.urlshorter.utils.MathUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.MurmurHash3;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author mazexiang
 * @CreateDate 2021/7/3
 * @Version 1.0
 */
@Service
@Slf4j
public class ShortUrlService {
    @Autowired
    private CacheService cacheService;



    public List<String> queryAll(){
        // TODO: 2021/7/4  query all from db
        return new ArrayList<>();
    }

    public String generateShortUrl(String url){
        if (StringUtils.isBlank(url)){
            throw  new ShortUrlException("url is blank");
        }
        long hash = MurmurHash3.hash32x86(url.getBytes());
        return MathUtils.decimalToBase62(hash);
    }

    public String findByKey(String key){

        String val = cacheService.get(key);
        if (StringUtils.isBlank(val)) {
            // TODO: 2021/7/4 query from db
        }
        return  val;
    }

    public int saveToDb(String url, String hash) {
         return  0;
    }
}
