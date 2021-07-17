package com.example.shorturlproj.service.impl;


import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author lishiyu
 * @description call方法重写，生成短域名
 * @param <T>
 */

final class GenerateShortUrl<T> implements Callable<T> {

    private static final AtomicLong index = new AtomicLong(0);

    private final static char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
    };
    @Override
    public T call() throws Exception {
        //先取后加
        Long value = index.getAndIncrement();
        //则8位62进制数最大为62^8=218340105584896
        index.compareAndSet(218340105584896L,0);
        String urlString = compressNumberToUrl(value);
        String shortUrl = urlString;
        return (T) shortUrl;
    }

    private static String compressNumberToUrl(long number) {
        String urlString = new String();
        while(number > 61){
            urlString = digits[(int)(number % 62)] + urlString;
            number = number / 62;
        }
        urlString = digits[(int)(number % 62)] + urlString;
        return urlString;
    }
}