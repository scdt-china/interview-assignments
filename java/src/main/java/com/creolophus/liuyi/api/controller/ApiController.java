package com.creolophus.liuyi.api.controller;

import com.creolophus.liuyi.api.service.ShortUrlService;
import com.creolophus.liuyi.common.api.ApiResult;
import com.creolophus.liuyi.common.base.AbstractController;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author magicnana
 * @date 2021/7/12 17:00
 */
@RestController
@RequestMapping(value = "/ia/short/api")
public class ApiController extends AbstractController {

  @Resource
  private ShortUrlService shortUrlService;

  @RequestMapping(value = "/long_url", method = RequestMethod.GET)
  public ApiResult longUrl(
      @RequestParam("shortUrl") String shortUrl
  ) {
    String longUrl = shortUrlService.findLongUrlByShortUrl(shortUrl);
    return new ApiResult(longUrl);
  }

  @RequestMapping(value = "/short_url", method = RequestMethod.GET)
  public ApiResult shortUrl(
      @RequestParam("longUrl") String longUrl
  ) {
    String shortUrl = shortUrlService.createShortUrl(longUrl);
    return new ApiResult(shortUrl);
  }
}
