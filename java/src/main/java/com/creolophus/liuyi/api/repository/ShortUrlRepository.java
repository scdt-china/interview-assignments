package com.creolophus.liuyi.api.repository;

/**
 * @author magicnana
 * @date 2021/7/13 18:21
 */
public interface ShortUrlRepository {

  int insertUrl(String shortUrl,String longUrl);

  String queryUrl(String shortUrl);

}
