package com.creolophus.liuyi.api.storage;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

/**
 * @author magicnana
 * @date 2021/7/15 11:10
 */
public class UrlStorageTest {

  @Test
  public void testUrl(){

    Config.INDEX_FILE_PATH = "target/test/index/testUrl/";
    Config.DATA_FILE_PATH = "target/test/data/testUrl/";

    //这里把测试生成的路径和运行时分开.

    UrlStorage urlStorage = new UrlStorage();

    Map<String,String> map = new HashMap();

    for(int i=0;i<10;i++){
      String shortUrl = RandomStringUtils.randomAlphabetic(8);
      int count = RandomUtils.nextInt(12,32);
      String longUrl = RandomStringUtils.randomAlphabetic(count);

      map.put(shortUrl,longUrl);
      urlStorage.put(shortUrl,longUrl);
    }

    for(Map.Entry<String,String> entry : map.entrySet()){
      String ret = urlStorage.get(entry.getKey());
      String url = entry.getValue();
      assertTrue(ret.equals(url));
    }

    urlStorage.clean();
  }

}