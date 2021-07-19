package com.wanghui.controller;

import com.wanghui.model.ShortUrl;
import com.wanghui.service.DomainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 10281
 * @title 短域名服务API入口
 * @Date 2021-07-17 15:42
 * 提供两个API接口:
 * 1、短域名存储接口：接受长域名信息，返回短域名信息
 *2、短域名读取接口：接受短域名信息，返回长域名信息。
 * @Description
 */

@RestController
@RequestMapping("/api/shorturl")
@Api(tags = {"短域名服务"})
public class ShortUrlController {

    @Autowired
    private DomainService domainService;

    /**
     * 短域名存储接口：接受长域名信息，返回短域名信息
     * @param longUrl
     */
    @PostMapping("/shortname")
    @ApiOperation("查询短域名")
    @ApiImplicitParam(name = "longUrl", value = "长域名", dataType = "String", paramType ="query", required = true, defaultValue = "www.baidu.com")
    public ShortUrl getShortUrl(@RequestParam String longUrl) {
        return domainService.long2ShortUrl(longUrl);
    }

    /**
     * 短域名读取接口：接受短域名信息，返回长域名信息。
     * @param shortUrl
     */
    @GetMapping("/shortname")
    @ApiOperation("查询长域名")
    @ApiImplicitParam(name = "shortUrl", value = "短域名", dataType = "String", paramType ="query", required = true, defaultValue = "baidu")
    public ShortUrl getLongUrl(@RequestParam String shortUrl) {
        return domainService.short2LongUrl(shortUrl);
    }
}
