package cn.dns.utils;


import cn.dns.exception.CheckArgsException;

/**
 * 「十进制与62进制互相转换」的算法
 * <table>
 *     <tr>
 *         <th>ASCII可显示字符</th><th>对应十进制数字范围</th>
 *     </tr>
 *     <tr>
 *         <td>0-9</td><td>48~57</td>
 *     </tr>
 *     <tr>
 *         <td>A-Z</td><td>65~90</td>
 *     </tr>
 *     <tr>
 *         <td>a-z</td><td>97~122</td>
 *     </tr>
 * <p>在本工具中，约定代表62进制的62位序列为以下格式：0-9A-Za-z</p>
 */
public class ConversionUtils {

    /**
     * 在进制表示中的字符集合，0-Z分别用于表示最大为62进制的符号表示
     */
    private static final char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    private static final Integer DECIMAL = 10;

    private static Long maxNumber = -1L;



    /**
     * 生成指定长度短域名
     * @param number 随机数字
     * @param seed   步长
     * @param length 生成域名长度
     * @return
     */
    public static String convertToByLength(long number, int seed, int length)  {
        if (maxNumber == -1) {
            maxNumber = new Double(Math.pow(62, length - 1)).longValue();
        }
        if (number > maxNumber) {
            throw new CheckArgsException("短域名超长,最长" + length + "位");
        }

        return convertTo(number, seed);
    }


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
