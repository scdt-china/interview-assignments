package com.creolophus.liuyi.api.storage;

import java.nio.ByteBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataFile extends MFile {

  private static final Logger logger = LoggerFactory.getLogger(DataFile.class);


  public DataFile(String fileName) {
    super(fileName,Config.DATA_FILE_CAPACITY);
  }

  public String find(int start, int length) {
    ByteBuffer bn = mappedByteBuffer.asReadOnlyBuffer();
    bn.position(start);

    byte[] bs = new byte[length];
    bn.get(bs);

    String url = new String(bs);
    return url;
  }
}
