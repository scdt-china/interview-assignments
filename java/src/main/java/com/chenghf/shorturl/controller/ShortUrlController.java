package com.chenghf.shorturl.controller;

import com.chenghf.shorturl.service.IShortUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Collections;
import java.util.Set;


/**
 * 短域名url
 *
 * @author chf
 * @date 2021年7月4日
 */
@RestController
@Validated
public class ShortUrlController
{
    @Autowired
    private IShortUrlService shortUrlService;


    /**
     * 长域名转短域名服务
     * @author chf
     * @date 2021年7月4日
     * @param longUrl
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name="longUrl",value="长域名",required=true,paramType="query",dataType="String")
    })
    @GetMapping("/longToShort")
    public String longToShort(@NotBlank(message = "长域名不能为空") @RequestParam("longUrl") String longUrl)  {
        return shortUrlService.longToShort( longUrl);
    }


    /**
     * 短域名获取长域名服务
     * @author chf
     * @date 2021年7月4日
     * @param shortUrl
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name="shortUrl",value="短域名",required=true,paramType="path",dataType="String")
    })
    @GetMapping("/shortToLong/{shortUrl}")
    public String shortToLong(@NotBlank(message = "短域名不能为空") @PathVariable("shortUrl") String shortUrl)  {
        return shortUrlService.shortToLong(shortUrl);
    }


}
