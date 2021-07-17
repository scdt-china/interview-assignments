package com.example.shorturlproj.controller;


import com.example.shorturlproj.service.ShortUrlTransformService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;


@RestController
@Api(tags = "ShortUrlProjController")
public class ShortUrlProjController {
    private static final Logger log = LoggerFactory.getLogger(ShortUrlProjController.class);
    @Resource(name = "shortUrlTransformServiceImpl")
    private ShortUrlTransformService shortUrlTransformService;

    @GetMapping("/getShortUrl")
    @ApiOperation(value = "getShortUrlTest", notes = "输入长域名，生成短域名"/*,response = String.class*/)

    public String getShortUrlTest(@ApiParam(name="longUrl",value="长域名",required=true)@RequestParam("longUrl")String longUrl) throws ExecutionException {
            String ret = shortUrlTransformService.getShortUrlFromLongUrl(longUrl);
            log.debug("长域名转换结果："+ret);
            //System.out.println(ret);
            return ret;

    }
    @GetMapping("/getLongUrl")
    @ApiOperation(value = "getLongUrlTest", notes = "输入短域名，返回长域名，如果短域名已失效则返回错误信息")

    public String getLongUrlTest(@ApiParam(name="shortUrl",value="短域名",required=true)@RequestParam("shortUrl")String shortUrl) throws ExecutionException {
        String ret = shortUrlTransformService.getLongUrlFromShortUrl(shortUrl);
        //System.out.println(ret);
        log.debug("短域名转换结果："+ret);
        return ret;
    }
}
