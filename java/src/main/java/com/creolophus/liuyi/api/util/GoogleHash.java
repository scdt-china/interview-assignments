package com.creolophus.liuyi.api.util;
import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;


/**
 * @author magicnana
 * @date 2021/7/12 16:35
 */
public class GoogleHash {

  public static String murmur3_32_string(String primaryKey) {
    return Hashing.murmur3_32().hashString(primaryKey, StandardCharsets.UTF_8).toString();
  }


  public static int murmur3_32_int(String primaryKey) {
    return Hashing.murmur3_32().hashString(primaryKey, StandardCharsets.UTF_8).asInt();
  }

}
