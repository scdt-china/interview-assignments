package com.creolophus.liuyi.api.repository;

import com.creolophus.liuyi.api.storage.UrlStorage;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * @author magicnana
 * @date 2021/7/13 18:24
 */
@Component
public class ShortUrlMemeryRepository implements ShortUrlRepository {

  @Resource
  private UrlStorage urlStorage;

  @Override
  public int insertUrl(String shortUrl, String longUrl) {
    urlStorage.put(shortUrl,longUrl);
    return 1;
  }

  @Override
  public String queryUrl(String shortUrl) {
    return urlStorage.get(shortUrl);
  }
}
