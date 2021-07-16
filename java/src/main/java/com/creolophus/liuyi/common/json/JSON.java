package com.creolophus.liuyi.common.json;

import java.lang.reflect.Type;

/**
 * @author magicnana
 * @date 2020/8/17 1:51 PM
 */
public class JSON {

  public static <T> T parseObject(String json, Type type) {
    return GsonUtil.toJava(json, type);
  }

  public static <T> T parseObject(String json, Class<T> clazz) {
    return GsonUtil.toJava(json, clazz);
  }

  public static <T> T parseObject(Object object, Type type) {
    return GsonUtil.toJava(object, type);
  }

  public static <T> T parseObject(Object object, Class<T> clazz) {
    return GsonUtil.toJava(object, clazz);
  }

  public static <T> T parseObject(byte[] bytes, Class<T> clazz) {
    return GsonUtil.toJava(bytes, clazz);
  }

  public static <T> T parseObject(byte[] bytes, Type type) {
    return GsonUtil.toJava(bytes, type);
  }

  public static String toJSONString(Object object) {
    return GsonUtil.toJson(object);
  }

  public static byte[] toJSONBytes(Object object) {
    return GsonUtil.toByteArray(object);
  }
}
