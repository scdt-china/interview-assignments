package com.xiong.urlservice.utils;

import java.util.Stack;

/**
 * @author: create by xiong
 * @version: v1.0
 * @description:
 * @date:2021/6/23 2:00 下午
 */
public class EncodeUtils {
    /** 乱序（混淆） */
    private static final char[] CHAR_SET =
            "LBClqO43AIbTMDy8G1eVZmF0hutRkzXdQ6wNfiKEJaxWHPSg57rj9nosY2vUcp".toCharArray();

    public static void main(String[] args) {
//        for (long i = 10000000000; long < 10000000100; i++) {
//            long nextId = SnowflakesUtils.nextId();
//            String s = convert10to62(i, 6);
//            System.out.println(i+"转换后："+s);
//
//        }
        String s = convert10to62(10000000000L, 6);
        System.out.println(10000000000L+"转换后："+s);
        //567591519796727809转换后：a2NaLq4HE8
//        String toDecimal = convertBase62ToDecimal("a2NaLq4HE8");
//        System.out.println(toDecimal);
    }



    /**
     * 将10进制转化为62进制
     *
     * @param number 10进制数字
     * @param length 转化成的62进制长度，不足length长度的话高位补0，否则不改变什么
     * @return 62进制字符串表示
     */
    public static String convert10to62(long number, int length) {
        long rest = number;
        Stack<Character> stack = new Stack<>();
        StringBuilder result = new StringBuilder(0);
        while (rest != 0) {
            stack.add(CHAR_SET[new Long((rest - (rest / 62) * 62)).intValue()]);
            rest = rest / 62;
        }
        while (!stack.isEmpty()) {
            result.append(stack.pop());
        }
        int resultLength = result.length();
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < length - resultLength; i++) {
            temp.append('0');
        }

        return temp.append(result.toString()).toString();
    }

    /**
     * 将62进制转换成10进制数
     *
     * @param ident62 62进制字符串表示
     * @return 10进制数字字符串表示
     */
    public static String convertBase62ToDecimal(String ident62) {
        int decimal = 0;
        int base = 62;
        int keisu;
        int cnt = 0;

        byte[] ident = ident62.getBytes();
        for (int i = ident.length - 1; i >= 0; i--) {
            int num = 0;
            if (ident[i] > 48 && ident[i] <= 57) {
                num = ident[i] - 48;
            } else if (ident[i] >= 65 && ident[i] <= 90) {
                num = ident[i] - 65 + 10;
            } else if (ident[i] >= 97 && ident[i] <= 122) {
                num = ident[i] - 97 + 10 + 26;
            }
            keisu = (int) Math.pow(base, cnt);
            decimal += num * keisu;
            cnt++;
        }
        return String.format("%08d", decimal);
    }
}
