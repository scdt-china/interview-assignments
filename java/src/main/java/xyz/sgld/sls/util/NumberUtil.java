package xyz.sgld.sls.util;

public class NumberUtil {
    private static char[] table = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    private static final int BIT = table.length;
    private static final int MIN_LEN = 3;
    private static final int MAX_LEN = 8;

    public static final String longTo62Str(long number) {
        if (number < 0)
            throw new IllegalArgumentException("number must >=0");
        StringBuffer stringBuffer = new StringBuffer();
        do {
            int index = (int) (number % BIT);
            stringBuffer.insert(0, table[index]);
            number /= BIT;
        } while (number > 0);
        if (stringBuffer.length() < MIN_LEN) {
            int zeroLen = MIN_LEN - stringBuffer.length();
            for (int i = 0; i < zeroLen; i++) {
                stringBuffer.insert(0, table[0]);
            }
        }
        return stringBuffer.toString();
    }

    public static final long str62ToLong(String str) {
        if (str != null &&
                str.length() >= MIN_LEN &&
                str.length() <= MAX_LEN &&
                check62Str(str)
        ) {
            long res = 0;
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(str.length() - i - 1);
                long num = getCharIndexOf(c);
                if (num > 0) {
                    for (int j = 0; j < i; j++) {
                        num *= BIT;
                    }
                }
                res += num;
            }
            return res;
        }
        throw new IllegalArgumentException("str must a number or a-z or A-Z length less than " + MAX_LEN + " " +
                "and more than " + MAX_LEN + " string");
    }

    public static final boolean check62Str(String str) {
        String regxStr = "^[A-Za-z0-9]+$";
        return str.matches(regxStr);
    }

    public static int getCharIndexOf(char c) {
        //0-9
        if (c >= 48 && c <= 57)
            return c - 48;
        //A-Z
        if (c >= 65 && c <= 90)
            return 36 + c - 65;
        //a-z
        if (c >= 97 && c <= 122)
            return 10 + c - 97;
        throw new IllegalArgumentException("char c must a 0-9 or a-z or A-Z ");
    }
}
