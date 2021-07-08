package com.manaconnan.urlshorter.utils;

import java.util.Stack;


/**
 * @Author mazexiang
 * @CreateDate 2021/7/4
 * @Version 1.0
 */
public class MathUtils {
    private static char[] charSet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();


    /**
     *
     * @param number
     * @return
     */
    public static String decimalToBase62(long number) {
        Long rest = Math.abs(number);
        Stack<Character> stack = new Stack<Character>();
        StringBuilder result = new StringBuilder(0);
        while (rest != 0) {
            stack.add(charSet[new Long((rest - (rest / 62) * 62)).intValue()]);
            rest = rest / 62;
        }
        while (!stack.isEmpty()) {
            result.append(stack.pop());
        }

        return result.toString();

    }



}
