package com.suncy.shorturl.controller;

import com.suncy.shorturl.biz.IShortUrlBiz;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/shorturl")
@Api
public class ShorUrlContrller {

    @Autowired
    @Qualifier("shortUrlBizV1Impl")
    private IShortUrlBiz shortUrlBiz;

    @ApiOperation(value = "生成短链接")
    @RequestMapping("/to-shorturl")
    @ResponseBody
    public String toShortUrl(@ApiParam(name = "fullUrl", value = "全链接url", required = true) String fullUrl) {
        return shortUrlBiz.toShortUrl(fullUrl);
    }

    @ApiOperation(value = "查找全链接")
    @RequestMapping("/find-fullurl")
    @ResponseBody
    public String findFullUrl(@ApiParam(name = "shortUrl", value = "短链接", required = true) String shortUrl) {
        return shortUrlBiz.findFullUrl(shortUrl);
    }

}
