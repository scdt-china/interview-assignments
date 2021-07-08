package com.cyz.shorturl.controller;

import com.cyz.shorturl.service.ShortUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName HelloController
 * @Description //短域名服务控制器
 * @Author CYZ
 * @Date 2021/07/04 0017 上午 10:11
 **/
@RestController
@RequestMapping("/url")
@Api(tags = "短域名服务接口")
public class ShortUrlController {

    @Autowired
    private ShortUrlService shortUrlService;

    @GetMapping("/shorten")
    @ApiOperation("短域名存储接口")
    @ApiImplicitParam(name = "longUrl", value = "长域名", dataType = "String", paramType = "query", required = true, example = "http://www.163.com")
    public String shorten(@RequestParam String longUrl) {
        String shortUrl = shortUrlService.shortenUrl(longUrl);
        return shortUrl;
    }

    @GetMapping("/original")
    @ApiOperation("短域名读取接口")
    @ApiImplicitParam(name = "shortUrl", value = "短域名", dataType = "String", paramType = "query", required = true, example = "http://z.cn/asdf1234")
    public String original(@RequestParam String shortUrl) {
        String longUrl = shortUrlService.originalUrl(shortUrl);
        return longUrl;
    }

}
