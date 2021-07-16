package com.creolophus.liuyi.api.storage;

/**
 * @author magicnana
 * @date 2021/7/14 18:55
 */
public class Config {

  public static final int HEADER_LENGTH = 4;

  /**
   * 单个索引的字节数
   */
  public static final int INDEX_LENGTH = 16;

  /**
   * 每个索引文件包含的索引的数量
   */
  public static final int INDEX_FILE_LIMIT = 256;

  /**
   * 每个索引文件的所有索引的字节数之和
   */
  public static final int INDEX_FILE_LENGTH = INDEX_LENGTH * INDEX_FILE_LIMIT;

  /**
   * 索引文件的数量
   */
  public static final int INDEX_FILE_SIZE = 512;


  /**
   * 索引文件名前缀
   */
  public static String INDEX_FILE_PATH = "target/run/index/";
  public static String DATA_FILE_PATH = "target/run/data/";



  /**
   * 数据文件名称
   */
  public static final String DATA_FILE_NAME = "data";


  /**
   * 数据文件大小
   */
  public static final long DATA_FILE_CAPACITY = 1024 * 1024 * 10; //10m



}
