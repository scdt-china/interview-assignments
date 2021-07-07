package com.zdkj.modler.shorturl.controller;

import com.zdkj.handler.annotion.ShortUrlLog;
import com.zdkj.modler.shorturl.param.ShortUrlReadParam;
import com.zdkj.modler.shorturl.param.ShortUrlSaveParam;
import com.zdkj.modler.shorturl.service.ShortUrlService;
import com.zdkj.pojo.response.ResponseData;
import com.zdkj.pojo.response.SuccessResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author bihuiwen
 * @version 1.0
 * @description: 短域名服务Controller
 * @date 2021/7/5 下午11:09
 */
@Api(value = "短链接API", tags = "短链接相关API接口")
@RestController
public class ShortUrlController {

    @Resource
    ShortUrlService shortUrlService;

    /**
     * 短域名存储控制器
     * @param shortUrlSaveParam
     * @return
     */
    @PostMapping("/shortUrl/save")
    @ApiOperation(value = "短域名存储", httpMethod = "POST")
    @ShortUrlLog(title = "短域名存储接口")
    public ResponseData saveShortUrl(@Validated(ShortUrlSaveParam.save.class) ShortUrlSaveParam shortUrlSaveParam) {
        return new SuccessResponseData(shortUrlService.shortUrlSave(shortUrlSaveParam));
    }

    /**
     * 短域名读取控制器
     * @param shortUrlReadParam
     * @return
     */
    @GetMapping("/shortUrl/read")
    @ShortUrlLog(title = "短域名读取接口")
    @ApiOperation(value = "短域名读取", httpMethod = "GET")
    public ResponseData readShortUrl(@Validated(ShortUrlReadParam.read.class) ShortUrlReadParam shortUrlReadParam) {
        return new SuccessResponseData(shortUrlService.shortUrlRead(shortUrlReadParam));
    }

}
