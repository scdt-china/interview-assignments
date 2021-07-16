package com.creolophus.liuyi.api.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author magicnana
 * @date 2021/7/14 19:17
 */
public abstract class MFile {

  protected static final AtomicInteger TOTAL_MAPPED_FILES = new AtomicInteger(0);
  protected static final AtomicLong TOTAL_MAPPED_VIRTUAL_MEMORY = new AtomicLong(0);
  private static final Logger logger = LoggerFactory.getLogger(MFile.class);
  protected final AtomicInteger wrotePosition = new AtomicInteger(0);
  protected long fileSize;
  protected String fileName;
  protected File file;
  protected FileChannel fileChannel;
  protected MappedByteBuffer mappedByteBuffer;


  public MFile(final String fileName, long fileSize) {
    this.fileName = fileName;
    this.file = new File(fileName);
    this.fileSize = fileSize;

    boolean ok = false;

    ensureDirOK(this.file.getParent());

    boolean isNew = !this.file.exists();

    try {
      this.fileChannel = new RandomAccessFile(this.file, "rw").getChannel();
      this.mappedByteBuffer = this.fileChannel.map(MapMode.READ_WRITE, 0, fileSize);
      TOTAL_MAPPED_VIRTUAL_MEMORY.addAndGet(fileSize);
      TOTAL_MAPPED_FILES.incrementAndGet();
      ok = true;

    } catch (FileNotFoundException e) {
      logger.error("Failed to create file " + this.fileName, e);
      throw new RuntimeException(e);
    } catch (IOException e) {
      logger.error("Failed to map file " + this.fileName, e);
      throw new RuntimeException(e);
    } finally {
      if (!ok && this.fileChannel != null) {
        try {
          this.fileChannel.close();
        } catch (IOException e) {
          throw new RuntimeException("Failed to close fileChannel", e);
        }
      }
    }

    make(isNew);
  }

  private static Object invoke(final Object target, final String methodName,
      final Class<?>... args) {
    return AccessController.doPrivileged(new PrivilegedAction<Object>() {
      public Object run() {
        try {
          Method method = method(target, methodName, args);
          method.setAccessible(true);
          return method.invoke(target);
        } catch (Exception e) {
          throw new IllegalStateException(e);
        }
      }
    });
  }

  private static Method method(Object target, String methodName, Class<?>[] args)
      throws NoSuchMethodException {
    try {
      return target.getClass().getMethod(methodName, args);
    } catch (NoSuchMethodException e) {
      return target.getClass().getDeclaredMethod(methodName, args);
    }
  }

  private static ByteBuffer viewed(ByteBuffer buffer) {
    String methodName = "viewedBuffer";
    Method[] methods = buffer.getClass().getMethods();
    for (int i = 0; i < methods.length; i++) {
      if (methods[i].getName().equals("attachment")) {
        methodName = "attachment";
        break;
      }
    }

    ByteBuffer viewedBuffer = (ByteBuffer) invoke(buffer, methodName);
    if (viewedBuffer == null) {
      return buffer;
    } else {
      return viewed(viewedBuffer);
    }
  }

  public int append(byte[] data) {

    if (data == null || data.length == 0) {
      return 0;
    }

    int currentPos = this.wrotePosition.get();

    if ((currentPos + data.length) <= this.fileSize) {
      try {
        this.mappedByteBuffer.position(currentPos);
        this.mappedByteBuffer.put(data);
      } catch (Throwable e) {
        logger.error("Error occurred when append message to mappedFile.", e);
      }
      this.wrotePosition.addAndGet(data.length);
      return currentPos;
    } else {
      throw new RuntimeException("此文件已满");
    }
  }

  public void clean() {
    if (mappedByteBuffer == null || !mappedByteBuffer.isDirect()
        || mappedByteBuffer.capacity() == 0) {
      return;
    }
    invoke(invoke(viewed(mappedByteBuffer), "cleaner"), "clean");
  }

  private boolean ensureDirOK(final String dirName) {
    if (dirName != null) {
      File f = new File(dirName);
      if (!f.exists()) {
        f.mkdirs();
        return true;
      } else {
        return false;
      }
    }
    return false;
  }

  public void flush() {

    mappedByteBuffer.position(0);
    mappedByteBuffer.putInt(this.wrotePosition.get());
    this.mappedByteBuffer.force();
  }

  private void make(boolean isNew) {
    int newPosition = 0;
    if (isNew) {
      mappedByteBuffer.position(0);
      mappedByteBuffer.putInt(0);
      newPosition = newPosition + Config.HEADER_LENGTH;
    } else {
      mappedByteBuffer.position(0);
      newPosition = mappedByteBuffer.getInt();
    }
    wrotePosition.addAndGet(newPosition);
    this.flush();
  }

}
