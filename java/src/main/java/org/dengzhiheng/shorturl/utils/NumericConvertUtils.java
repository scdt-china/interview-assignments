package org.dengzhiheng.shorturl.utils;

/**
 * 参考JDK源码实现进制转换
 * @author : When6passBye
 * @date : 2021/7/20 下午6:14
 */
public class NumericConvertUtils {

    /**
     * 在进制表示中的字符集合，0-Z分别用于表示最大为62进制的符号表示
     */
    private static final char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    private static final Integer DECIMAL = 10;

    /**
     * 将十进制的数字转换为指定进制的字符串
     *
     * @param number 十进制的数字
     * @param seed   指定的进制
     * @return 指定进制的字符串
     */
    public static String convertTo(long number, int seed) {
        if (number < 0) {
            number = ((long) 2 * 0x7fffffff) + number + 2;
        }
        char[] buf = new char[32];
        int charPos = 32;
        while ((number / seed) > 0) {
            buf[--charPos] = DIGITS[(int) (number % seed)];
            number /= seed;
        }
        buf[--charPos] = DIGITS[(int) (number % seed)];
        return new String(buf, charPos, (32 - charPos));
    }

}
