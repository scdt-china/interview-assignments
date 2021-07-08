package com.example.shorturl.controller;

import com.example.shorturl.service.UrlService;
import com.example.shorturl.vos.LongurlReqVo;
import com.example.shorturl.vos.LongurlResVo;
import com.example.shorturl.vos.ShorturlReqVo;
import com.example.shorturl.vos.ShorturlResVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author : shenhc
 * @date : 2021/7/6
 * desc:
 */
@RestController
@RequestMapping("/shorturl")
@Api(value = "ShorturlController", description = "ShorturlController")
public class ShorturlController {

    @Autowired
    private UrlService urlService;


    @ApiOperation(value = "获取短域名", notes = "获取短域名", httpMethod = "POST", response = ShorturlResVo.class)
    @RequestMapping("/toShort")
    public ShorturlResVo getShorturl(@RequestBody ShorturlReqVo input) {

        String longurl = input.getUrl();
        ShorturlResVo resVo = new ShorturlResVo();
        resVo.setUrl(urlService.getShorturl(longurl));
        return resVo;
    }

    @RequestMapping("/toLong")
    @ApiOperation(value = "获取长域名", notes = "跟据短域名获取长域名", httpMethod = "POST",
            response = LongurlResVo.class)
    public LongurlResVo getLongurl(@RequestBody LongurlReqVo input) {
        String shorturl = input.getUrl();
        LongurlResVo resVo = new LongurlResVo();
        resVo.setUrl(urlService.getLongurl(shorturl));
        return resVo;
    }
}
