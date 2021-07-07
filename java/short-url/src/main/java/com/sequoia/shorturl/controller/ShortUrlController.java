package com.sequoia.shorturl.controller;

import com.sequoia.shorturl.common.ResponseResult;import com.sequoia.shorturl.service.ShortUrlService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
/***
 *
 * 短域名服务API接口
 *
 * @Author
 *
 * @Date 2021/6/27
 *
 * @version v1.0.0
 *
 */
@RestController
@Slf4j
@Api(value  = "短域名接口",tags = "域名转换操作接")
@Deprecated
public class ShortUrlController {

   @Autowired
   ShortUrlController() {
   }

   @Autowired
   private ShortUrlService shortUrlService;

   @ApiOperation(value = "根据传入请求url，返回对应的短域名", notes = "短域名转换")
   @RequestMapping(value="/createShortUrl" , method = RequestMethod.POST)
   public ResponseResult createShortUrl(@RequestParam(name="originalUrl",required=true) String originalUrl){

        return shortUrlService.createShortUrl(originalUrl);

   }

   @ApiOperation(value = "根据传入请求短url，返回对应的原长域名", notes = "长域名还原")
   @RequestMapping(value="/getOriginalUrl", method = RequestMethod.GET)
   public ResponseResult getOriginalUrl(@RequestParam(name="shortUrl",required=true) String shortUrl){

        return shortUrlService.getOriginalUrl(shortUrl);

   }


}
