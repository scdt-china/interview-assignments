package com.zdkj.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author bihuiwen
 * @version 1.0
 * @description: TODO
 * @date 2021/7/5 下午9:06
 */
public class MultiBaseConvertUtil {
    public static final char[] array =
            {
                    'q','2',   'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's', 'd',
                    'f',  '5','g', 'h', 'j','4', 'k', 'l', '3','z', 'x', 'c', 'v', 'b', 'n', 'm',
                    'Q', 'W', '0','E','6', '7','R', 'T', 'Y', '8','U', 'I', 'O','1', 'P', 'A', 'S', 'D',
                    'F', 'G', 'H', 'J', 'K', '9','L', 'Z', 'X', 'C', 'V', 'B', 'N', 'M'};


    public static Map<Character, Integer> charValueMap = new ConcurrentHashMap<Character, Integer>();

    //初始化map
    static {
        for (int i = 0; i < array.length; i++) charValueMap.put(array[i], i);
    }

    public static void main(String[] args) {

        for (long i = 2000; i <2020 ; i++) {
            String decimalStr = numberConvertToDecimal(i, 62);
            System.out.println(decimalStr);

            long toNumber = decimalConvertToNumber(decimalStr, 62);
            System.out.println(decimalStr + " 转换成 " + toNumber);
        }
    }


    /**
     * 把数字转换成相对应的进制,目前支持(2-62)进制
     *
     * @param number
     * @param decimal
     * @return
     */
    public static String numberConvertToDecimal(long number, int decimal) {
        StringBuilder builder = new StringBuilder();
        while (number != 0) {
            builder.append(array[(int) (number - (number / decimal) * decimal)]);
            number /= decimal;
        }
        return builder.reverse().toString();
    }

    /**
     * 把进制字符串转换成相应的数字
     * @param decimalStr
     * @param decimal
     * @return
     */
    public static long decimalConvertToNumber(String decimalStr, int decimal) {
        long sum = 0;
        long multiple = 1;
        char[] chars = decimalStr.toCharArray();
        for (int i = chars.length - 1; i >= 0; i--) {
            char c = chars[i];
            sum += charValueMap.get(c) * multiple;
            multiple *= decimal;
        }
        return sum;
    }
}
