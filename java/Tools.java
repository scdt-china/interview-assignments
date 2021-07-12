package com.luo.assignment3.util;

import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @PackageName:com.luo.assignment3.util
 * @ClassName: Tools
 * @Description: 缓存类 ConcurrentHashMap 处理高并发情况下的缓存
 * @author: 罗天文
 * @date: 2021-06-25
 */
public class Tools {
 private static Map<String,Object>  CACHE_DATA;
 static {
  CACHE_DATA=new ConcurrentHashMap<>();
 }
 public static void set(String key,Object value){
   CACHE_DATA.put(key,value);
 }
 public static Object get(String key){
  return  CACHE_DATA.get(key);
 }
}
