package com.creolophus.liuyi.api.service;

import com.creolophus.liuyi.api.repository.ShortUrlRepository;
import com.creolophus.liuyi.api.util.GoogleHash;
import com.creolophus.liuyi.common.base.AbstractService;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author magicnana
 * @date 2021/7/13 17:56
 */
@Service
public class ShortUrlService extends AbstractService {

  @Resource
  private ShortUrlRepository shortUrlRepository;

  public String createShortUrl(String longUrl){
    String shortUrl =  GoogleHash.murmur3_32_string(longUrl);

    String longUrlExist = findLongUrlByShortUrl(shortUrl);

    if(StringUtils.isBlank(longUrlExist)){
      saveUrl(shortUrl,longUrl);
      return shortUrl;
    }

    if(longUrlExist.equals(longUrl)){
      return shortUrl;
    }else{
      throw new RuntimeException("先抛出来吧,碰撞就不写了");
    }

  }

  public Integer saveUrl(String shortUrl,String longUrl){
    return shortUrlRepository.insertUrl(shortUrl, longUrl);
  }

  public String findLongUrlByShortUrl(String shortUrl){
    return shortUrlRepository.queryUrl(shortUrl);
  }

}
