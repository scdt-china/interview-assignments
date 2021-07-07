package com.xiong.urlservice.controller;

import com.xiong.urlservice.boot.request.OriginUrlRequest;
import com.xiong.urlservice.boot.request.ShortUrlRequest;
import com.xiong.urlservice.boot.response.Result;
import com.xiong.urlservice.boot.response.ShortUrlResponse;
import com.xiong.urlservice.service.UrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author: create by xiong
 * @version: v1.0
 * @description: 短连接服务
 * @date:2021/6/21 1:38 下午
 */
@Slf4j
@RestController
@RequestMapping("/url/")
@Api(tags = "短连接服务管理")
public class UrlController {

    @Resource
    private UrlService urlService;

    @ApiOperation("生成短链接")
    @PostMapping("saveShortUrl")
    public Result<ShortUrlResponse> saveShortUrl(@RequestBody @Valid ShortUrlRequest request, BindingResult result){
        if (result.hasErrors()) {
            return Result.fail(result.getFieldError().getDefaultMessage());
        }
        return urlService.saveShortUrl(request);
    }
    @ApiOperation("获取原链接")
    @PostMapping("getOriginUrl")
    public Result<ShortUrlResponse> getOriginUrl(@RequestBody @Valid OriginUrlRequest request, BindingResult result){
        if (result.hasErrors()) {
            return Result.fail(result.getFieldError().getDefaultMessage());
        }
        return urlService.getOriginUrl(request);
    }
}
