package com.creolophus.liuyi.api.storage;

import org.springframework.stereotype.Component;

/**
 * @author magicnana
 * @date 2021/7/14 20:24
 */
@Component
public class UrlStorage {

  private static final IndexQueue INDEX_QUEUE = IndexQueue.getInstance();
  private static final DataFile dataFile = new DataFile(Config.DATA_FILE_PATH+Config.DATA_FILE_NAME);

  public void clean() {
    dataFile.clean();
    INDEX_QUEUE.clean();
  }


  public void put(String shortUrl,String longUrl){
    byte[] bytes = longUrl.getBytes();
    int start = dataFile.append(bytes);
    int length = bytes.length;
    dataFile.flush();

    Index index = new Index(shortUrl,start ,length);
    INDEX_QUEUE.write(index);
  }

  public String get(String shortUrl){
    Index index = INDEX_QUEUE.read(shortUrl);
    if(index==null){
      return null;
    }
    return dataFile.find(index.getPosition(),index.getLength());
  }

}
