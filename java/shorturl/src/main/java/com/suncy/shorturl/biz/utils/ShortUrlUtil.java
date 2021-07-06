package com.suncy.shorturl.biz.utils;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @author suncy
 * @Date 2021/6/30 8:38
 */
public class ShortUrlUtil {

    // 要使用生成 URL的字符
    private static final String[] CHARS = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l",
            "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6",
            "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
            "S", "T", "U", "V", "W", "X", "Y", "Z" };

    // CHARS 值与下标的映射
    private static final Map<String, Integer> CHAR_INDEX_MAP = Maps.newHashMap();

    // 生成 URL字符数组CHARS长度占用
    private static final int CHARS_LENTGH_BIT_SIZE = 5;
    // 16进制一位占用字节数
    private static final int HEX_BIT_SIZE = 4;

    static {
        for (int i = 0; i < CHARS.length; i++) {
            CHAR_INDEX_MAP.put(CHARS[i], i);
        }
    }

    public static List<String> shortUrl(String urlMd5, int shortUrlLen) {

        int urlMd5SubBitSize = CHARS_LENTGH_BIT_SIZE * shortUrlLen;
        // 分割urlMd5的长度；
        int urlMd5SubStrLength = (int) Math.ceil(1.0 * urlMd5SubBitSize / HEX_BIT_SIZE);

        Iterable<String> subUrlMd5s = Splitter.fixedLength(urlMd5SubStrLength).split(urlMd5);
        List<String> resultList = Lists.newArrayList();
        for (String subUrlMd5 : subUrlMd5s) {
            if (subUrlMd5.length() < urlMd5SubStrLength) {
                continue;
            }
            // 子串转成16进制
            long lHexLong = 0xFFFFFFFFFFL & Long.parseLong(subUrlMd5, 16);// 0xFFFFFFFFFFL &  并取前40位
            String shortUrl = StringUtils.EMPTY;
            for (int i = 0; i < shortUrlLen; i++) {

                int index = (int) (0x0000003D & lHexLong);
                // 把取得的字符相加
                shortUrl += CHARS[index];
                // 每次循环按位右移 5 位
                lHexLong = lHexLong >> 5;
            }
            resultList.add(shortUrl);
        }
        return resultList;
    }

    /**
     * 10进制转62进制
     *
     * @param num
     * @return
     */
    public static String encodeString(long num) {
        StringBuffer result = new StringBuffer();
        do {
            int idx = (int) num % CHARS.length;
            result.append(CHARS[idx]);
            num = num / CHARS.length;
        } while (num != 0);
        return result.toString();
    }

    /**
     * 62进制转10进制
     *
     * @param str
     * @return
     */
    public static long decodeString(String str) {
        long result = 0;
        for (int i = 0; i < str.length(); i++) {
            String chr = String.valueOf(str.charAt(i));
            int idx = CHAR_INDEX_MAP.get(chr);
            long cur = idx * (long) Math.pow(62, i);
            result = result + cur;
        }
        return result;
    }
}
