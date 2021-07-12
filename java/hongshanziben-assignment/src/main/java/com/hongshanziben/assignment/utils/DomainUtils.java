package com.hongshanziben.assignment.utils;

import java.util.UUID;

/**
 * @author SJYUAN:KINGSJ.YUAN@FOXMAIL.COM
 * @date 2021-07-08.
 */
public class DomainUtils {

    private static final char[] DICT = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    public static String getShortDomain() {
        StringBuffer buffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String sub = uuid.substring(i * 4, i * 4 + 4);
            int parseInt = Integer.parseInt(sub, 16);
            buffer.append(DICT[parseInt % 32]);

        }
        return buffer.toString();
    }

}
