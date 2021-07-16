package com.creolophus.liuyi.api.storage;

import java.nio.ByteBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IndexFile extends MFile{

  private static final Logger logger = LoggerFactory.getLogger(IndexFile.class);


  public IndexFile(String fileName){
    super(fileName,Config.INDEX_FILE_LENGTH);
  }

  public Index find(String shortUrl){
    for(int i=Config.HEADER_LENGTH;i<fileSize;i+=Config.INDEX_LENGTH){
      ByteBuffer bn = mappedByteBuffer.asReadOnlyBuffer();
      bn.position(i);

      byte[] bs = new byte[8];
      bn.get(bs);

      String key1 = new String(bs);
      int val1 = bn.getInt();
      int val2 = bn.getInt();
      if(val1==0){
        break;
      }

      if(key1.equalsIgnoreCase(shortUrl)){
        return new Index(shortUrl,val1,val2);
      }
    }
    return null;
  }


  public boolean append(Index index) {

    if(index==null){
      return false;
    }

    int currentPos = this.wrotePosition.get();

    if ((currentPos + index.getLength()) <= this.fileSize) {
      try {
        this.mappedByteBuffer.position(currentPos);
        this.mappedByteBuffer.put(index.getShortUrl().getBytes());
        this.mappedByteBuffer.putInt(index.getPosition());
        this.mappedByteBuffer.putInt(index.getLength());
      } catch (Throwable e) {
        logger.error("Error occurred when append message to mappedFile.", e);
      }
      this.wrotePosition.addAndGet(Config.INDEX_LENGTH);
      return true;
    }else{
      throw new RuntimeException("此文件已满");
    }
  }



}
