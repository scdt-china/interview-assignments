package com.chenghf.shorturl.service.impl;

import com.chenghf.shorturl.service.IShortUrlService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShortUrlServiceImpl implements IShortUrlService {

    // 字典容器
    private String[] chars = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p",
            "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A",
            "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"
    };
    // 映射关系存储
    private Map<String, String> mappingMap = new HashMap<>();
    // 模拟数据库存储longUrl
    private List<String> longUrlList = new ArrayList<>();
    // 域名常量
    public static final String DOMAIN = "chenghf.com/";

    /**
     * 长域名转短域名服务
     * @author chf
     * @date 2021年7月4日
     * @param longUrl
     * @return
     */
    @Override
    public String longToShort( String longUrl) {
        this.longUrlList.add(longUrl);
        // id编号
        int id = this.longUrlList.indexOf(longUrl);
        // 62进制转换
        int d1 = id / 62;
        int d2 = id % 62;
        String shortStr = chars[d1] + chars[d2];
        //补齐6位
        int diff = 6 - shortStr.length();
        if (diff > 0) {
            for (int i = 0, n = diff; i < n; i++) {
                shortStr = chars[0] + shortStr;
            }
        }

        // 保存映射
        mappingMap.put(shortStr, longUrl);
        return ShortUrlServiceImpl.DOMAIN + shortStr;
    }
    /**
     * 短域名获取长域名服务
     * @author chf
     * @date 2021年7月4日
     * @param shortUrl
     * @return
     */
    @Override
    public String shortToLong( String shortUrl) {
        return mappingMap.get(shortUrl);
    }
}
