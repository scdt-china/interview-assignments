package com.url.transcoding.controller;

import com.url.transcoding.service.UrlTranscodingService;
import com.url.transcoding.util.CommonResult;
import com.url.transcoding.util.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Api(tags = "短连接生成")
@RestController
public class UrlTranscodingController {

    @Autowired
    private UrlTranscodingService urlTranscodingService;

    @PostMapping(value = "/longToShort")
    @ApiOperation("短域名存储接口：接受长域名信息，返回短域名信息")
    @ApiImplicitParam(name = "longUrl", value = "长域名地址", dataType = "String", paramType = "query")
    public CommonResult longToShort(@RequestParam String longUrl) {
        return CommonResult.success(urlTranscodingService.longToShort(longUrl));
    }

    @GetMapping(value = "/shortToLong/{shortUrl}")
    @ApiOperation("短域名读取接口：接受短域名信息，返回长域名信息")
    @ApiImplicitParam(name = "shortUrl", value = "短域名地址", dataType = "String", paramType = "path")
    public CommonResult shortToLong(@PathVariable("shortUrl") String shortUrl) {
        String longUrl = urlTranscodingService.shortToLong(shortUrl);
        if (longUrl == null) {
            return CommonResult.failed(ResultCode.INVALID_URL);
        }
        return CommonResult.success(longUrl);
    }
}
