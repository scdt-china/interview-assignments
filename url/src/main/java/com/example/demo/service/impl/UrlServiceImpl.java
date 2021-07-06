package com.example.demo.service.impl;

import com.example.demo.service.UrlService;
import com.example.demo.util.LRULinkedHashMap;
import com.example.demo.util.MD5Util;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;


@Service
public class UrlServiceImpl implements UrlService {
    private static ConcurrentHashMapCache hashMap = new ConcurrentHashMapCache();
    @Override
    public String save(String url) {
        String shortUrl = null;
        try{
            shortUrl = gererateShortUrl(url);
            hashMap.put(shortUrl, url);
        }catch (Exception e) {
            return "系统异常";
        }
        return shortUrl;
    }

    @Override
    public String restoreUrl(String url) {
        String LongURL = hashMap.get(url);
        if ("".equals(LongURL) || LongURL == null){
         return "无效的域名";
        }
        return LongURL;
    }
    /**
     * 将长链接转换为短链接
     * @param url
     * @return
     */
    public String gererateShortUrl(String url) {
        // 可以自定义生成 MD5 加密字符传前的混合 KEY
        String key = "caron" ;
        // 要使用生成 URL 的字符
        String[] chars = new String[] { "a" , "b" , "c" , "d" , "e" , "f" , "g" , "h" ,
                "i" , "j" , "k" , "l" , "m" , "n" , "o" , "p" , "q" , "r" , "s" , "t" ,
                "u" , "v" , "w" , "x" , "y" , "z" , "0" , "1" , "2" , "3" , "4" , "5" ,
                "6" , "7" , "8" , "9" , "A" , "B" , "C" , "D" , "E" , "F" , "G" , "H" ,
                "I" , "J" , "K" , "L" , "M" , "N" , "O" , "P" , "Q" , "R" , "S" , "T" ,
                "U" , "V" , "W" , "X" , "Y" , "Z"

        };
        // 对传入网址进行 MD5 加密
        String sMD5EncryptResult = MD5Util.MD5(key+url);
        String hex = sMD5EncryptResult;

        // 把加密字符按照 8 位一组 16 进制与 0x3FFFFFFF 进行位与运算
        String sTempSubString = hex.substring(2 * 8, 2 * 8 + 8);    //固定取第三组

        long lHexLong = 0x3FFFFFFF & Long.parseLong (sTempSubString, 16);
        String outChars = "" ;
        for ( int j = 0; j < 8; j++) {
            long index = 0x0000003D & lHexLong;
            outChars += chars[( int ) index];
            lHexLong = lHexLong >> 5;
        }
        return outChars;
    }
}
