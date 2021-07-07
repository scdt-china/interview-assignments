package xyz.sgld.sls.util;

public class StrUtil {
    /**
     * check a String is empty
     *
     * @param str the check string
     * @return if str is null or the trim len is zero return true
     */
    public static final boolean isEmpty(String str) {
        if (str == null)
            return true;
        if (str.trim().length() == 0)
            return true;
        return false;
    }
}
