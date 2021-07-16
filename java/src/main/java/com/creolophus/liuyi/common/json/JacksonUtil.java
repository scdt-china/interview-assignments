package com.creolophus.liuyi.common.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * 默认不忽略 Null 属性,但是这里已配置 SimpleDateFormat已指定 Object 或者泛型属性 有可能是 LinkedHashMap Object 或者泛型属性,如果制定
 * TypeReference或者 JavaType,那么结果是目标类型 new TypeReference<Pager<User>>(){} 这里还有一个把 LinkedHashMap 转为
 * JavaObject 的方法
 *
 * @author magicnana
 * @date 2020/8/19 9:15 AM
 */
public class JacksonUtil {

  /**
   * 这里有一个栗子: https://github.com/easonjim/5_java_example/blob/master/springboottest/springboottest10/src/main/java/com/jsoft/springboottest/springboottest1
   * /controller/TestController.java
   */

  private static ObjectMapper mapper = new ObjectMapper();

  static {
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    mapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
  }

  public static synchronized void init(ObjectMapper input) {
    mapper = input;
  }

  public static ObjectMapper mapper() {
    return mapper;
  }

  public static byte[] toByteArray(Object obj) {
    if (obj == null) {
      return null;
    }
    try {
      return mapper.writeValueAsBytes(obj);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> T toJava(byte[] bytes, Class<T> clazz) {
    if (bytes == null) {
      return null;
    }
    try {
      return mapper.readValue(bytes, clazz);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> T toJava(String string, TypeReference<T> type) {
    if (null == string || "".equals(string)) {
      return null;
    }

    try {
      return mapper.readValue(string, type);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> T toJava(byte[] bytes, TypeReference<T> type) {
    if (bytes == null) {
      return null;
    }
    try {
      return mapper.readValue(bytes, type);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> T toJava(String string, JavaType type) {
    if (null == string || "".equals(string)) {
      return null;
    }
    try {
      return mapper.readValue(string, type);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> T toJava(byte[] bytes, JavaType type) {
    if (bytes == null) {
      return null;
    }
    try {
      return mapper.readValue(bytes, type);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> T toJava(String string, Class<T> clazz) {
    if (null == string || "".equals(string)) {
      return null;
    }

    try {
      return mapper.readValue(string, clazz);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> T toJava(Object object, Class<T> clazz) {
    if (object == null) {
      return null;
    }
    return mapper.convertValue(object, clazz);
  }

  public static String toJson(Object obj) {
    if (obj == null) {
      return null;
    }
    try {
      return mapper.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
