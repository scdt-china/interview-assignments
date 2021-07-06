package cn.dns.utils;

import cn.dns.exception.CheckArgsException;

public class CheckArgsUtils {
    public static void checkArgs(boolean isNotchecked,String msg){
        if(isNotchecked){
            throw new CheckArgsException(msg);
        }
    }
}
