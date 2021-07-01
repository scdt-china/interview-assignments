package com.panx.utils;

import com.panx.exception.UrlSwitchException;

public class UrlSwitchUtils {


    /**
     * 在进制表示中的字符集合，0-Z分别用于表示最大为62进制的符号表示
     */
    private static final char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    /**
     * 将十进制的id序列号转为62进制的短域名编码
     * @param number 十进制的数字
     * @return 指定进制的字符串
     */
    public static String idToShortUrl(long number) {
        if (number < 0) {
            number = ((long) 2 * 0x7fffffff) + number + 2;
        }
        char[] buf = new char[32];
        int charPos = 32;
        while ((number / 62) > 0) {
            buf[--charPos] = digits[(int) (number % 62)];
            number /= 62;
        }
        buf[--charPos] = digits[(int) (number % 62)];
        return new String(buf, charPos, (32 - charPos));
    }

    public static String getUrlHead(String url) throws UrlSwitchException {
        String[] urlSplit = url.split("/");
        if(urlSplit.length<4){
            throw new UrlSwitchException("url格式异常");
        }
        String urlHead = "";
        for(int i=0;i<3;i++){
            urlHead+=urlSplit[i]+"/";
        }
        return urlHead;
    }
}
