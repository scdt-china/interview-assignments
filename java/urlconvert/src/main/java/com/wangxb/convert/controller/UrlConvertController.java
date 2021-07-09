package com.wangxb.convert.controller;

import com.wangxb.convert.service.UrlConvertService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/url/")
@Api(value = "长短链接转换")
public class UrlConvertController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UrlConvertService urlConvertService;

    @RequestMapping(method = RequestMethod.GET,value = "/getShortUrl")
    @ApiOperation(value = "获取短链接", notes = "将长链接转换成短链接")
    public String getShortUrl(@ApiParam(value = "长链接" , required=true ) @RequestParam String longUrl){
        String shortUrl = urlConvertService.getShortUrl(longUrl);
        return shortUrl;
    }


    @RequestMapping(method = RequestMethod.GET,value = "/getLongUrl")
    @ApiOperation(value = "获取长链接", notes = "将短链接转换成长链接")
    public String getLongUrl(@ApiParam(value = "短链接" , required=true ) @RequestParam String shortUrl){
        String longUrl = urlConvertService.getLongUrl(shortUrl);
        return longUrl;
    }
}
