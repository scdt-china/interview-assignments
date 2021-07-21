package com.homework.tinyurl.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @Deacription 采用62进制存储10进制
 * @Author zhangjun
 * @Date 2021/7/17 11:22 下午
 **/
public class Base62EncodeUtil {

    /**
     * 混合
     */
    private static String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    /**
     * 位数
     */
    private static int scale = 62;
    /**
     * 长度
     */
    private static int minLength = 5;

    /**
     * 10进制转62进制
     *
     * @param num
     * @return
     */
    public static String encode(long num) {
        StringBuilder sb = new StringBuilder();
        int remainder;
        while (num > scale - 1) {
            //对 scale 进行求余，然后将余数追加至 sb 中，由于是从末位开始追加的，因此最后需要反转字符串
            remainder = Long.valueOf(num % scale).intValue();
            sb.append(chars.charAt(remainder));
            //除以进制数，获取下一个末尾数
            num = num / scale;
        }
        sb.append(chars.charAt(Long.valueOf(num).intValue()));
        String value = sb.reverse().toString();
        return StringUtils.leftPad(value, minLength, '0');
        //return value;
    }

    /**
     * 62进制转10进制
     *
     * @param str
     * @return public static long decode(String str) {
    //将 0 开头的字符串进行替换
    str = str.replace("^0*", "");
    long value = 0;
    char tempChar;
    int tempCharValue;
    for (int i = 0; i < str.length(); i++) {
    //获取字符
    tempChar = str.charAt(i);
    //单字符值
    tempCharValue = chars.indexOf(tempChar);
    //单字符值在进制规则下表示的值
    value += (long) (tempCharValue * Math.pow(scale, str.length() - i - 1));
    }
    return value;
    }     */
}
