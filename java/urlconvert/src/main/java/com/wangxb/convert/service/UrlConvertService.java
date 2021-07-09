package com.wangxb.convert.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import com.wangxb.convert.mem.CacheMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class UrlConvertService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static String charCode = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static List<Character> listCharCode = Lists.newArrayList();
    //短链接字母数字组合数
    private static long charLength = charCode.length();
    //最大的短链接组合数
    private static long maxCharLength = 1;

    private static AtomicLong atomicLongSeq = new AtomicLong(-1);
    //默认返回的短链接长度
    private static int defultUrlLength = 8;
    //默认返回的host
    private static String defaultHost = "http://t.com/";

    CacheMap cacheMapShortUrl = new CacheMap();
    static {

        listCharCode = charCode.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
        Collections.shuffle(listCharCode);
        for(int i = 0;i<(defultUrlLength-3);i++){
            maxCharLength = charLength * charLength;
        }
    }

    public String getShortUrl(String longUrl){
        String retUrl = "";
        if (StringUtils.isEmpty(longUrl) == true){
            return retUrl;
        }

        long seq = getSeq();
        if (seq == -1){
            return retUrl;
        }
        retUrl = convertShortUrl(seq,defultUrlLength-3).toString();
        retUrl = addRandomChar(retUrl);
        retUrl = defaultHost + retUrl;

        cacheMapShortUrl.put(retUrl,longUrl);
        return retUrl;
    }

    private long getSeq(){

        long seq = atomicLongSeq.incrementAndGet();
        if (seq >= maxCharLength){
            seq = -1;
        }
        return seq;
    }

    private synchronized void initSeq(long seq){
        if (seq == maxCharLength ) {
            atomicLongSeq.set(-1);
        }
    }

    public String getLongUrl(String shortUrl){
        String retUrl = "";
        if (StringUtils.isEmpty(shortUrl) == true){
            return retUrl;
        }
        retUrl = cacheMapShortUrl.get(shortUrl);
        if (StringUtils.isEmpty(retUrl) == false){
            return retUrl;
        }
        return retUrl;
    }

    private String convertShortUrl(long seq,int length) {
        String shortUrl = "";
        StringBuilder url = new StringBuilder();
        for (int i = 0;i<length;i++) {
            int index = (int) (seq % charLength);
            url.append(listCharCode.get(index));
            seq = (seq / charLength) - 1;
            if (seq == -1){
                while(i<length-1){
                    url.append(listCharCode.get(0));
                    i++;
                }
                break;
            }
        }
        shortUrl = url.reverse().toString();
        return shortUrl;
    }

    String addRandomChar(String url){
        Random rand = new Random();
        int nRand = (int)(charLength);
        url = listCharCode.get(rand.nextInt(nRand)) + listCharCode.get(rand.nextInt(nRand)) + url + listCharCode.get(rand.nextInt(nRand));
        return url;
    }

    public void clear(int num){
        cacheMapShortUrl.clear(8);
    }
}
