package com.rufeng.shorturl.utils;

import com.google.common.hash.Hashing;
import org.apache.commons.lang3.StringUtils;

/**
 * @author qujingguo
 * @version 1.0.0
 * @date 2021/5/27 7:20 下午
 * @description
 */
public class ShortLinkUtils {

    private static final String CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int SCALE = 62;
    private static final int MIN_LENGTH = 6;


    /**
     * 创建短链接
     *
     * @param url
     * @return
     */
    public static String createShortUrl(String url) {
        long num = Hashing.murmur3_32().hashUnencodedChars(url).padToLong();
        return base62Encode(num);
    }

    /**
     * 将10进制数字转为62进制
     *
     * @param num Long 型数字
     * @return 62进制字符串
     */
    public static String base62Encode(long num) {
        StringBuilder sb = new StringBuilder();
        int remainder;
        while (num > SCALE - 1) {
            remainder = Long.valueOf(num % SCALE).intValue();
            sb.append(CHARS.charAt(remainder));
            num = num / SCALE;
        }
        sb.append(CHARS.charAt(Long.valueOf(num).intValue()));
        String value = sb.reverse().toString();
        return StringUtils.leftPad(value, MIN_LENGTH, '0');
    }

    /**
     * 62进制字符串转为十进制数字
     *
     * @param str 编码后的62进制字符串
     * @return 解码后的 10 进制字符串
     */
    public static long base62Decode(String str) {
        str = str.replace("^0*", "");
        long num = 0;
        int index = 0;
        for (int i = 0; i < str.length(); i++) {
            index = CHARS.indexOf(str.charAt(i));
            num += (long) (index * (Math.pow(SCALE, str.length() - i - 1)));
        }
        return num;
    }

}
