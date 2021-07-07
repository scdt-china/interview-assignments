package com.assignment.domain.util;


import org.apache.commons.lang3.StringUtils;

/**
 * @Author: zhangruiqi03
 * @Date: 2021/6/28 10:13 PM
 * 实现10进制和62进制转换， 62进制的结果没有/.等影响url路径的特殊字符
 */
public class NumberTransUtil {



    /**
     * 将数字转为62进制
     * @param num    Long 型数字
     * @return 62进制字符串
     */
    public static String toBase62(long num) {
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();
        int remainder = 0;
        int scale = 62;
        while (num > scale - 1) {
            remainder = Long.valueOf(num % scale).intValue();
            sb.append(chars.charAt(remainder));
            num = num / scale;
        }
        sb.append(chars.charAt(Long.valueOf(num).intValue()));
        String value = sb.reverse().toString();
        // 短域名最大长度是8
        return StringUtils.leftPad(value, 8, '0');
    }
    /**
     * 62进制字符串转为数字
     * @param str 编码后的62进制字符串
     * @return 解码后的 10 进制字符串
     */
    public static long toNumber(String str) {
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        int scale = 62;
        str = str.replace("^0*", "");
        long num = 0;
        int index = 0;
        for (int i = 0; i < str.length(); i++) {
            index = chars.indexOf(str.charAt(i));
            num += (long) (index * (Math.pow(scale, str.length() - i - 1)));
        }
        return num;
    }

}
