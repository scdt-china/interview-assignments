package com.creolophus.liuyi.api.storage;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

/**
 * @author magicnana
 * @date 2021/7/15 10:32
 */
public class DataFileTest {

  @Test
  public void testStorage(){

    String path1 = "target/test/data/testStorage";
    String path2 = "target/test/index/testStorage";

    File file1 = new File(path1);
    File file2 = new File(path2);

    if(file1.exists()){
      file1.delete();
    }

    if(file2.exists()){
      file2.delete();
    }
    //每次删除的原因: 多次测试的短连接是一样的,这里的长连接是随机生成的.所以第二次测试的时候,生成的短连接和第一次一样,但是长连接不一样.最下边断言的时候肯定出错.所以这里直接删除文件了


    DataFile dataFile = new DataFile(path1);
    IndexFile indexFile = new IndexFile(path2);



    List<Index> list = new ArrayList(4);
    for(int i = 0;i< 4;i++){
      int count = RandomUtils.nextInt(12,32);
      String url = RandomStringUtils.randomAlphabetic(count);

      byte[] longurl = url.getBytes();
      int start = dataFile.append(longurl);
      int length = longurl.length;

      Index index = new Index("short00"+i, start, length);
      index.setLongUrl(url);

      list.add(index);

      indexFile.append(index);
      dataFile.flush();
      indexFile.flush();

    }

    for(int i=0;i<list.size();i++){
      Index index = list.get(i);
      Index searchResult = indexFile.find(index.getShortUrl());
      String url = dataFile.find(searchResult.getPosition(), searchResult.getLength());
      assertTrue(index.getLongUrl().equals(url));
    }

    dataFile.clean();
    indexFile.clean();
  }
}