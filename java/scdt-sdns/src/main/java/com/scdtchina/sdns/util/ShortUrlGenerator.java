package com.scdtchina.sdns.util;

import java.util.concurrent.atomic.AtomicLong;

public class ShortUrlGenerator {

    private static final char[] shortUrlCharactors = new char[]{
            'q', 'A', '3', 'F', 'G', 'H', 'I', 'J', 'K', '4',
            'D', 'E', 'Z', '-', '_', '1', 'B', 'L', 'b', 'c',
            '5', 'h', 'Q', 'R', 'u', 'v', 'U', 'V', '6', 'i',
            'j', 'k', 'l', '8', 'z', 'f', 'g', 's', 't', 'S',
            'T', 'M', 'N', 'o', 'p', 'Z', '-', '_', '1', 'B',
            'L', 'b', 'c', '5', 'h', '9', 'a', 'm', 'n', 'd',
            'e', 'q', 'r', 'Q', 'R', 'u', 'v', 'U', 'V', '6',
            'i', 'j', 'k', 'l', '8', 'z', '9', 'a', 'm', 'n',
            'd', 'e', '0', 'r'};
    private static final long mask64 = (1 << 6) - 1;

    private static final AtomicLong cursor = new AtomicLong(0);

    public static String generateNext() {
        long index = cursor.incrementAndGet();
        char[] buffer = new char[8];
        int pos = 7;
        while (pos >= 0) {
            buffer[pos--] = shortUrlCharactors[(int) (index & mask64)];
            index >>>= 6;
        }
        return new String(buffer);
    }

}
