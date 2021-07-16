package com.creolophus.liuyi.api.storage;

/**
 * @author magicnana
 * @date 2021/7/14 19:52
 */
public final class IndexQueue {

  private IndexFile[] files;

  private static IndexQueue instance;

  private IndexQueue(){
    files = new IndexFile[Config.INDEX_FILE_SIZE];
    for(int i=0;i<files.length;i++){
      files[i] = new IndexFile(Config.INDEX_FILE_PATH+i);
    }
  }

  public static IndexQueue getInstance(){
    if(instance!=null){
      return instance;
    }else{
      instance = init();
      return instance;
    }
  }

  public static IndexQueue init(){
    synchronized (IndexQueue.class){
      return new IndexQueue();
    }
  }

  public void clean() {
    for(int i=0;i<files.length;i++){
      files[i].clean();
    }
  }

  public void write(Index index){
    files[index.getIndex()].append(index);
    files[index.getIndex()].flush();
  }

  public Index read(String shortUrl){
    Index index = new Index(shortUrl, 0,0);
    return files[index.getIndex()].find(shortUrl);
  }

}
