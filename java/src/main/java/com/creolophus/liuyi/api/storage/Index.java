package com.creolophus.liuyi.api.storage;

import com.creolophus.liuyi.api.util.GoogleHash;
import java.util.Objects;

/**
 * @author magicnana
 * @date 2021/7/14 18:43
 */
public final class Index {


  private String shortUrl;
  private int position;
  private int length;
  private int index;
  private String longUrl;

  public Index(String shortUrl,int position,int length){
    int hash = Math.abs(GoogleHash.murmur3_32_int(shortUrl));
    this.index = hash < Config.INDEX_FILE_SIZE?hash:hash % Config.INDEX_FILE_SIZE;
    this.shortUrl = shortUrl;
    this.position = position;
    this.length = length;
  }

  public String getShortUrl() {
    return shortUrl;
  }

  public String getLongUrl() {
    return longUrl;
  }

  public void setLongUrl(String longUrl) {
    this.longUrl = longUrl;
  }

  public int getPosition() {
    return position;
  }

  public int getIndex() {
    return index;
  }

  public int getLength() {
    return length;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Index index1 = (Index) o;
    return getPosition() == index1.getPosition() &&
        getLength() == index1.getLength() &&
        getIndex() == index1.getIndex() &&
        Objects.equals(getShortUrl(), index1.getShortUrl());
  }

}
