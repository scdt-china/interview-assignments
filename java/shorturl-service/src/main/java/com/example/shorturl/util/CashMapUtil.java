package com.example.shorturl.util;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : shenhc
 * @date : 2021/7/6
 * desc:
 * 用于存储临时的key-value对
 */
public class CashMapUtil {

    public static Map<String,String> cash = new HashMap();

    public static synchronized void pushValue(String key,String value){
        String valueCash = cash.get(key);
        if(StringUtils.isBlank(valueCash)  || StringUtils.isEmpty(valueCash)){
            cash.put(key,value);
        }else {
            throw  new  RuntimeException("key value 已存在");
        }

    }

    public static synchronized String getValue(String key){
        return  cash.get(key);
    }


    public static synchronized String getKeyByValue(String value){
        Map.Entry<String, String> ele =  cash.entrySet().stream().filter(e -> value.equals(e.getValue())).findFirst().orElse(null);
        if(ele == null){
            return null;
        }
        return ele.getValue();
    }

}
