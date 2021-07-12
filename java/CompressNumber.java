package com.luo.assignment3.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @PackageName:com.luo.assignment3.util
 * @ClassName: CompressNumber
 * @Description: 生成短链接主要方法
 * @author: 罗天文
 * @date: 2021-06-25
 */
public class CompressNumber {
    /**
     * 通过当前序号生成8位字符串
     * @param num 当前位数
     * @return 8位字符串
     */
    public static String base62Encode(long num) {
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
        return StringUtils.leftPad(value, 8, '0');
    }

}
