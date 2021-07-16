package com.creolophus.liuyi.api.storage;

import static org.junit.Assert.assertTrue;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

/**
 * @author magicnana
 * @date 2021/7/14 20:40
 */
public class IndexTest {

  @Test
  public void testIndex(){
    for(int i=0;i<100;i++){
      String url = RandomStringUtils.randomAlphabetic(8);
      Index index = new Index(url,0 , 0);
      assertTrue("有错咋地?",index.getIndex()>=0 && index.getIndex()<Config.INDEX_FILE_SIZE);
    }
  }

}