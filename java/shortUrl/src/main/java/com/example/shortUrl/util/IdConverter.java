package com.example.shortUrl.util;

public class IdConverter {
    private static final String baseChar = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static char[] arrBaseChar = baseChar.toCharArray();
    private static int baseLen = arrBaseChar.length;

    public static String ten2SixtyTwo(long input){
        String encodedString = new String();
        if(input == 0) {
            return String.valueOf(arrBaseChar[0]);
        }
        while (input > 0) {
            encodedString = encodedString + (arrBaseChar[(int) (input % baseLen)]);
            input = input / baseLen;
        }
        return encodedString;
    }
}
