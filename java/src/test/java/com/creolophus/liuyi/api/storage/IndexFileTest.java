package com.creolophus.liuyi.api.storage;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import org.junit.Test;

/**
 * @author magicnana
 * @date 2021/7/14 15:49
 */
public class IndexFileTest {

  @Test
  public void testPutInt(){

  }


  @Test
  public void testIndexConstruct() throws IOException {

    File file = new File("target/test/index/testIndexConstruct");
    if(file.exists()){
      file.delete();
    }
    FileChannel fileChannel = new RandomAccessFile(file, "rw").getChannel();
    MappedByteBuffer mappedByteBuffer = fileChannel.map(MapMode.READ_WRITE, 0, 1024);


    mappedByteBuffer.putInt(0);


    String key = "abcdefjh";
    mappedByteBuffer.put(key.getBytes());
    mappedByteBuffer.putInt(100);
    mappedByteBuffer.putInt(101);
    System.out.println(mappedByteBuffer.position()+" "+mappedByteBuffer.limit()+" "+mappedByteBuffer.capacity());


    String key2 = "ijklmnop";
    mappedByteBuffer.put(key2.getBytes());
    mappedByteBuffer.putInt(102);
    mappedByteBuffer.putInt(103);


    mappedByteBuffer.force();



    System.out.println(mappedByteBuffer.position()+" "+mappedByteBuffer.limit()+" "+mappedByteBuffer.capacity());

    ByteBuffer bb = mappedByteBuffer.asReadOnlyBuffer();
    bb.position(0);
    int po = bb.getInt();

    bb.position(4);
    byte[] bytes1 = new byte[8];
    bb.get(bytes1);
    String value1 = new String(bytes1);
    int i3 = bb.getInt();
    int i4 = bb.getInt();

    bb.position(20);
    byte[] bytes = new byte[8];
    bb.get(bytes);
    String value2 = new String(bytes);
    int i1 = bb.getInt();
    int i2 = bb.getInt();

    System.out.println("ss");

  }


  @Test
  public void testAppendAndFind(){
    IndexFile indexFile = new IndexFile("target/test/index/testAppendAndFind");

    Index index1 = new Index("abcdef11", 100, 100);
    Index index2 = new Index("abcdef22", 200, 200);
    indexFile.append(index1);
    indexFile.append(index2);

    indexFile.flush();

    Index ret1 = indexFile.find(index1.getShortUrl());
    Index ret2 = indexFile.find(index2.getShortUrl());

    assertTrue(index1.equals(ret1));
    assertTrue(index2.equals(ret2));

    indexFile.clean();
  }


}